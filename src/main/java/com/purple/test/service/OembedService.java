package com.purple.test.service;

import com.purple.test.api.ApiClient;
import com.purple.test.api.ApiKey;
import com.purple.test.domain.ContentData;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OembedService {
    private final ApiClient apiClient;

    public ArrayList requestEndpoint() throws IOException, ParseException {
        return apiClient.Endpoint();
    }

    public String compareUrl(String urlStr, ArrayList lst) {

        String splitUrl[] = urlStr.split("\\.");
        String splitResult = splitUrl.length >= 3 ? splitUrl[1] : splitUrl[0];
        if(splitUrl[0].contains("twitter"))
            splitResult = splitUrl[0].substring(9);
        String combineUrl="";
        for(Object objLst : lst){
            String str = objLst.toString();
            if(str.contains(splitResult)){
                if(str.contains("instagram")) {
                    combineUrl = str + "?url=" + urlStr + "&access_token=" + ApiKey.apiID + "|" + ApiKey.secretCode;
                    break;
                }
                if(str.contains("oembed.")) {
                    str = str.replace("{format}", "json");
                }
                combineUrl=str+"?url="+urlStr;
                break;
            }
        }
        System.out.println("urlStr :"+urlStr);
        return combineUrl;
    }

    public List requestData(String urlStr) {
        RestTemplate template = new RestTemplate();
        Map<String, Object> embedResult = template.getForObject(urlStr, Map.class);

        List<ContentData> result = new ArrayList<>();
        for(Map.Entry<String,Object> entry : embedResult.entrySet()){
            System.out.println("inside : " + entry.getKey() + entry.getValue());
            if(entry.getKey().contains("title"))
                continue;
            if(entry.getKey().contains("thumbnail_url"))
                result.add(new ContentData(entry.getKey(),"<img src="+entry.getValue().toString()+">"));
            if(entry.getValue() != null)
                result.add(new ContentData(entry.getKey(),entry.getValue().toString()));
        }
        return result;
    }

    public List requestTitle(String urlStr) {
        RestTemplate template = new RestTemplate();
        Map<String, Object> embedResult = template.getForObject(urlStr, Map.class);

        List<ContentData> result = new ArrayList<>();
        for(Map.Entry<String,Object> entry : embedResult.entrySet()){
            System.out.println("title:"+ entry.getKey());
            if(entry.getKey().contains("title"))
                result.add(new ContentData(entry.getKey(),entry.getValue().toString()));
        }
        return result;
    }
}
