package com.api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.api.data.Reader;
import com.api.data.StaticData;
import com.api.model.Movie;
import com.api.model.TVProgram;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Component
public class AllServiceImp extends Reader implements AllService {

    /* ------    Movie     ------- */

    // 파일을 불러와 allMovieIdList 정보 업데이트
    // 넷플릭스에서 방영되는 모든 영화들의 movie_id 목록 반환
    public ArrayList<Long> getAllMovieIds() {

        FileReader fr = null;
        BufferedReader br = null;
        StringTokenizer st;

        String line;
        ArrayList<Long> allMovieIdList = new ArrayList<Long>();

        try {
            fr = new FileReader(new File(StaticData.MOVIE_ID_LIST_FILE_PATH));
            br = new BufferedReader(fr);

            // file 한줄씩 읽기
            while ((line = br.readLine()) != null) {

                // StringTokenizer 에 한 줄 담기
                st = new StringTokenizer(line);

                // StringTokenizer 에 담긴 토큰을 list 에 추가
                while (st.hasMoreTokens()) {
                    allMovieIdList.add(Long.parseLong(st.nextToken()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                if (fr != null) fr.close();
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return allMovieIdList;
    }

    // id 로 프로그램의 모든 정보 JsonObject 로 반환
    public JsonObject getMovieJsonById(long id) {

        String url = StaticData.API_MAIN_URL;
        url += "/movie/" + id;
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader(url);

        return getJson();
    }

    // id 로 프로그램의 모든 정보 Movie 객체로 반환
    public Movie getMovieById(long id) {

        // 반환값을 담을 Movie 객체 선언
        Movie movie = new Movie();
        // genre 정보를 담을 리스트 선언
        ArrayList<Integer> genres;

        try {

            JsonObject mv = getMovieJsonById(id);

            // movie_id
            movie.setId(mv.get("id").getAsLong());

            // 제목
            movie.setTitle(mv.get("title").getAsString());

            // 영상 길이
            try {
                movie.setRuntime(mv.get("runtime").getAsInt());
            } catch (Exception e) {
                movie.setRuntime(0);
            }

            // 장르
            genres = new ArrayList<Integer>();

            for (JsonElement element : mv.get("genres").getAsJsonArray()) {
                genres.add(element.getAsJsonObject().get("id").getAsInt());
            }

            movie.setGenres(genres);

            // 개요
            movie.setOverview(mv.get("overview").getAsString());

            // 포스터 URI
            try {
                movie.setPosterPath(mv.get("poster_path").getAsString());
            } catch (Exception e) {
                movie.setPosterPath(null);
            }

            // 영상 스트리밍 URL
            try {
                movie.setHomepage(mv.get("homepage").getAsString());
            } catch (Exception e) {
                movie.setHomepage(null);
            }

            // 방영일 정보
            try {
                movie.setReleaseDate(mv.get("release_date").getAsString());
            } catch (Exception e) {
                movie.setReleaseDate(null);
            }

            // 인기도
            movie.setPopularity(mv.get("popularity").getAsDouble());

            // 종영 여부
            movie.setStatus(mv.get("status").getAsString());

        } catch (Exception e) {
            System.out.println(id);
            e.printStackTrace();
        }

        return movie;
    }

    // movieIdList 에 포함된 영화들의 정보 리스트 반환
    public ArrayList<Movie> getMovieList(ArrayList<Long> movieIdList) {

        // 반환값을 담을 Movie 리스트 선언
        ArrayList<Movie> movies = new ArrayList<Movie>();

        for (long movieId : movieIdList) {
            movies.add(getMovieById(movieId));
        }

        return movies;
    }

    // 인기 영화의 모든 정보 JsonArray 로 반환
    public JsonArray getPopularMovieIdList(int pageNumber) {

        String url = StaticData.API_MAIN_URL;
        url += "/movie/popular";
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;
        url += "&page=" + pageNumber;

        getReader(url);

        return getJson().get("results").getAsJsonArray();
    }


    /* ------    TV Program     ------- */

    // 파일을 불러와 allTvIdList 정보 업데이트
    // 넷플릭스에서 방영되는 모든 TV Program 들의 tv_id 목록 반환
    public ArrayList<Long> getAllTVIds() {

        FileReader fr = null;
        BufferedReader br = null;
        StringTokenizer st;

        String line;
        ArrayList<Long> allTvIdList = new ArrayList<Long>();

        try {
            fr = new FileReader(new File(StaticData.TV_ID_LIST_FILE_PATH));
            br = new BufferedReader(fr);

            // file 한줄씩 읽기
            while ((line = br.readLine()) != null) {

                // StringTokenizer 에 한 줄 담기
                st = new StringTokenizer(line);

                // StringTokenizer 에 담긴 토큰을 list 에 추가
                while (st.hasMoreTokens()) {
                    allTvIdList.add(Long.parseLong(st.nextToken()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                if (fr != null) fr.close();
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return allTvIdList;
    }

    // id 로 TV 프로그램의 모든 정보 JsonObject 로 반환
    public JsonObject getTVJsonById(long id) {

        String url = StaticData.API_MAIN_URL;
        url += "/tv/" + id;
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader(url);

        return getJson();
    }

    // id 로 TV 프로그램의 모든 정보 TVProgram 객체로 반환
    public TVProgram getTVById(long id) {

        // 반환값을 담을 TVProgram 객체 선언
        TVProgram tvProgram = new TVProgram();
        // season 정보와 genre 정보를 담을 리스트 선언
        ArrayList<Integer> seasons, genres;

        try {

            JsonObject tv = getTVJsonById(id);

            // tv_id
            tvProgram.setId(tv.get("id").getAsLong());

            // 제목
            tvProgram.setName(tv.get("name").getAsString());
            // 영상 길이
            try {
                tvProgram.setEpisodeRunTime(tv.get("episode_run_time").getAsInt());
            } catch (Exception e) {
                tvProgram.setEpisodeRunTime(0);
            }

            // 장르
            genres = new ArrayList<Integer>();

            for (JsonElement element : tv.get("genres").getAsJsonArray()) {
                genres.add(element.getAsJsonObject().get("id").getAsInt());
            }

            tvProgram.setGenres(genres);

            // 개요
            tvProgram.setOverview(tv.get("overview").getAsString());

            // 포스터 URI
            try {
                tvProgram.setPosterPath(tv.get("poster_path").getAsString());
            } catch (Exception e) {
                tvProgram.setPosterPath(null);
            }

            // 영상 스트리밍 URL
            tvProgram.setHomepage(tv.get("homepage").getAsString());

            // 방영일 정보
            try {
                tvProgram.setFirstAirDate(tv.get("first_air_date").getAsString());
            } catch (Exception e) {
                tvProgram.setFirstAirDate(null);
            }

            try {
                tvProgram.setLastAirDate(tv.get("last_air_date").getAsString());
            } catch (Exception e) {
                tvProgram.setLastAirDate(null);
            }

            // 인기도
            tvProgram.setPopularity(tv.get("popularity").getAsDouble());

            // 시즌 정보
            seasons = new ArrayList<Integer>();

            for (JsonElement element : tv.get("seasons").getAsJsonArray()) {
                seasons.add(element.getAsJsonObject().get("season_number").getAsInt());
            }

            tvProgram.setSeasons(seasons);

            // 종영 여부
            tvProgram.setStatus(tv.get("status").getAsString());

        } catch (Exception e) {
            System.out.println(id);
            e.printStackTrace();
        }

        return tvProgram;
    }

    // tvIdList 에 포함된 TV Program 들의 정보 리스트 반환
    public ArrayList<TVProgram> getTVProgramList(ArrayList<Long> tvIdList) {

        // 반환값을 담을 TVProgram 리스트 선언
        ArrayList<TVProgram> tvPrograms = new ArrayList<TVProgram>();

        for (long tvId : tvIdList) {
            tvPrograms.add(getTVById(tvId));
        }

        return tvPrograms;
    }

    // 인기 TV 프로그램의 모든 정보 JsonArray 로 반환
    public JsonArray getPopularTVProgramIdList(int pageNumber) {

        String url = StaticData.API_MAIN_URL;
        url += "/tv/popular";
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;
        url += "&page=" + pageNumber;

        getReader(url);

        return getJson().get("results").getAsJsonArray();
    }

    // 최신 TV 프로그램의 모든 정보 JsonArray 로 반환
    public JsonArray getOnTheAirTVProgramIdList(int pageNumber) {

        String url = StaticData.API_MAIN_URL;
        url += "/tv/on_the_air";
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;
        url += "&page=" + pageNumber;

        getReader(url);

        return getJson().get("results").getAsJsonArray();
    }

}
