package com.github.kazuki43zoo.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiApplication.class)
@WebIntegrationTest(randomPort = true)
public class HomeApiTests {

    @Configuration
    public static class LocalContext {
        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

    @Value("http://localhost:${local.server.port}")
    String bookResourceUrl;

    @Autowired
    RestOperations restOperations;

    @Test
    public void getResource() {
        HomeResource resource = restOperations.getForObject(
                bookResourceUrl, HomeResource.class);

        assertThat(resource.getMessage(), is("Hello API!!"));
    }


    public static class HomeResource implements Serializable {
        private static final long serialVersionUID = 1L;
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
