package com.tv.model;

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

}
