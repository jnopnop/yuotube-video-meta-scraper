package org.nop.parser;

import org.nop.parser.model.VideoMeta;

public interface VideoMetaRetrievalFacade {

    VideoMeta getVideoMeta(String videoId);
}
