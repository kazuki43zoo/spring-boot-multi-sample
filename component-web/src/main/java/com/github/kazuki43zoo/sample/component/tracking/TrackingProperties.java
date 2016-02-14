package com.github.kazuki43zoo.sample.component.tracking;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "web.tracking")
public class TrackingProperties {

    private static final String DEFAULT_TRACKING_ID_KEY = "X-Track-Id";

    private String idKey = DEFAULT_TRACKING_ID_KEY;

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

}
