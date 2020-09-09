package com.tv.controller;

import com.tv.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    // 넷플릭스에서 방영되는 모든 영화 목록 반환 - 페이징

    // 모든 영화를 분류하는 장르명 목록 반환

    // 장르명에 매칭되는 장르 id 값 반환

    // 장르 id 에 매칭되는 장르명 반환

    // 장르 id 목록에 매칭되는 영화 목록 반환

    // 연도별 영화 목록 반환

    // 인기순 영화 목록 반환

    // 최신순 영화 목록 반환

}
