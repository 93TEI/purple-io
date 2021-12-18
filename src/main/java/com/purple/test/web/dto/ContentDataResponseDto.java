package com.purple.test.web.dto;

import com.purple.test.domain.ContentData;
import lombok.*;

@Data
@Getter
public class ContentDataResponseDto {
    private String key;
    private String value;
}
