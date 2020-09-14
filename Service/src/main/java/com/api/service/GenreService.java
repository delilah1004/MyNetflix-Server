package com.api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.api.data.Reader;
import com.api.data.StaticData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GenreService extends Reader {

    private String url = StaticData.API_MAIN_URL;


    /* ------    TV Program     ------- */

    // TV 프로그램의 모든 장르명 반환
    public ArrayList<String> getTVGenreNames() {

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
    public int getTVGenreId(String genreName) {

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

    // 장르 id 로 장르명 반환
    public String getTVGenreName(int genreId) {

        String genreName = null;

        url += "/genre/tv/list";
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader(url);
        JsonArray genres = getGenres();

        for (JsonElement element : genres) {
            if (genreId == element.getAsJsonObject().get("id").getAsInt()) {
                genreName = element.getAsJsonObject().get("name").getAsString();
                break;
            }
        }

        return genreName;
    }



    /* ------    Movie     ------- */


    // TV 프로그램의 모든 장르명 반환
    public ArrayList<String> getMovieGenreNames() {

        ArrayList<String> genreNames = new ArrayList<>();

        url += "/genre/movie/list";
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
    public int getMovieGenreId(String genreName) {

        int genreId = 0;

        url += "/genre/movie/list";
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

    // 장르 id 로 장르명 반환
    public String getMovieGenreName(int genreId) {

        String genreName = null;

        url += "/genre/movie/list";
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader(url);
        JsonArray genres = getGenres();

        for (JsonElement element : genres) {
            if (genreId == element.getAsJsonObject().get("id").getAsInt()) {
                genreName = element.getAsJsonObject().get("name").getAsString();
                break;
            }
        }

        return genreName;
    }

}
