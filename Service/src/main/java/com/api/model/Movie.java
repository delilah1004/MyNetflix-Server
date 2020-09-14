package com.api.model;

import java.util.ArrayList;

public class Movie {

    // movie_id
    private long id;
    // 제목
    private String title;
    // 영상 길이
    private int runtime;
    // 장르
    private ArrayList<Integer> genres;
    // 영화 개요
    private String overview;

    // poster URI
    private String posterPath;

    // 영상 스트리밍 URL
    private String homepage;

    // 검색용 데이터

    // 개봉일
    private String releaseDate;

    // 인기도
    private double popularity;

    // 종영 여부 - Released, ...
    private String status;


    public Movie() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public ArrayList<Integer> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Integer> genres) {
        this.genres = genres;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
