package com.github.kazuki43zoo.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiApplication.class)
@WebIntegrationTest(randomPort = true)
public class TodosApiTests {

    @Configuration
    public static class LocalContext {
        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

    @Value("http://localhost:${local.server.port}/todos")
    String todoResourceUrl;

    @Autowired
    RestOperations restOperations;

    @Test
    public void postAndGetResource() {
        TodoResource resource = new TodoResource();
        resource.setTodoTitle("Todoアプリの開発");

        URI createdResourceUri = restOperations.postForLocation(
                todoResourceUrl, resource);

        TodoResource createdResource = restOperations.getForObject(createdResourceUri, TodoResource.class);
        assertThat(createdResource.getTodoTitle(), is("Todoアプリの開発"));
        assertThat(createdResource.isFinished(), is(false));

    }

    @Test
    public void putResource() {
        TodoResource resource = new TodoResource();
        resource.setTodoTitle("Todoアプリの開発");

        URI createdResourceUri = restOperations.postForLocation(
                todoResourceUrl, resource);

        TodoResource createdResource = restOperations.getForObject(createdResourceUri, TodoResource.class);

        createdResource.setTodoTitle(createdResource.getTodoTitle() + "(Spring 4.3対応)");

        ResponseEntity<Void> updatedEntity = restOperations.exchange(RequestEntity.put(createdResourceUri).body(createdResource), Void.class);

        assertThat(updatedEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));

        TodoResource updatedResource = restOperations.getForObject(createdResourceUri, TodoResource.class);
        assertThat(updatedResource.getTodoTitle(), is("Todoアプリの開発(Spring 4.3対応)"));
        assertThat(updatedResource.isFinished(), is(false));

    }

    @Test
    public void deleteResource() {
        TodoResource resource = new TodoResource();
        resource.setTodoTitle("Todoアプリの開発");

        URI createdResourceUri = restOperations.postForLocation(
                todoResourceUrl, resource);

        TodoResource createdResource = restOperations.getForObject(createdResourceUri, TodoResource.class);

        String todoId = restOperations.getForEntity(createdResourceUri, TodoResource.class).getBody().getTodoId();

        ResponseEntity<Void> updatedEntity = restOperations.exchange(RequestEntity.delete(createdResourceUri).build(), Void.class);

        assertThat(updatedEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
        try {
            restOperations.getForObject(createdResourceUri, TodoResource.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            assertThat(e.getResponseBodyAsString(), is("{\n  \"message\" : \"Todo is not found (todoId = " + todoId + ")\",\n  \"documentation_url\" : \"http://example.com/api/errors\"\n}"));
        }
    }

    @Test
    public void getResources() {

        restOperations.delete(todoResourceUrl);


        {
            TodoResource resource = new TodoResource();
            resource.setTodoTitle("Todoアプリの開発");
            restOperations.postForLocation(todoResourceUrl, resource);
        }

        {
            TodoResource resource = new TodoResource();
            resource.setTodoTitle("Todoアプリの開発(Spring 4.3対応)");
            restOperations.postForLocation(todoResourceUrl, resource);
        }

        {
            TodoResource resource = new TodoResource();
            resource.setTodoTitle("Todoアプリの開発(Spring 5.0対応)");
            restOperations.postForLocation(todoResourceUrl, resource);
        }


        {
            ResponseEntity<List<TodoResource>> responseEntity =
                    restOperations.exchange(RequestEntity.get(URI.create(todoResourceUrl)).build(), new ParameterizedTypeReference<List<TodoResource>>() {
                    });
            List<TodoResource> resources = responseEntity.getBody();
            assertThat(resources.size(), is(3));
            {
                TodoResource resource = resources.get(0);
                assertThat(resource.getTodoTitle(), is("Todoアプリの開発"));
                assertThat(resource.isFinished(), is(false));
            }
            {
                TodoResource resource = resources.get(1);
                assertThat(resource.getTodoTitle(), is("Todoアプリの開発(Spring 4.3対応)"));
                assertThat(resource.isFinished(), is(false));
            }
            {
                TodoResource resource = resources.get(2);
                assertThat(resource.getTodoTitle(), is("Todoアプリの開発(Spring 5.0対応)"));
                assertThat(resource.isFinished(), is(false));
            }
        }

    }

    public static class TodoResource implements Serializable {
        private static final long serialVersionUID = 1L;

        private String todoId;
        private String todoTitle;
        private boolean finished;
        private LocalDateTime createdAt;

        public String getTodoId() {
            return todoId;
        }

        public void setTodoId(String todoId) {
            this.todoId = todoId;
        }

        public String getTodoTitle() {
            return todoTitle;
        }

        public void setTodoTitle(String todoTitle) {
            this.todoTitle = todoTitle;
        }

        public boolean isFinished() {
            return finished;
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
    }

}
