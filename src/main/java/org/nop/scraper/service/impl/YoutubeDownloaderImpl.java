package org.nop.scraper.service.impl;

import org.nop.scraper.service.YoutubeDownloader;
import org.nop.utils.AsyncUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

@Service
public class YoutubeDownloaderImpl implements YoutubeDownloader {

    @Inject
    private AsyncRestTemplate asyncRestTemplate;

    @Override
    public CompletableFuture<ResponseEntity<String>> getPage(final String url) {
        return AsyncUtils.toCompletableFuture(asyncRestTemplate.getForEntity(url, String.class));
    }
}
