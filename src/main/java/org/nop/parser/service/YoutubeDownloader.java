package org.nop.parser.service;

import org.springframework.http.ResponseEntity;

public interface YoutubeDownloader {

    ResponseEntity<String> getPage(String url);
}
