package org.nop.scraper.service.impl;

import org.jsoup.nodes.Document;
import org.nop.scraper.mapping.annotation.CssSelector;
import org.nop.scraper.mapping.annotation.DomTarget;
import org.nop.scraper.mapping.annotation.DomTarget.DomTargetType;
import org.nop.scraper.mapping.annotation.JsoupModel;
import org.nop.scraper.service.JsoupConverter;
import org.nop.utils.functional.TriFunction;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

@Component
public class JsoupConverterImpl implements JsoupConverter {

    private static final Map<DomTargetType, TriFunction<Document, String, String, String>> DOM_OPERATIONS =
            new EnumMap<>(DomTargetType.class);

    static {
        DOM_OPERATIONS.put(DomTargetType.ATTRIBUTE, ((doc, selector, attr) -> doc.select(selector).attr(attr)));
        DOM_OPERATIONS.put(DomTargetType.INNER_HTML, ((doc, selector, unused) -> doc.select(selector).html()));
        DOM_OPERATIONS.put(DomTargetType.OUTER_HTML, ((doc, selector, unused) -> doc.select(selector).outerHtml()));
        DOM_OPERATIONS.put(DomTargetType.TEXT, ((doc, selector, unused) -> doc.select(selector).text()));
    }

    @Override
    public <T> T toModel(final Document document, final Class<T> modelClass) {
        //TODO: Use appropriate parameter-level annotations
        Assert.notNull(document, "Jsoup Document should not be null!");
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
        final String attrName = domTarget.attributeName();

        return DOM_OPERATIONS.get(domTargetType).apply(document, selector, attrName);
    }
}
