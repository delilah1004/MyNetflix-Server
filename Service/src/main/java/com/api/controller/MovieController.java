package com.api.controller;

import com.api.model.Movie;
import com.api.service.GenreService;
import com.api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MovieController {

    @Autowired
    MovieService movieService;
    @Autowired
    GenreService genreService;

    // 넷플릭스에서 방영되는 모든 영화 목록 반환 - 페이징
    @GetMapping("/movie/all")
    public ArrayList<Movie> getAllMovies(@RequestParam int pageNumber) {

        return movieService.getAllMovies(pageNumber);
    }

    // 모든 영화를 분류하는 장르명 목록 반환
    @GetMapping("/movie/genres")
    public ArrayList<String> getMovieGenreNames() {

        return genreService.getMovieGenreNames();
    }

    // 장르명에 매칭되는 영화 장르 id 값 반환
    @GetMapping("/movie/getGenreId")
    public int getMovieGenreId(@RequestParam String genreName) {

        return genreService.getMovieGenreId(genreName);
    }

    // 장르 id 에 매칭되는 영화 장르명 반환
    @GetMapping("/movie/getGenreName")
    public String getMovieGenreName(@RequestParam int genreId) {

        return genreService.getMovieGenreName(genreId);
    }

    // 장르 id 목록에 매칭되는 영화 목록 반환
    @GetMapping("/movie/search/genres")
    public ArrayList<Movie> getMoviesByGenreIds(@RequestParam long lastId, @RequestBody ArrayList<Integer> genreIds) {

        return movieService.getMoviesByGenreIds(lastId, genreIds);
    }

    // 연도별 영화 목록 반환
    @GetMapping("movie/search/year")
    public ArrayList<Movie> getMoviesByYear(@RequestParam long lastId, String year){

        return movieService.getMoviesByYear(lastId, year);
    }
}
