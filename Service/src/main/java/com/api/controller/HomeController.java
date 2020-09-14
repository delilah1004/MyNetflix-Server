package com.api.controller;

import com.api.model.Movie;
import com.api.model.TVProgram;
import com.api.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class HomeController {
    
    @Autowired
    HomeService homeService;
    
    // 최신 영화 6개
    
    // 인기 영화 12개
    @GetMapping("home/movie/popular")
    public ArrayList<Movie> getBestPopularMovies(){

        return homeService.getBestPopularMovies();
    }
    
    // 최신 TV 프로그램 6개
    
    // 인기 TV 프로그램 12개
    @GetMapping("home/tv/popular")
    public ArrayList<TVProgram> getBestPopularTVPrograms(){

        return homeService.getBestPopularTVPrograms();
    }

}
