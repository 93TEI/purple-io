package com.purple.test.web.dto;

import com.purple.test.domain.Youtube;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class YoutubeResponseDto {
    private String title;
    private String author_name;
    private String author_url;
    private String type;
    private int height;
    private int width;
    private String version;
    private String provider_name;
    private String provider_url;
    private int thumbnail_height;
    private int thumbnail_width;
    private String thumbnail_url;
    private String html;

    public Youtube toEntity(){
        return Youtube.builder()
                .title(title)
                .author_name(author_name)
                .author_url(author_url)
                .type(type)
                .height(height)
                .width(width)
                .version(version)
                .provider_name(provider_name)
                .provider_url(provider_url)
                .thumbnail_height(thumbnail_height)
                .thumbnail_width(thumbnail_width)
                .thumbnail_url(thumbnail_url)
                .html(html).build();
    }
}
