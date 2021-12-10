package com.purple.test.api;

import com.purple.test.web.dto.YoutubeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ApiClient {

    private final RestTemplate restTemplate = new RestTemplate(); // new RestTemplate()를 붙여주니까 오류가 해결됨

    public YoutubeResponseDto requestUserInfo() {
        String UserInfoUrl = "https://www.youtube.com/oembed?url=https%3A%2F%2Fyoutu.be%2FZOOeP3SBFIM";

        return restTemplate.getForObject(UserInfoUrl,YoutubeResponseDto.class);
    }
}