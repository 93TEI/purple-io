package com.purple.test.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purple.test.web.dto.YoutubeResponseDto;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@RestController
public class IndexController {

    @GetMapping("/")
    public String call(Model model) throws IOException, ParseException {
        StringBuilder result = new StringBuilder();
        String inputUrl = "https://youtu.be/ZOOeP3SBFIM";
        String urlStr="https://www.youtube.com/"+"oembed?url="+inputUrl;

        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        String returnLine;

        while((returnLine = br.readLine()) != null){
            result.append(returnLine+"\n\r");
        }
        urlConnection.disconnect();
        String infoResult =result.toString();

        //매핑
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(infoResult, Map.class);
        YoutubeResponseDto dto = new YoutubeResponseDto(map.get("title").toString(),map.get("author_name").toString(),map.get("author_url").toString(),map.get("type").toString(),Integer.parseInt(map.get("height").toString()),
                Integer.parseInt(map.get("width").toString()),map.get("version").toString(),map.get("provider_name").toString(),map.get("provider_url").toString(),Integer.parseInt(map.get("thumbnail_height").toString()),
                Integer.parseInt(map.get("thumbnail_width").toString()),map.get("thumbnail_url").toString(),map.get("html").toString());

        model.addAttribute("youtube",dto);


        //엔드포인트
        URL urlEndpointStr = new URL("https://oembed.com/providers.json");
        HttpURLConnection urlEndpoint = (HttpURLConnection) urlEndpointStr.openConnection();
        urlEndpoint.setRequestMethod("GET");

        BufferedReader br2 = new BufferedReader(new InputStreamReader(urlEndpoint.getInputStream(), "UTF-8"));
        String returnLine2;

        StringBuilder result2 = new StringBuilder();
        while((returnLine2 = br2.readLine()) != null){
            result2.append(returnLine2+"\n\r");
        }
        urlEndpoint.disconnect();
        String endPointResult =result2.toString();



        return endPointResult;

    }
}
