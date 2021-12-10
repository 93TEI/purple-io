package com.purple.test.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Youtube {
    @Id
    @Column
    private String title;
    @Column
    private String author_name;
    @Column
    private String author_url;
    @Column
    private String type;
    @Column
    private int height;
    @Column
    private int width;

    @Column
    private String version;
    @Column
    private String provider_name;
    @Column
    private String provider_url;
    @Column
    private int thumbnail_height;
    @Column
    private int thumbnail_width;
    @Column
    private String thumbnail_url;
    @Column
    private String html;

    @Builder
    public Youtube(String title, String author_name, String author_url, String type, int height, int width, String version, String provider_name, String provider_url, int thumbnail_height, int thumbnail_width, String thumbnail_url, String html) {
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
