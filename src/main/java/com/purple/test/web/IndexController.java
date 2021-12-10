package com.purple.test.web;

import org.h2.engine.Domain;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

        // utf8 안먹어서 직접 변환해줌
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

        //자르기
        JSONObject jObj;
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj=(JSONObject) jsonParser.parse(sss);
        JSONObject parseResponse = (JSONObject) jsonObj.get("response");

        JSONObject parseBody = (JSONObject) parseResponse.get("body");

        JSONArray array = (JSONArray) parseBody.get("items");

        //도메인 매핑
        for(int i=0; i<array.size();i++){
            jObj=(JSONObject)array.get(i);

            //도메인클래스 빌더패턴으로 값 삽입

        }

        return sss;
    }
}
