package com.make.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.make.model.MoviePreView;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Service
public class SearchService {

    public void getMovies(String year) throws Exception {

        ArrayList<MoviePreView> movieList = getMoviesByYear(getLatestMovieId(), year);

        for (MoviePreView movie : movieList) {
            System.out.println("포스터 URL : " + movie.getPosterPath());
            System.out.println("제목 : " + movie.getTitle());
            System.out.println("날짜 : " + movie.getDate());
            System.out.println("개요 : " + movie.getOverview());
            System.out.println("\n--------------------------------------\n");
        }
    }

    public void getMovie(int num) throws Exception {

        String URL, json, date;
        String poster_path, title, overview;
        BufferedReader br;
        URL url;
        HttpURLConnection conn;

        MoviePreView movie;
        ArrayList<MoviePreView> movieList = null;

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

    public ArrayList<MoviePreView> getMoviesByYear(long lastId, String year) throws Exception {

        String URL, json, date;
        String poster_path, title, overview;
        BufferedReader br;
        URL url;
        HttpURLConnection conn;

        MoviePreView movie;
        ArrayList<MoviePreView> movieList = null;

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

        return movieList;
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