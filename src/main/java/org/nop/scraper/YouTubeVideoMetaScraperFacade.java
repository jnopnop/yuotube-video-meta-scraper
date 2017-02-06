package org.nop.scraper;

import org.nop.scraper.model.VideoMeta;

import java.util.concurrent.CompletableFuture;

public interface YouTubeVideoMetaScraperFacade {

    CompletableFuture<VideoMeta> getVideoMeta(String videoId);
}
