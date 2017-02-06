package org.nop.web.controller;

import org.nop.scraper.YouTubeVideoMetaScraperFacade;
import org.nop.scraper.model.VideoMeta;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.inject.Inject;

@RestController
public class MetaInformationController extends AbstractAsyncController {

    @Inject
    private YouTubeVideoMetaScraperFacade parserFacade;

    @RequestMapping(value = "/video/youtube/{id}/meta", method = RequestMethod.GET)
    public DeferredResult<VideoMeta> getYoutubeVideoMeta(final @PathVariable("id") String id) {
        return respond(parserFacade.getVideoMeta(id));
    }
}
