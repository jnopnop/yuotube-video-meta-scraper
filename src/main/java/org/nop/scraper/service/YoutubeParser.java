package org.nop.scraper.service;

import org.nop.scraper.model.VideoMeta;

public interface YoutubeParser {

    VideoMeta parseYoutubeResponse(String response);
}
