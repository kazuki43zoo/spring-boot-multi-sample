package com.github.kazuki43zoo.sample;

import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;

@ServletComponentScan
@SpringBootApplication
public class ScreenApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScreenApplication.class, args);
    }

    // TODO: This solutions is workaround for execution at working directory of multi project root.
    @Profile("default")
    @Component
    private static class StandaloneEmbeddedServletContainerCustomizer implements EmbeddedServletContainerCustomizer {

        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            File screen = new File("sample-screen-jsp");
            if (screen.exists()) {
                File docRoot = new File(screen, "src/main/webapp");
                if (docRoot.exists()) {
                    container.setDocumentRoot(docRoot);
                }
            }
        }

    }

}
