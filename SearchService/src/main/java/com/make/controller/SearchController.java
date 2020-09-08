package com.make.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.make.service.SearchService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/search_year")
    public void moviesInYear(@RequestParam String year) throws Exception{

        //searchService.getMovies(year);
    }

    @GetMapping("/search")
    public void getMovie(@RequestParam int num) throws Exception{

        searchService.getMovie(num);
    }

    @GetMapping("/test")
    public String getPopularMovieListDesc(@RequestParam int pageNum) throws Exception {

        return searchService.getPopularMovieListDesc(pageNum);
    }

}
