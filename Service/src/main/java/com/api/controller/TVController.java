package com.api.controller;

import com.api.model.TVProgram;
import com.api.service.GenreService;
import com.api.service.TVService;
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

    // 넷플릭스에서 방영되는 모든 TV Program 목록 반환 - 페이징
    @GetMapping("/tv/all")
    public ArrayList<TVProgram> getAllTVPrograms(@RequestParam int pageNumber) {

        return tvService.getAllTVPrograms(pageNumber);
    }

    // 모든 TV Program 을 분류하는 장르명 목록 반환
    @GetMapping("/tv/genres")
    public ArrayList<String> getTVGenreNames() {

        return genreService.getTVGenreNames();
    }

    // 장르명에 매칭되는 TV 프로그램 장르 id 값 반환
    @GetMapping("/tv/getGenreId")
    public int getTVGenreId(@RequestParam String genreName) {

        return genreService.getTVGenreId(genreName);
    }

    // 장르 id 에 매칭되는 TV 프로그램 장르명 반환
    @GetMapping("/tv/getGenreName")
    public String getTVGenreName(@RequestParam int genreId) {

        return genreService.getTVGenreName(genreId);
    }

    /* ------ 장르별 검색 -------- */
    
    // 장르 id 목록에 매칭되는 TV Program 목록 반환
    @GetMapping("/tv/search/genres")
    public ArrayList<TVProgram> getTVProgramsByGenreIds(@RequestParam long lastId, @RequestBody ArrayList<Integer> genreIds) {

        return tvService.getTVProgramsByGenreIds(lastId, genreIds);
    }

    /* ------ 연도별 검색 -------- */

    // 연도별 TV Program 목록 반환
    @GetMapping("tv/search/year")
    public ArrayList<TVProgram> getTVProgramsByYear(@RequestParam long lastId, String year){

        return tvService.getTVProgramsByYear(lastId, year);
    }

    /* ------ 인기순 검색 -------- */

    // 넷플릭스에서 방영되는 모든 TV Program 목록 인기 내림차순 반환 - 페이징
    @GetMapping("tv/popular/desc")
    public ArrayList<TVProgram> getPopularDescTVPrograms(@RequestParam int pageNumber){

        return tvService.getPopularDescTVPrograms(pageNumber);
    }

    // 인기순 - 오름차순 TV Program 목록 반환
    @GetMapping("tv/popular/asc")
    public ArrayList<TVProgram> getPopularAscTVPrograms(@RequestParam int pageNumber) {

        return tvService.getPopularAscTVPrograms(pageNumber);
    }


    /* ------ 방영일순 검색 -------- */

    // 최신순 TV Program 목록 반환
    @GetMapping("tv/latest")
    public ArrayList<TVProgram> getLatestTVPrograms(@RequestParam int pageNumber) {

        return tvService.getLatestTVPrograms(pageNumber);
    }

    // 오래된순 TV Program 목록 반환
    @GetMapping("tv/oldest")
    public ArrayList<TVProgram> getOldestTVPrograms(@RequestParam int pageNumber) {

        return tvService.getOldestTVPrograms(pageNumber);
    }

}
