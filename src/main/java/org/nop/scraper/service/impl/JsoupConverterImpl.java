package org.nop.scraper.service.impl;

import org.jsoup.nodes.Document;
import org.nop.scraper.mapping.annotation.CssSelector;
import org.nop.scraper.mapping.annotation.DomTarget;
import org.nop.scraper.mapping.annotation.DomTarget.DomTargetType;
import org.nop.scraper.mapping.annotation.JsoupModel;
import org.nop.scraper.service.JsoupConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Component
public class JsoupConverterImpl implements JsoupConverter {

    @Override
    public <T> T toModel(final Document document, final Class<T> modelClass) {
        Assert.isTrue(modelClass.isAnnotationPresent(JsoupModel.class),
                String.format("Target class is not annotated with %s", JsoupModel.class.getName()));

        try {
            Constructor<T> constructor = modelClass.getConstructor();
            final T model = constructor.newInstance();

            ReflectionUtils.doWithFields(modelClass, field -> {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, model, this.parseFieldCssSelector(field, document));
            }, field -> field.isAnnotationPresent(CssSelector.class));

            return model;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    private String parseFieldCssSelector(final Field field, final Document document) {
        final CssSelector cssSelector = field.getAnnotation(CssSelector.class);
        final DomTarget domTarget = cssSelector.target();

        final String selector = cssSelector.value();
        final DomTargetType domTargetType = domTarget.value();

        if (domTargetType == DomTargetType.ATTRIBUTE) {
            final String attrName = domTarget.attributeName();
            return document.select(selector).attr(attrName);
        }

        return "Not implemented yet!";
    }
}
