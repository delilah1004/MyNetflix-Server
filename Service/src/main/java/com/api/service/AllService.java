package com.api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.api.model.Movie;
import com.api.model.TVProgram;

import java.util.ArrayList;

public interface AllService {
    
    // 영화
    public ArrayList<Long> getAllMovieIds();

    public JsonObject getMovieById(long id);

    public ArrayList<Movie> getMovieList(ArrayList<Long> movieIdList);

    public JsonArray getPopularMovieIdList(int pageNumber);
    

    // TV 프로그램
    public ArrayList<Long> getAllTVIds();

    public JsonObject getTVById(long id);

    public ArrayList<TVProgram> getTVProgramList(ArrayList<Long> tvIdList);

    public JsonArray getPopularTVProgramIdList(int pageNumber);

    public JsonArray getNowPlayingTVProgramIdList(int pageNumber);
}
