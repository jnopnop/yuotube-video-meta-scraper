package org.nop.scraper.mapping.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DomTarget {

    DomTargetType value() default DomTargetType.TEXT;

    String attributeName() default "";

    enum DomTargetType {
        TEXT, INNER_HTML, OUTER_HTML, ATTRIBUTE
    }
}
