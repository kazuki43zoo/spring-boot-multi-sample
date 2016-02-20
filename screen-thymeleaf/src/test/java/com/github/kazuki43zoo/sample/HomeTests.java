package com.github.kazuki43zoo.sample;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThymeleafApplication.class)
@WebIntegrationTest(randomPort = true)
public class HomeTests {

    @Value("http://localhost:${local.server.port}${server.context-path:}")
    String documentRootUrl;

    @Test
    public void home() throws IOException {
    	try(WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38)){
            HtmlPage page = webClient.getPage(new URL(documentRootUrl));

            assertThat(page.getElementById("message").asText(), is("Hello Thymeleaf !!"));
        }
    }

}
