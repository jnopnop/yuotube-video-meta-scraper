package org.nop.scraper;

import org.nop.scraper.model.VideoMeta;

public interface YouTubeVideoMetaScraperFacade {

    VideoMeta getVideoMeta(String videoId);
}
