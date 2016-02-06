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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiApplication.class)
@WebIntegrationTest(randomPort = true)
public class BooksApiTests {

    @Configuration
    public static class LocalContext {
        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

    @Value("http://localhost:${local.server.port}/books")
    String bookResourceUrl;

    @Autowired
    RestOperations restOperations;

    @Test
    public void getResource() {
        BookResource resource = restOperations.getForObject(
                bookResourceUrl + "/00000000-0000-0000-0000-000000000000",
                BookResource.class);

        assertThat(resource.getBookId(), is("00000000-0000-0000-0000-000000000000"));
        assertThat(resource.getName(), is("書籍名"));
        assertThat(resource.getPublishedDate(), is(LocalDate.of(2010, 4, 20)));
    }

    @Test
    public void postResource() {
        BookResource resource = new BookResource();
        resource.setName("Spring入門");
        resource.setPublishedDate(LocalDate.of(2016, 4, 1));

        URI createdResourceUri = restOperations.postForLocation(
                bookResourceUrl, resource);

        BookResource createdResource = restOperations.getForObject(createdResourceUri, BookResource.class);
        assertThat(createdResource.getName(), is("Spring入門"));
        assertThat(createdResource.getPublishedDate(), is(LocalDate.of(2016, 4, 1)));

    }

    @Test
    public void putResource() {
        BookResource resource = new BookResource();
        resource.setName("Spring入門");
        resource.setPublishedDate(LocalDate.of(2016, 4, 1));

        URI createdResourceUri = restOperations.postForLocation(
                bookResourceUrl, resource);

        BookResource createdResource = restOperations.getForObject(createdResourceUri, BookResource.class);

        createdResource.setName(createdResource.getName() + "(Spring 4.2対応)");
        createdResource.setPublishedDate(LocalDate.of(2016, 3, 20));

        ResponseEntity<Void> updatedEntity = restOperations.exchange(RequestEntity.put(createdResourceUri).body(createdResource), Void.class);

        assertThat(updatedEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
        BookResource updatedResource = restOperations.getForObject(createdResourceUri, BookResource.class);
        assertThat(updatedResource.getName(), is("Spring入門(Spring 4.2対応)"));
        assertThat(updatedResource.getPublishedDate(), is(LocalDate.of(2016, 3, 20)));

    }

    @Test
    public void deleteResource() {
        BookResource resource = new BookResource();
        resource.setName("Spring入門");
        resource.setPublishedDate(LocalDate.of(2016, 4, 1));

        URI createdResourceUri = restOperations.postForLocation(
                bookResourceUrl, resource);
        String bookId = restOperations.getForEntity(createdResourceUri, BookResource.class).getBody().getBookId();

        ResponseEntity<Void> updatedEntity = restOperations.exchange(RequestEntity.delete(createdResourceUri).build(), Void.class);

        assertThat(updatedEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
        try {
            restOperations.getForObject(createdResourceUri, BookResource.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            assertThat(e.getResponseBodyAsString(), is("{\n  \"message\" : \"Book is not found (bookId = " + bookId + ")\",\n  \"documentation_url\" : \"http://example.com/api/errors\"\n}"));
        }
    }


    @Test
    public void getResources() {

        restOperations.delete(bookResourceUrl);


        {
            BookResource resource = new BookResource();
            resource.setName("Spring入門 2 (Spring 5対応)");
            resource.setPublishedDate(LocalDate.of(2017, 4, 1));
            try {
                restOperations.postForLocation(bookResourceUrl, resource);
            }catch (HttpServerErrorException e){
                e.printStackTrace();
                System.out.println(e.getResponseBodyAsString());
            }
        }

        {
            BookResource resource = new BookResource();
            resource.setName("Spring入門");
            resource.setPublishedDate(LocalDate.of(2016, 4, 1));
            restOperations.postForLocation(bookResourceUrl, resource);
        }


        {
            ResponseEntity<List<BookResource>> responseEntity =
                    restOperations.exchange(RequestEntity.get(URI.create(bookResourceUrl)).build(), new ParameterizedTypeReference<List<BookResource>>() {
                    });
            List<BookResource> resources = responseEntity.getBody();
            assertThat(resources.size(), is(3));
            {
                BookResource resource = resources.get(0);
                assertThat(resource.getName(), is("書籍名"));
                assertThat(resource.getPublishedDate(), is(LocalDate.of(2010, 4, 20)));
            }
            {
                BookResource resource = resources.get(1);
                assertThat(resource.getName(), is("Spring入門"));
                assertThat(resource.getPublishedDate(), is(LocalDate.of(2016, 4, 1)));
            }
            {
                BookResource resource = resources.get(2);
                assertThat(resource.getName(), is("Spring入門 2 (Spring 5対応)"));
                assertThat(resource.getPublishedDate(), is(LocalDate.of(2017, 4, 1)));
            }
        }

        {
            ResponseEntity<List<BookResource>> responseEntity =
                    restOperations.exchange(RequestEntity.get(
                            UriComponentsBuilder
                                    .fromUriString(bookResourceUrl)
                                    .queryParam("name", "書籍名")
                                    .build().encode().toUri()).build(),
                            new ParameterizedTypeReference<List<BookResource>>() {
                            });
            List<BookResource> resources = responseEntity.getBody();

            assertThat(resources.size(), is(1));
            BookResource resource = resources.get(0);
            assertThat(resource.getName(), is("書籍名"));
            assertThat(resource.getPublishedDate(), is(LocalDate.of(2010, 4, 20)));
        }

        {
            ResponseEntity<List<BookResource>> responseEntity =
                    restOperations.exchange(RequestEntity.get(
                            UriComponentsBuilder
                                    .fromUriString(bookResourceUrl)
                                    .queryParam("publishedDate", "1999-01-01")
                                    .build().encode().toUri()).build(),
                            new ParameterizedTypeReference<List<BookResource>>() {
                            });
            List<BookResource> resources = responseEntity.getBody();
            assertThat(resources.size(), is(0));
        }

    }


    public static class BookResource implements Serializable {
        private static final long serialVersionUID = 1L;
        private String bookId;
        private String name;
        private java.time.LocalDate publishedDate;

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(LocalDate publishedDate) {
            this.publishedDate = publishedDate;
        }
    }

}
