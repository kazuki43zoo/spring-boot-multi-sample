package com.github.kazuki43zoo.sample.api.component.error;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "api.error")
public class ApiErrorProperties {

    private Map<Class<? extends Exception>, String> messageMappings;

    private String documentationUrl;

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public Map<Class<? extends Exception>, String> getMessageMappings() {
        return messageMappings;
    }

    public void setMessageMappings(Map<Class<? extends Exception>, String> messageMappings) {
        this.messageMappings = messageMappings;
    }

    public String resolveMessage(Exception ex, String defaultMessage) {
        return getMessageMappings().entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(ex.getClass()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(defaultMessage);
    }

}

