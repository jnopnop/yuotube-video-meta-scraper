package org.nop.scraper.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.nop.scraper.model.VideoMeta;
import org.nop.scraper.service.JsoupConverter;
import org.nop.scraper.service.YoutubeParser;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

@Service
public class YoutubeParserImpl implements YoutubeParser {

    @Inject
    private JsoupConverter jsoupConverter;

    @Override
    public CompletableFuture<VideoMeta> parseYoutubeResponse(final String response) {
        return toVideoMetaFuture(CompletableFuture.supplyAsync(() -> Jsoup.parse(response)));
    }

    private CompletableFuture<VideoMeta> toVideoMetaFuture(final CompletableFuture<Document> documentFuture) {
        return documentFuture.thenApply(doc -> jsoupConverter.toModel(doc, VideoMeta.class));
    }
}
