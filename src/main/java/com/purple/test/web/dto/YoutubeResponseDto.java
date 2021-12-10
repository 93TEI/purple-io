package com.purple.test.web.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
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

    public YoutubeResponseDto(String title, String author_name, String author_url, String type, int height, int width, String version, String provider_name, String provider_url, int thumbnail_height, int thumbnail_width, String thumbnail_url, String html) {
        this.title = title;
        this.author_name = author_name;
        this.author_url = author_url;
        this.type = type;
        this.height = height;
        this.width = width;
        this.version = version;
        this.provider_name = provider_name;
        this.provider_url = provider_url;
        this.thumbnail_height = thumbnail_height;
        this.thumbnail_width = thumbnail_width;
        this.thumbnail_url = thumbnail_url;
        this.html = html;
    }
}
