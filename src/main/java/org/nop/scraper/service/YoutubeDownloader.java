package org.nop.scraper.service;

import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

public interface YoutubeDownloader {

    CompletableFuture<ResponseEntity<String>> getPage(String url);
}
