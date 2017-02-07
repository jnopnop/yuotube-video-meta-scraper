package org.nop.scraper.service;

import org.jsoup.nodes.Document;

public interface JsoupConverter {

    <T> T toModel(Document document, Class<T> modelClass);
}
