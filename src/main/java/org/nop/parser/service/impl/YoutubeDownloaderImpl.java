package org.nop.parser.service.impl;

import org.nop.parser.service.YoutubeDownloader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

@Service
public class YoutubeDownloaderImpl implements YoutubeDownloader {

    @Inject
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> getPage(final String url) {
        return restTemplate.getForEntity(url, String.class);
    }
}
