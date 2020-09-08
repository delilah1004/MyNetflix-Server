package com.tv.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tv.data.StaticData;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Service
public class GenreService {

    private String url = StaticData.API_MAIN_URL;
    private BufferedReader br;
    private JsonArray genres = null;

    // TV 프로그램의 모든 장르명 반환
    public ArrayList<String> getGenreNames() {

        ArrayList<String> genreNames = new ArrayList<>();

        url += "/genre/tv/list";
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader();
        getGenres();

        for(JsonElement element : genres){
            genreNames.add(element.getAsJsonObject().get("name").getAsString());
        }

        return genreNames;
    }

    // 장르명으로 장르 id 반환
    public int getGenreId(String genreName) {

        int genreId = 0;

        url += "/genre/tv/list";
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader();
        getGenres();

        for(JsonElement element : genres){
            if(genreName.equals(element.getAsJsonObject().get("name").getAsString())){
                genreId = element.getAsJsonObject().get("id").getAsInt();
                break;
            }
        }

        return genreId;
    }

    // 공통 함수
    // API URL 반환값 읽어오기
    public void getReader() {

        try {
            URL getTvURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) getTvURL.openConnection();
            conn.setRequestMethod(StaticData.protocol);
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 장르 JsonArray 값 받기
    public void getGenres() {

        genres = JsonParser.parseReader(br).getAsJsonObject().get("genres").getAsJsonArray();
    }

}
