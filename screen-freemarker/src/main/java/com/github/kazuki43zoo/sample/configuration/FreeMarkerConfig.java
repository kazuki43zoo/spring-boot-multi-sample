package com.github.kazuki43zoo.sample.configuration;

import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.ObjectWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Arrays;
import java.util.regex.Pattern;

@Configuration
public class FreeMarkerConfig {

    // TODO: This solutions is workaround to use JSP tag library on the Spring Boot executable jar.
    @Autowired
    void configureTaglibFactory(FreeMarkerConfigurer configurer) {
        TaglibFactory factory = configurer.getTaglibFactory();
        factory.setMetaInfTldSources(Arrays.asList(new TaglibFactory.ClasspathMetaInfTldSource(Pattern.compile(".*"))));
        factory.setObjectWrapper(new BeansWrapperBuilder(freemarker.template.Configuration.getVersion()).build());
    }

}
