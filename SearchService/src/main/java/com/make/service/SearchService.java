package com.make.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Service
public class SearchService {

    private static final String mainURL = "https://api.themoviedb.org/3";
    private static final String imageURL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2";
    private static final String API_KEY = "334cc048cf91e9c7e784d8d3241e3b4c";
    private static final String KOREAN = "ko-KR";

    public String getPopularMovieListDesc(int pageNum) {

        String URL = mainURL;
        URL += "/discover/movie";
        URL += "?api_key=" + API_KEY;
        URL += "&language=" + KOREAN;
        URL += "&sort_by=popularity.desc&page";
        URL += "&page=" + pageNum;

        return getJson(URL);
    }

    public String getJson(String URL) {

        String json = null;

        try {
            URL url = new URL(URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            json = br.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    public void printPreviewInfo(String json) throws Exception {

        JsonParser parser = new JsonParser();
        JsonElement obj = parser.parse(json);

        String poster_path = obj.getAsJsonObject().get("poster_path").getAsString();
        String title = obj.getAsJsonObject().get("title").getAsString();
        String date = obj.getAsJsonObject().get("release_date").getAsString().substring(0, 4);
        String overview = obj.getAsJsonObject().get("overview").getAsString();

        System.out.println("포스터 : " + imageURL + poster_path);
        System.out.println("제목 : " + title);
        System.out.println("날짜 : " + date);
        System.out.println("개요 : " + overview);
    }

    public String getMovieById(long n) throws Exception {

        String URL = mainURL;
        URL += "/movie/" + n;
        URL += "?api_key=" + API_KEY;
        URL += "&language=" + KOREAN;

        BufferedReader br;
        URL url;
        HttpURLConnection conn;
        String protocol = "GET";

        url = new URL(URL);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(protocol);
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        return br.readLine();
    }

    public void getMovie(int num) throws Exception {

        String URL, json, date;
        String poster_path, title, overview;
        BufferedReader br;
        URL url;
        HttpURLConnection conn;

        for (int i = 2; i <= 80; i++) {
            URL = "https://api.themoviedb.org/3/movie/" + i + "?api_key=334cc048cf91e9c7e784d8d3241e3b4c&language=ko-KR";
            url = new URL(URL);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            json = br.readLine();

            JsonParser parser = new JsonParser();
            JsonElement obj = parser.parse(json);

            if (obj.getAsJsonObject().get("status_code").getAsInt() == 34) continue;

            date = obj.getAsJsonObject().get("release_date").getAsString().substring(0, 4);

            System.out.println(obj.getAsJsonObject().get("release_date").getAsString());
            System.out.println(date);
        }
    }

    public void getMoviesByYear(long lastId, String year) throws Exception {

        String URL, json, date;
        String poster_path, title, overview;
        BufferedReader br;
        URL url;
        HttpURLConnection conn;

        for (int i = 2; i <= 80; i++) {
            URL = "https://api.themoviedb.org/3/movie/" + i + "?api_key=334cc048cf91e9c7e784d8d3241e3b4c&language=ko-KR";
            url = new URL(URL);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            json = br.readLine();

            JsonParser parser = new JsonParser();
            JsonElement obj = parser.parse(json);

            if (obj.getAsJsonObject().get("status_code").getAsInt() == 34) continue;

            date = obj.getAsJsonObject().get("release_date").getAsString().substring(0, 4);

            if (date.equals(year)) {

                // movie = new MoviePreView();

                poster_path = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + obj.getAsJsonObject().get("poster_path").getAsString();

                System.out.println(poster_path);
                System.out.println(obj.getAsJsonObject().get("title").getAsString());
                System.out.println(obj.getAsJsonObject().get("release_date").getAsString());
                System.out.println(obj.getAsJsonObject().get("overview").getAsString());

                /*
                movie.setPosterPath(poster_path);
                movie.setTitle(obj.getAsJsonObject().get("title").getAsString());
                movie.setDate(obj.getAsJsonObject().get("release_date").getAsString());
                movie.setOverview(obj.getAsJsonObject().get("overview").getAsString());

                movieList.add(movie);
                */
            }
        }
    }

    public ArrayList<String> getAllMovies(long lastId) throws Exception {

        String URL, json;
        BufferedReader br;
        URL url;
        HttpURLConnection conn;

        ArrayList<String> IdList = null;

        for (int i = 2; i <= lastId; i++) {
            URL = "https://api.themoviedb.org/3/movie/" + i + "?api_key=334cc048cf91e9c7e784d8d3241e3b4c&language=ko-KR";
            url = new URL(URL);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            json = br.readLine();

            JsonParser parser = new JsonParser();
            JsonElement obj = parser.parse(json);

            IdList.add(obj.getAsJsonObject().get("id").getAsString());
        }

        return IdList;
    }

    public long getLatestMovieId() throws Exception {

        String URL = "https://api.themoviedb.org/3/movie/latest?api_key=334cc048cf91e9c7e784d8d3241e3b4c&language=ko-KR";

        URL url = new URL(URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String json = br.readLine();

        JsonParser parser = new JsonParser();
        JsonElement obj = parser.parse(json);

        long id = obj.getAsJsonObject().get("id").getAsLong();

        return id;
    }

}