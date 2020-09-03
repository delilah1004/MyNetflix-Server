package com.make.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.make.service.SearchService;

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/search_year")
    public void moviesInYear(@RequestParam String year) throws Exception{

        searchService.getMovies(year);
    }

    @GetMapping("/search")
    public void getMovie(@RequestParam int num) throws Exception{

        searchService.getMovie(num);
    }

}
