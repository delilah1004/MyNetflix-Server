package com.api.service;

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

    // 최신 영화

    // 인기 영화
    public ArrayList<Movie> getBestPopularMovies() {

        return allService.getMovieList(getBestPopularMovieIds());
    }

    // 인기 영화 Id 반환
    public ArrayList<Long> getBestPopularMovieIds() {

        ArrayList<Long> allMovieIdList = allService.getAllMovieIds();
        ArrayList<Long> popularMovieIdList = new ArrayList<>();

        for(int i=1; i<6; i++) {

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

    // 최신 TV 프로그램
    public ArrayList<TVProgram> getNowPlayingTVPrograms() {

        return allService.getTVProgramList(getNowPlayingTVIds());
    }

    // 최신 TV 프로그램 Id 반환
    public ArrayList<Long> getNowPlayingTVIds() {

        ArrayList<Long> allTvIdList = allService.getAllTVIds();
        ArrayList<Long> nowPlayingTvIdList = new ArrayList<>();

        for(int i=1; i<6; i++) {

            JsonArray results = allService.getNowPlayingTVProgramIdList(i);

            for (JsonElement element : results) {
                long id = element.getAsJsonObject().get("id").getAsLong();

                if (allTvIdList.contains(id)) nowPlayingTvIdList.add(id);

                if (nowPlayingTvIdList.size() == count) break;
            }

            if (nowPlayingTvIdList.size() == count) break;
        }

        return nowPlayingTvIdList;
    }

    // 인기 TV 프로그램
    public ArrayList<TVProgram> getBestPopularTVPrograms() {

        return allService.getTVProgramList(getBestPopularTVIds());
    }

    // 인기 TV 프로그램 Id 반환
    public ArrayList<Long> getBestPopularTVIds() {

        ArrayList<Long> allTvIdList = allService.getAllTVIds();
        ArrayList<Long> popularTvIdList = new ArrayList<>();

        for(int i=1; i<6; i++) {

            JsonArray results = allService.getPopularTVProgramIdList(i);

            for (JsonElement element : results) {
                long id = element.getAsJsonObject().get("id").getAsLong();

                if (allTvIdList.contains(id)) popularTvIdList.add(id);

                if (popularTvIdList.size() == count) break;
            }

            if (popularTvIdList.size() == count) break;
        }

        return popularTvIdList;
    }

}
