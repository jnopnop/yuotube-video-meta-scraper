package org.nop.scraper.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.nop.scraper.model.VideoMeta;
import org.nop.scraper.service.YoutubeParser;
import org.springframework.stereotype.Service;

@Service
public class YoutubeParserImpl implements YoutubeParser {

    @Override
    public VideoMeta parseYoutubeResponse(final String response) {
        return toVideoMeta(Jsoup.parse(response));
    }

    private VideoMeta toVideoMeta(final Document document) {
        VideoMeta videoMeta = new VideoMeta();
        videoMeta.setTitle(document.title());
        return videoMeta;
    }
}
