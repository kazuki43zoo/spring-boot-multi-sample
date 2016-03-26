package com.github.kazuki43zoo.sample;

import com.github.kazuki43zoo.sample.api.controller.todo.TodoResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringApplicationConfiguration;
import org.springframework.boot.test.context.web.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jms.core.JmsMessageOperations;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.support.SimpleJmsHeaderMapper;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessagingMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {JmsConsumerApplication.class, TodosApiTests.LocalContext.class})
@WebIntegrationTest(randomPort = true)
public class TodosApiTests {

    @Configuration
    static class LocalContext {

        @Bean
        DataSource dataSource() {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        }

        @Bean
        JmsMessagingTemplate jmsMessagingTemplate(ConnectionFactory connectionFactory, MappingJackson2MessageConverter mappingJackson2MessageConverter) {
            JmsMessagingTemplate template = new JmsMessagingTemplate(connectionFactory);
            template.setJmsMessageConverter(
                    new MessagingMessageConverter(mappingJackson2MessageConverter, new SimpleJmsHeaderMapper()));
            return template;
        }

    }

    @Autowired
    JmsMessageOperations operations;

    @Test
    public void postResource() throws InterruptedException {

        TodoResource resource = new TodoResource();
        resource.setTodoTitle("Todoアプリの開発");

        Map<String, Object> headers = new HashMap<>();
        headers.put("username", "kazuki43zoo");
        headers.put("trackingId", UUID.randomUUID().toString());

        operations.convertAndSend("todo", resource, headers);

        TimeUnit.SECONDS.sleep(2);

    }


}
