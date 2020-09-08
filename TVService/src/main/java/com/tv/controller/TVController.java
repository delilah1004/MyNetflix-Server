package com.tv.controller;

import com.tv.model.TVProgram;
import com.tv.service.GenreService;
import com.tv.service.TVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TVController {

    @Autowired
    TVService tvService;
    @Autowired
    GenreService genreService;

    // 넷플릭스에서 방영되는 모든 TV Program 목록 반환
    @GetMapping("/tv/all")
    public ArrayList<TVProgram> getAllTVPrograms() {

        return tvService.getAllTVPrograms();
    }

    // 장르명에 매칭되는 장르 id 값 반환
    @GetMapping("/tv/genre")
    public int getGenreId(@RequestParam String genreName) {

        return genreService.getGenreId(genreName);
    }

    // 모든 TV Program 을 분류하는 장르명 목록 반환
    @GetMapping("/tv/genres")
    public ArrayList<String> getGenreNames() {

        return genreService.getGenreNames();
    }
    
    // 장르 id 목록에 매칭되는 TV Program 목록 반환
    @GetMapping("/tv/search/genres")
    public ArrayList<TVProgram> getTVProgramsByGenreId(@RequestBody ArrayList<Integer> genreIds) {

        return tvService.getTVProgramsByGenreId(genreIds);
    }

    // 장르 id 에 매칭되는 TV Program 목록 반환
    @GetMapping("/tv/search/genre")
    public ArrayList<TVProgram> getTVProgramByGenreId(@RequestParam int genreId) {

        return tvService.getTVProgramByGenreId(genreId);
    }

}
