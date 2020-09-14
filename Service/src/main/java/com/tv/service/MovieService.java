package com.tv.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tv.data.Reader;
import com.tv.data.StaticData;
import com.tv.model.Movie;
import com.tv.model.TVProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Service
public class MovieService extends Reader{

    @Autowired
    AllService allService;

    // 한 페이지에 띄울 영화의 개수
    private final int count = 15;

    // 넷플릭스에서 방영되는 모든 TV Program 목록 반환
    public ArrayList<Movie> getAllMovies(int pageNumber) {

        // allTvIdList 초기화
        ArrayList<Long> allMovieIdList = allService.getAllMovieIds();

        // 매칭된 tvId 만 담을 list 초기화
        ArrayList<Long> movieIdList = new ArrayList<>();

        // 검색을 시작할 인덱스
        int startIndex = (pageNumber - 1) * count;

        //for (long movieId : movieIdList) {
        for (int i = startIndex; i < startIndex + count; i++) {
            movieIdList.add(allMovieIdList.get(i));
        }

        return allService.getMovieList(movieIdList);
    }

}
