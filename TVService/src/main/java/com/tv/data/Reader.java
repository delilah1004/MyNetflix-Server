package com.tv.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tv.data.StaticData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Reader {

    public BufferedReader br;

    // 공통 함수
    // API URL 반환값 읽어오기
    public void getReader(String url) {

        try {
            URL getTvURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) getTvURL.openConnection();
            conn.setRequestMethod(StaticData.protocol);
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 읽어온 정보 JsonObject 값으로 받기
    public JsonObject getJson() {

        return JsonParser.parseReader(br).getAsJsonObject();
    }

    // 장르 JsonArray 값 받기
    public JsonArray getGenres() {

        return getJson().get("genres").getAsJsonArray();
    }
}
