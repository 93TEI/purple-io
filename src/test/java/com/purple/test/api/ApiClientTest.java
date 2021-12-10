package com.purple.test.api;

import com.purple.test.web.dto.YoutubeResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiClientTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private YoutubeResponseDto dto;

    private final String UserInfoUrl = "https://www.youtube.com/oembed?url=https%3A%2F%2Fyoutu.be%2FZOOeP3SBFIM";

    @Test
    public void nickNameSearch()
    {
        final HttpHeaders httpHeaders = new HttpHeaders();  //HttpHeaders는 서버에 데이터를 보내주는 방법
        final HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        dto = restTemplate.exchange(UserInfoUrl, HttpMethod.GET, entity, YoutubeResponseDto.class).getBody();

        System.out.println(dto);
    }
}
