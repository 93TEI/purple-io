package com.purple.test.api;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class ApiClient {

    public ArrayList Endpoint() throws IOException, ParseException {
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
        return lst;
    }
}