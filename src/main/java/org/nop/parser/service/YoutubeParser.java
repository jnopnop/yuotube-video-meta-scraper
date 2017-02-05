package org.nop.parser.service;

import org.nop.parser.model.VideoMeta;

public interface YoutubeParser {

    VideoMeta parseYoutubeResponse(String response);
}
