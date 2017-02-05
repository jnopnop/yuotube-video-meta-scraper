package org.nop.scraper.service;

import org.springframework.http.ResponseEntity;

public interface YoutubeDownloader {

    ResponseEntity<String> getPage(String url);
}
