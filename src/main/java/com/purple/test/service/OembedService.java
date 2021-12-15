package com.purple.test.service;

import com.purple.test.api.ApiClient;
import com.purple.test.api.ApiKey;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OembedService {
    private final ApiClient apiClient;

    public ArrayList requestEndpoint() throws IOException, ParseException {
        return apiClient.Endpoint();
    }

    public String compareUrl(ArrayList lst) {
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

    public void requestData(String urlStr) {
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
    }

    public void getData() {

    }


}
