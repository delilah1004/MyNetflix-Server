package com.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.api.data.Reader;
import com.api.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MovieService extends Reader{

    @Autowired
    AllService allService;

    // 한 페이지에 띄울 영화의 개수
    private final int count = 15;

    // 넷플릭스에서 방영되는 모든 영화 목록 반환
    public ArrayList<Movie> getAllMovies(int pageNumber) {

        // allMovieIdList 초기화
        ArrayList<Long> allMovieIdList = allService.getAllMovieIds();

        // 매칭된 movieId 만 담을 list 초기화
        ArrayList<Long> movieIdList = new ArrayList<>();

        // 검색을 시작할 인덱스
        int startIndex = (pageNumber - 1) * count;

        //for (long movieId : movieIdList) {
        for (int i = startIndex; i < startIndex + count; i++) {
            movieIdList.add(allMovieIdList.get(i));
        }

        return allService.getMovieList(movieIdList);
    }

    // 장르 id 목록에 매칭되는 영화 목록 반환
    public ArrayList<Movie> getMoviesByGenreIds(long lastId, ArrayList<Integer> genreIds) {

        ArrayList<Long> allMovieIdList = allService.getAllMovieIds();

        // 매칭된 movieId 만 담을 list 초기화
        ArrayList<Long> movieIdList = new ArrayList<>();

        // 검색을 시작할 인덱스
        int startIndex;

        if (lastId == 0) {
            // 첫 번째 페이지면 0부터 시작
            startIndex = 0;
        } else {
            // 이전 페이지의 마지막 프로그램 다음 index 부터 검색
            startIndex = allMovieIdList.indexOf(lastId) + 1;
        }

        // 선택된 장르에 매칭되는 영화의 id 리스트 생성 (최대 15개)
        for (int i = startIndex; i < allMovieIdList.size(); i++) {

            JsonObject movie = allService.getMovieJsonById(allMovieIdList.get(i));

            // 해당 영화의 장르 중 검색할 장르가 포함되어 있는지 검사
            for (JsonElement element : movie.get("genres").getAsJsonArray()) {
                if (genreIds.contains(element.getAsJsonObject().get("id").getAsInt())) {
                    movieIdList.add(allMovieIdList.get(i));
                    break;
                }
            }

            // 한 페이지에 띄울 영화의 개수를 충족하면 종료
            if (movieIdList.size() == count) break;
        }

        return allService.getMovieList(movieIdList);
    }

    // 연도별 영화 목록 반환
    public ArrayList<Movie> getMoviesByYear(long lastId, String year) {

        ArrayList<Long> allMovieIdList = allService.getAllMovieIds();

        // 매칭된 movieId 만 담을 list 초기화
        ArrayList<Long> movieIdList = new ArrayList<>();

        // 검색을 시작할 인덱스
        int startIndex;

        if (lastId == 0) {
            // 첫 번째 페이지면 0부터 시작
            startIndex = 0;
        } else {
            // 이전 페이지의 마지막 프로그램 다음 index 부터 검색
            startIndex = allMovieIdList.indexOf(lastId) + 1;
        }

        // 선택된 장르에 매칭되는 영화의 id 리스트 생성 (최대 15개)
        for (int i = startIndex; i < allMovieIdList.size(); i++) {

            JsonObject movie = allService.getMovieJsonById(allMovieIdList.get(i));

            try {
                // 해당 영화의 방영일이 year 값과 동일한지 검사
                if (year.equals(movie.get("release_date").getAsString().split("-")[0])) {
                    movieIdList.add(allMovieIdList.get(i));

                    System.out.println(movie.get("release_date").getAsString().split("-")[0]);
                }
            } catch (Exception e) {
                continue;
            }

            // 한 페이지에 띄울 영화의 개수를 충족하면 종료
            if (movieIdList.size() == count) break;
        }

        return allService.getMovieList(movieIdList);
    }

}
