package com.tv.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tv.data.StaticData;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class TestService {

    private JsonObject json;

    public JsonObject getTV() {

        BufferedReader br;
        URL getTvURL;
        HttpURLConnection conn;

        String url = StaticData.API_MAIN_URL;
        url += "/tv/63174";
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        try {
            getTvURL = new URL(url);
            conn = (HttpURLConnection) getTvURL.openConnection();
            conn.setRequestMethod(StaticData.protocol);
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            json = JsonParser.parseReader(br).getAsJsonObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(json);

        return json;
    }
}
