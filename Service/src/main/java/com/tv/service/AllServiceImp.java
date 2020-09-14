package com.tv.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tv.data.Reader;
import com.tv.data.StaticData;
import com.tv.model.Movie;
import com.tv.model.TVProgram;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Component
public class AllServiceImp extends Reader implements AllService{

    // 파일을 불러와 allMovieIdList 정보 업데이트
    // 넷플릭스에서 방영되는 모든 영화들의 movie_id 목록 반환
    public ArrayList<Long> getAllMovieIds() {

        FileReader fr = null;
        BufferedReader br = null;
        StringTokenizer st;

        String line;
        ArrayList<Long> allMovieIdList = new ArrayList<>();

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
    public JsonObject getMovieById(long id) {

        String url = StaticData.API_MAIN_URL;
        url += "/movie/" + id;
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader(url);

        return getJson();
    }

    // movieIdList 에 포함된 영화들의 정보 리스트 반환
    public ArrayList<Movie> getMovieList(ArrayList<Long> movieIdList) {

        // 반환값을 담을 Movie 리스트 선언
        ArrayList<Movie> movies = new ArrayList<>();
        // genre 정보를 담을 리스트 선언
        ArrayList<Integer> genres;

        for (long movieId : movieIdList) {

            try {
                JsonObject mv = getMovieById(movieId);

                Movie movie = new Movie();

                mv.get("id").getAsLong();

                movies.add(movie);

            } catch (Exception e) {
                System.out.println(movieId);
                e.printStackTrace();
            }
        }

        return movies;
    }

    // 파일을 불러와 allTvIdList 정보 업데이트
    // 넷플릭스에서 방영되는 모든 TV Program 들의 tv_id 목록 반환
    public ArrayList<Long> getAllTVIds() {

        FileReader fr = null;
        BufferedReader br = null;
        StringTokenizer st;

        String line;
        ArrayList<Long> allTvIdList = new ArrayList<>();

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
    public JsonObject getTVById(long id) {

        String url = StaticData.API_MAIN_URL;
        url += "/tv/" + id;
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader(url);

        return getJson();
    }

    // 인기 TV 프로그램의 모든 정보 JsonObject 로 반환
    public JsonArray getPopularTVProgramIdList(int pageNumber) {

        String url = StaticData.API_MAIN_URL;
        url += "/tv/popular";
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;
        url += "&page=" + pageNumber;

        getReader(url);

        return getJson().get("results").getAsJsonArray();
    }

    // tvIdList 에 포함된 TV Program 들의 정보 리스트 반환
    public ArrayList<TVProgram> getTVProgramList(ArrayList<Long> tvIdList) {

        // 반환값을 담을 TVProgram 리스트 선언
        ArrayList<TVProgram> tvPrograms = new ArrayList<>();
        // season 정보와 genre 정보를 담을 리스트 선언
        ArrayList<Integer> seasons, genres;

        for (long tvId : tvIdList) {

            try {
                JsonObject tv = getTVById(tvId);

                TVProgram tvProgram = new TVProgram();

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
                genres = new ArrayList<>();

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
                seasons = new ArrayList<>();

                for (JsonElement element : tv.get("seasons").getAsJsonArray()) {
                    seasons.add(element.getAsJsonObject().get("season_number").getAsInt());
                }

                tvProgram.setSeasons(seasons);

                // 종영 여부
                tvProgram.setStatus(tv.get("status").getAsString());

                tvPrograms.add(tvProgram);
            } catch (Exception e) {
                System.out.println(tvId);
                e.printStackTrace();
            }
        }

        return tvPrograms;
    }


}