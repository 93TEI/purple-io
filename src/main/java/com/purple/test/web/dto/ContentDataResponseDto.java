package com.purple.test.web.dto;

import com.purple.test.domain.ContentData;
import lombok.*;

@Data
@Getter
public class ContentDataResponseDto {
    private String key;
    private String value;

    public ContentData toEntity()
    {
        return ContentData.builder()
                .key(key)
                .value(value)
                .build();
    }
}
