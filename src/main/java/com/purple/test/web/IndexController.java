package com.purple.test.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purple.test.web.dto.YoutubeResponseDto;
import org.json.simple.parser.ParseException;
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
    public String callApi() throws IOException, ParseException {
        StringBuilder result = new StringBuilder();

            String urlStr="https://www.youtube.com/oembed?url=https%3A%2F%2Fyoutu.be%2FZOOeP3SBFIM";
            URL url = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        String returnLine;

        while((returnLine = br.readLine()) != null){
            result.append(returnLine+"\n\r");
        }

        urlConnection.disconnect();
        String ss =result.toString();

        /*
        // utf8 안먹어서 직접 변환해줌 -> Mapping을 진행하니 오류가 났음. 브라우저엔 유니코드가 그대로 나왔지만 사실은 UTF-8변환이 제대로 됐던 것 같다.
        StringBuffer temp = new StringBuffer();

        for(int i=0; i<ss.length(); i++){
            if(ss.charAt(i) == '\\'  &&  ss.charAt(i+1) == 'u'){
                Character c = (char)Integer.parseInt(ss.substring(i+2, i+6), 16);
                temp.append(c);
                i+=5;
            }else{
                temp.append(ss.charAt(i));
            }
        }
        String sss = temp.toString();
         */
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(ss, Map.class);
        YoutubeResponseDto dto = new YoutubeResponseDto(map.get("title").toString(),map.get("author_name").toString(),map.get("author_url").toString(),map.get("type").toString(),Integer.parseInt(map.get("height").toString()),
                Integer.parseInt(map.get("width").toString()),map.get("version").toString(),map.get("provider_name").toString(),map.get("provider_url").toString(),Integer.parseInt(map.get("thumbnail_height").toString()),
                Integer.parseInt(map.get("thumbnail_width").toString()),map.get("thumbnail_url").toString(),map.get("html").toString());

        return dto.toString();
    }
}
