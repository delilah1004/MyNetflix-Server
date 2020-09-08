package com.tv.controller;

import com.tv.model.TVProgram;
import com.tv.service.GenreService;
import com.tv.service.TVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TVController {

    @Autowired
    TVService tvService;
    @Autowired
    GenreService genreService;

    @GetMapping("/tv/all")
    public ArrayList<TVProgram> getAllTVId() {

        return tvService.getAllTV();
    }

    @GetMapping("/tv/genre")
    public int getGenreId(@RequestParam String genreName) {

        return genreService.getGenreId(genreName);
    }

    @GetMapping("/tv/genres")
    public ArrayList<String> getGenreNames() {

        return genreService.getGenreNames();
    }
}
