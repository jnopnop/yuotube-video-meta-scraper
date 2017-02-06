package org.nop.scraper.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.nop.scraper.model.VideoMeta;
import org.nop.scraper.service.YoutubeParser;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class YoutubeParserImpl implements YoutubeParser {

    public static final String SELECTOR_ID = "meta[itemprop=videoId]";
    public static final String SELECTOR_TITLE = "meta[name=title]";
    public static final String SELECTOR_DURATION = "meta[itemprop=duration]";
    public static final String SELECTOR_DESCRIPTION = "meta[itemprop=description]";
    public static final String SELECTOR_AUTHOR = "span[itemprop=author]>link";

    public static final String ATTR_CONTENT = "content";
    public static final String ATTR_HREF = "href";

    @Override
    public CompletableFuture<VideoMeta> parseYoutubeResponse(final String response) {
        return toVideoMetaFuture(CompletableFuture.supplyAsync(() -> Jsoup.parse(response)));
    }

    private CompletableFuture<VideoMeta> toVideoMetaFuture(final CompletableFuture<Document> documentFuture) {
        return documentFuture.thenCompose(doc -> {
            VideoMeta videoMeta = new VideoMeta();

            CompletableFuture<Void> idFuture = CompletableFuture.runAsync(() -> videoMeta
                    .setId(parse(doc, SELECTOR_ID, ATTR_CONTENT)));

            CompletableFuture<Void> titleFuture = CompletableFuture.runAsync(() -> videoMeta
                    .setTitle(parse(doc, SELECTOR_TITLE, ATTR_CONTENT)));

            CompletableFuture<Void> durationFuture = CompletableFuture.runAsync(() -> videoMeta
                    .setDuration(parse(doc, SELECTOR_DURATION, ATTR_CONTENT)));

            CompletableFuture<Void> descriptionFuture = CompletableFuture.runAsync(() -> videoMeta
                    .setDescription(parse(doc, SELECTOR_DESCRIPTION, ATTR_CONTENT)));

            CompletableFuture<Void> authorFuture = CompletableFuture.runAsync(() -> videoMeta
                    .setAuthor(parse(doc, SELECTOR_AUTHOR, ATTR_HREF)));

            return CompletableFuture.allOf(idFuture, titleFuture, durationFuture, descriptionFuture, authorFuture)
                    .thenApply(aVoid -> videoMeta);
        });
    }

    private String parse(final Document document, final String selector, final String attr) {
        return document.select(selector).attr(attr);
    }
}
