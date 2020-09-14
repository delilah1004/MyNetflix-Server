package com.api.model;

import java.util.ArrayList;

public class TVProgram {

    // tv_id
    private long id;
    // 제목
    private String name;
    // 영상 길이
    private int episodeRunTime;
    // 장르
    private ArrayList<Integer> genres;
    // 프로그램 개요
    private String overview;

    // poster URI
    private String posterPath;

    // 영상 스트리밍 URL
    private String homepage;

    // 검색용 데이터

    // 첫 방영일
    private String firstAirDate;
    // 마지막 방영일
    private String lastAirDate;

    // 인기도
    private double popularity;

    // season_number 목록
    private ArrayList<Integer> seasons;
    // 종영 여부 - Ended, Returning Series
    private String status;

    public TVProgram() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(int episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
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

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public ArrayList<Integer> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Integer> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TVProgram{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", episodeRunTime='" + episodeRunTime + '\'' +
                ", genres=" + genres +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", homepage='" + homepage + '\'' +
                ", firstAirDate='" + firstAirDate + '\'' +
                ", lastAirDate='" + lastAirDate + '\'' +
                ", popularity=" + popularity +
                ", seasons=" + seasons +
                ", status='" + status + '\'' +
                '}';
    }
}
