package com.github.kazuki43zoo.sample.component.tracking;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TrackingId {
}
