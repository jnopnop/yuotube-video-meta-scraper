package org.nop.web.controller;

import org.nop.parser.VideoMetaRetrievalFacade;
import org.nop.parser.model.VideoMeta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class MetaInformationController {

    @Inject
    private VideoMetaRetrievalFacade parserFacade;

    @RequestMapping(value = "/video/youtube/{id}/meta", method = RequestMethod.GET)
    public ResponseEntity<VideoMeta> getYoutubeVideoMeta(final @PathVariable("id") String id) {
        return new ResponseEntity<>(parserFacade.getVideoMeta(id), HttpStatus.OK);
    }
}
