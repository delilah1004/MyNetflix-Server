package com.tv.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tv.model.Movie;
import com.tv.model.TVProgram;

import java.util.ArrayList;

public interface AllService {
    
    // 영화
    public ArrayList<Long> getAllMovieIds();

    public JsonObject getMovieById(long id);

    public ArrayList<Movie> getMovieList(ArrayList<Long> movieIdList);
    

    // TV 프로그램
    public ArrayList<Long> getAllTVIds();

    public JsonObject getTVById(long id);

    public JsonArray getPopularTVProgramIdList(int pageNumber);

    public ArrayList<TVProgram> getTVProgramList(ArrayList<Long> tvIdList);
}
