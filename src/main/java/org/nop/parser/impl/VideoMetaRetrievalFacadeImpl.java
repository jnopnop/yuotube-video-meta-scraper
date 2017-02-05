package org.nop.parser.impl;

import org.nop.parser.VideoMetaRetrievalFacade;
import org.nop.parser.model.VideoMeta;
import org.nop.parser.service.YoutubeDownloader;
import org.nop.parser.service.YoutubeParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class VideoMetaRetrievalFacadeImpl implements VideoMetaRetrievalFacade {

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
