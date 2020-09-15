package com.api.service;

import com.api.data.StaticData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.api.model.Movie;
import com.api.model.TVProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HomeService{

    @Autowired
    AllService allService;

    // 콘텐츠 개수
    private static final int count = 12;
    // 페이지 수
    private static final int page = 6;

    /* ------    Movie     ------- */

    // 인기 영화
    public ArrayList<Movie> getBestPopularMovies() {

        return allService.getMovieList(getBestPopularMovieIds());
    }

    // 인기 영화 Id 반환 (6페이지 순회) - 12개
    public ArrayList<Long> getBestPopularMovieIds() {

        ArrayList<Long> allMovieIdList = allService.getMovieIds(StaticData.MOVIE_ID_LIST_FILE_PATH);
        ArrayList<Long> popularMovieIdList = new ArrayList<>();

        for(int i=1; i<page; i++) {

            JsonArray results = allService.getPopularMovieIdList(i);

            for (JsonElement element : results) {
                long id = element.getAsJsonObject().get("id").getAsLong();

                if (allMovieIdList.contains(id)) popularMovieIdList.add(id);

                if (popularMovieIdList.size() == count) break;
            }

            if (popularMovieIdList.size() == count) break;
        }

        return popularMovieIdList;
    }

    // 최신 영화
    public ArrayList<Movie> getNowPlayingMovies() {

        return allService.getMovieList(getNowPlayingMovieIds());
    }

    // 최신 영화 Id 반환 (6페이지 순회) - 12개
    public ArrayList<Long> getNowPlayingMovieIds() {

        ArrayList<Long> allMovieIdList = allService.getMovieIds(StaticData.MOVIE_ID_LIST_FILE_PATH);
        ArrayList<Long> noePlayingMovieIdList = new ArrayList<Long>();

        for(int i=1; i<page; i++) {

            JsonArray results = allService.getNowPlayingMovieIdList(i);

            for (JsonElement element : results) {
                long id = element.getAsJsonObject().get("id").getAsLong();

                if (allMovieIdList.contains(id)) noePlayingMovieIdList.add(element.getAsJsonObject().get("id").getAsLong());

                if (noePlayingMovieIdList.size() == count) break;
            }

            if (noePlayingMovieIdList.size() == count) break;
        }

        return noePlayingMovieIdList;
    }


    /* ------    TV Program     ------- */

    // 인기 TV 프로그램
    public ArrayList<TVProgram> getBestPopularTVPrograms() {

        return allService.getTVProgramList(getBestPopularTVIds());
    }

    // 인기 TV 프로그램 Id 반환 (6페이지 순회) - 12개
    public ArrayList<Long> getBestPopularTVIds() {

        ArrayList<Long> allTvIdList = allService.getTVIds(StaticData.TV_ID_LIST_FILE_PATH);
        ArrayList<Long> popularTvIdList = new ArrayList<Long>();

        for(int i=1; i<=page; i++) {

            JsonArray results = allService.getPopularTVProgramIdList(i);

            for (JsonElement element : results) {
                long id = element.getAsJsonObject().get("id").getAsLong();

                if (allTvIdList.contains(id)) popularTvIdList.add(element.getAsJsonObject().get("id").getAsLong());

                if (popularTvIdList.size() == count) break;
            }

            if (popularTvIdList.size() == count) break;
        }

        return popularTvIdList;
    }

    // 최신 TV 프로그램
    public ArrayList<TVProgram> getOnTheAirTVPrograms() {

        return allService.getTVProgramList(getOnTheAirTVIds());
    }

    // 최신 TV 프로그램 Id 반환 (6페이지 순회) - 12개
    public ArrayList<Long> getOnTheAirTVIds() {

        ArrayList<Long> allTvIdList = allService.getTVIds(StaticData.TV_ID_LIST_FILE_PATH);
        ArrayList<Long> onTheAirTvIdList = new ArrayList<>();

        for(int i=1; i<10; i++) {

            JsonArray results = allService.getOnTheAirTVProgramIdList(i);

            for (JsonElement element : results) {
                long id = element.getAsJsonObject().get("id").getAsLong();

                if (allTvIdList.contains(id)) onTheAirTvIdList.add(id);

                if (onTheAirTvIdList.size() == count) break;
            }

            if (onTheAirTvIdList.size() == count) break;
        }

        return onTheAirTvIdList;
    }

}
