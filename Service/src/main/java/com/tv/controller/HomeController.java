package com.tv.controller;

import com.google.gson.JsonObject;
import com.tv.model.TVProgram;
import com.tv.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class HomeController {
    
    @Autowired
    HomeService homeService;
    
    // 최신 영화 6개
    
    // 인기 영화 6개
    
    // 최신 TV 프로그램 6개
    
    // 인기 TV 프로그램 6개
    @GetMapping("home/tv/popular")
    public ArrayList<TVProgram> getBestPopularTVPrograms(){

        return homeService.getBestPopularTVPrograms();
    }

}
