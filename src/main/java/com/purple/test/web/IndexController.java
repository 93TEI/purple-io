package com.purple.test.web;

import com.purple.test.service.OembedService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final OembedService oembedService;
    private String embedUrl;

    @GetMapping("/")
    public String index(){
        return "response";
    }

    @GetMapping("/response")
    public String result(Model model){
        model.addAttribute("title",oembedService.requestTitle(embedUrl));
        model.addAttribute("list",oembedService.requestData(embedUrl));
        return "response";
    }

    @GetMapping("/api/v1/request/{urlStr}")
    public String callUrl(@PathVariable String urlStr, Model model) throws IOException, ParseException {
        urlStr = urlStr.replaceAll(":t:e:i:slash","/");
        urlStr = urlStr.replaceAll(":t:e:i:qustion","?");

        // oEmbed의 엔드포인트
        ArrayList lst = oembedService.requestEndpoint();

        // 입력한 url 매핑하여 oembed url로 바꿔주기
        embedUrl = oembedService.compareUrl(urlStr, lst);
        return "index";
    }

}
