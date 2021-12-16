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

    public String compareUrl( ArrayList lst) {
        String urlInput = "https://vimeo.com/20097015"; // GetMapping의 중괄호가 작동하지 않아서 직접 넣어서 테스트 중
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
        return urlStr;
    }

    public List requestData(String urlStr) {
        RestTemplate template = new RestTemplate();
        Map<String, Object> embedResult = template.getForObject(urlStr, Map.class);

        List<ContentData> result = new ArrayList<>();
        for(Map.Entry<String,Object> entry : embedResult.entrySet()){
            result.add(new ContentData(entry.getKey(),entry.getValue().toString()));
        }
        return result;
    }
}
