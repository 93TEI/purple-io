package com.purple.test.web;

import com.purple.test.api.ApiKey;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/response")
    public String result(Model model){
        model.addAttribute("value","value");
        return "response";
    }

    @GetMapping("/api/v1/request/{urlInput}")
    public String call(@PathVariable String urlInput, Model model) throws IOException, ParseException {

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
            String getEndpnt = provider_url.get("endpoints").toString();

            Object objEndpntUrl = jsonParser.parse(getEndpnt);
            JSONArray jsonArray = (JSONArray) objEndpntUrl;
            JSONObject urlData = (JSONObject) jsonArray.get(0);

            String value = (String) urlData.get("url");

            lst.add(value);
        }
        urlEndpoint.disconnect();

        // 입력한 url
        //String urlInput = "https://vimeo.com/20097015";
        String splitUrl[] = urlInput.split("\\.");
        String splitResult = splitUrl.length >= 3 ? splitUrl[1] : splitUrl[0];
        if(splitUrl[0].contains("twitter"))
            splitResult = splitUrl[0].substring(9);
        String urlStr="";
        for(Object objLst : lst){
            String str = objLst.toString();
            if(str.contains(splitResult)){
                if(str.contains("instagram")) {
                    urlStr = str + "?url=" + urlInput + "&access_token=" + ApiKey.apiID + "|" + ApiKey.secretCode;
                    break;
                }
                if(str.contains("oembed.")) {
                    str = str.replace("{format}", "json");
                }
                urlStr=str+"?url="+urlInput;
                break;
            }
        }

        RestTemplate template = new RestTemplate();
        Map<String, Object> embedResult = template.getForObject(urlStr, Map.class);

        //앞단에 데이터 넘기기
        String[] key = new String[embedResult.size()];
        String[] value = new String[embedResult.size()];

        int i=0;
        for(Map.Entry<String,Object> entry : embedResult.entrySet()){
            key[i] = entry.getKey();
            value[i++]=entry.getValue().toString();
        }

        model.addAttribute("value",value);

        return "response";
        //return value[value.length-12]; -12가 영상 viemo

    }
}
