package com.purple.test.web;

import com.purple.test.service.OembedService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final OembedService oembedService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/response")
    public String result(Model model){
        model.addAttribute("value","value");
        return "response";
    }

    @GetMapping("/api/v1/request/https://vimeo.com/20097015")
    public String call(Model model) throws IOException, ParseException {

        // oEmbed의 엔드포인트
        ArrayList lst = oembedService.requestEndpoint();

        // 입력한 url 매핑하여 oembed url로 바꿔주기
        String urlStr = oembedService.compareUrl(lst);

        // 데이터 요청과 저장
        oembedService.requestData(urlStr);
        return "response";
    }
}
