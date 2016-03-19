package com.github.kazuki43zoo.sample.configuration;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfig {

    @Bean
    JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setRecoveryInterval(1000L);
        factory.setMessageConverter(mappingJackson2MessageConverter());
        return factory;
    }

    @Bean
    MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTypeIdPropertyName("typeId");
        return converter;
    }

    @Profile("sqs")
    @Bean
    ConnectionFactory connectionFactory() {
        return SQSConnectionFactory.builder()
                .withRegion(Region.getRegion(Regions.AP_NORTHEAST_1))
                .withAWSCredentialsProvider(
                        new AWSCredentialsProviderChain(
                                new DefaultAWSCredentialsProviderChain()
                                , new PropertiesFileCredentialsProvider("SqsCredentials.properties") // for local
                        ))
                .build();
    }

}
