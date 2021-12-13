package com.purple.test.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purple.test.api.ApiKey;
import com.purple.test.web.dto.YoutubeResponseDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @GetMapping("/")
    public String call(Model model) throws IOException, ParseException {

        //
        String a = ApiKey.apiKey;

        //엔드포인트
        URL urlEndpointStr = new URL("https://oembed.com/providers.json");
        HttpURLConnection urlEndpoint = (HttpURLConnection) urlEndpointStr.openConnection();
        urlEndpoint.setRequestMethod("GET");

        BufferedReader rd = new BufferedReader(new InputStreamReader(urlEndpoint.getInputStream(), "UTF-8"));

        ArrayList lst = new ArrayList<String>();
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(rd);
        JSONArray array = (JSONArray) obj;

        for(int i=0; i< array.size(); i++){
            JSONObject provider_url = (JSONObject) array.get(i);
            String getEndpnt = (String) provider_url.get("endpoints").toString();

            Object objEndpntUrl = jsonParser.parse(getEndpnt);
            JSONArray jsonArray = new JSONArray();
            jsonArray = (JSONArray) objEndpntUrl;
            JSONObject urlData = (JSONObject) jsonArray.get(0);

            String value = (String) urlData.get("url");

            lst.add(value);
        }
        urlEndpoint.disconnect();

        // 입력한 url
        StringBuilder result = new StringBuilder();
        String inputUrl = "https://www.instagram.com/p/BUawPlPF_Rx/&access_token=1165265950291154|23cf7ce9e94d9d59740e5a7ea86f517d";
        String splitUrl[] = inputUrl.split("\\.");
        String splitResult = splitUrl.length >= 3 ? splitUrl[1] : splitUrl[0];
        String urlStr="";
        for(Object objLst : lst){
            String str = objLst.toString();
            if(str.contains(splitResult)){
                if(str.contains("oembed."))
                    str = str.replace("{format}", "json");
                urlStr=str+"?url="+inputUrl;
                break;
            }
        }

        Map<String, Object> embedResult = new HashMap<>();
        RestTemplate template = new RestTemplate();
        embedResult = template.getForObject(urlStr, Map.class);
        return embedResult.toString();

    }
}
