package org.nop.scraper.service;

import org.nop.scraper.model.VideoMeta;

import java.util.concurrent.CompletableFuture;

public interface YoutubeParser {

    CompletableFuture<VideoMeta> parseYoutubeResponse(String response);
}
