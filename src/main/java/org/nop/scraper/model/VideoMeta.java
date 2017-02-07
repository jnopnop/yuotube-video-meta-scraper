package org.nop.scraper.model;

import lombok.Getter;
import lombok.Setter;
import org.nop.scraper.mapping.annotation.CssSelector;
import org.nop.scraper.mapping.annotation.DomTarget;
import org.nop.scraper.mapping.annotation.JsoupModel;

import static org.nop.scraper.mapping.annotation.DomTarget.DomTargetType.ATTRIBUTE;

@Getter
@Setter
@JsoupModel
public class VideoMeta {

    @CssSelector(value = "meta[itemprop=videoId]",
            target = @DomTarget(value = ATTRIBUTE, attributeName = "content"))
    private String id;

    @CssSelector(value = "meta[name=title]",
            target = @DomTarget(value = ATTRIBUTE, attributeName = "content"))
    private String title;

    @CssSelector(value = "span[itemprop=author]>link",
            target = @DomTarget(value = ATTRIBUTE, attributeName = "href"))
    private String author;

    @CssSelector(value = "meta[itemprop=description]",
            target = @DomTarget(value = ATTRIBUTE, attributeName = "content"))
    private String description;

    @CssSelector(value = "meta[itemprop=duration]",
            target = @DomTarget(value = ATTRIBUTE, attributeName = "content"))
    private String duration;
}
