package org.nop.scraper.impl;

import org.nop.scraper.YouTubeVideoMetaScraperFacade;
import org.nop.scraper.model.VideoMeta;
import org.nop.scraper.service.YoutubeDownloader;
import org.nop.scraper.service.YoutubeParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class YouTubeVideoMetaScraperFacadeImpl implements YouTubeVideoMetaScraperFacade {

    @Inject
    private YoutubeDownloader downloader;

    @Inject
    private YoutubeParser parser;

    @Override
    public VideoMeta getVideoMeta(final String videoId) {
        String youtubeUrl = getYoutubeUrl(videoId);
        ResponseEntity<String> htmlDoc = downloader.getPage(youtubeUrl);
        return parser.parseYoutubeResponse(htmlDoc.getBody());
    }

    private String getYoutubeUrl(final String id) {
        return String.format("https://www.youtube.com/watch?v=%s", id);
    }
}
