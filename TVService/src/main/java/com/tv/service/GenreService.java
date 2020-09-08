package com.tv.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tv.data.StaticData;
import com.tv.model.TVProgram;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Service
public class GenreService extends Reader{

    private String url = StaticData.API_MAIN_URL;

    // TV 프로그램의 모든 장르명 반환
    public ArrayList<String> getGenreNames() {

        ArrayList<String> genreNames = new ArrayList<>();

        url += "/genre/tv/list";
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader(url);
        JsonArray genres = getGenres();

        for (JsonElement element : genres) {
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

        getReader(url);
        JsonArray genres = getGenres();

        for (JsonElement element : genres) {
            if (genreName.equals(element.getAsJsonObject().get("name").getAsString())) {
                genreId = element.getAsJsonObject().get("id").getAsInt();
                break;
            }
        }

        return genreId;
    }

}
