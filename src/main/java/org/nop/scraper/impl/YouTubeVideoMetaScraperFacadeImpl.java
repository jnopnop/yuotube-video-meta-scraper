package org.nop.scraper.impl;

import org.nop.scraper.YouTubeVideoMetaScraperFacade;
import org.nop.scraper.model.VideoMeta;
import org.nop.scraper.service.YoutubeDownloader;
import org.nop.scraper.service.YoutubeParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

@Service
public class YouTubeVideoMetaScraperFacadeImpl implements YouTubeVideoMetaScraperFacade {

    @Value("${youtube.video.url}")
    private String youtubeVideoBaseUrl;

    @Inject
    private YoutubeDownloader downloader;

    @Inject
    private YoutubeParser parser;

    @Override
    public CompletableFuture<VideoMeta> getVideoMeta(final String videoId) {
        String youtubeUrl = getYoutubeUrl(videoId);
        return downloader.getPage(youtubeUrl)
                .thenApply(HttpEntity::getBody)
                .thenCompose(parser::parseYoutubeResponse);
    }

    private String getYoutubeUrl(final String id) {
        return String.format(youtubeVideoBaseUrl, id);
    }
}
