package com.purple.test.web;

import com.purple.test.service.OembedService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiController {
    private final OembedService oembedService;
    private String embedUrl;

    @GetMapping("/response")
    public String result(Model model){
        model.addAttribute("list",oembedService.requestData(embedUrl));
        return "<iframe src=\"https://player.vimeo.com/video/20097015?h=08095da358&amp;app_id=122963\" width=\"640\" height=\"480\" frameborder=\"0\" allow=\"autoplay; fullscreen; picture-in-picture\" allowfullscreen title=\"A Time Artifact - work in progress\"></iframe>";
    }

    @GetMapping("/api/v1/request/{urlStr}")
    public String call(@RequestParam String urlStr, Model model) throws IOException, ParseException {

        System.out.println(urlStr);
        // oEmbed의 엔드포인트
        ArrayList lst = oembedService.requestEndpoint();

        // 입력한 url 매핑하여 oembed url로 바꿔주기
        embedUrl = oembedService.compareUrl(urlStr, lst);

        return "response";
    }
}