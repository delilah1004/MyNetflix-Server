package com.tv.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tv.data.Reader;
import com.tv.data.StaticData;
import com.tv.model.Movie;
import com.tv.model.TVProgram;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Service
public class MovieService extends Reader {

    private ArrayList<Long> allMovieIdList, movieIdList;
    // 한 페이지에 띄울 영화의 개수
    private final int count = 15;

    // 넷플릭스에서 방영되는 모든 TV Program 목록 반환
    public ArrayList<Movie> getAllMovies(int pageNumber) {

        // allTvIdList 초기화
        getAllMovieIds();

        // 매칭된 tvId 만 담을 list 초기화
        movieIdList = new ArrayList<>();

        // 검색을 시작할 인덱스
        int startIndex = (pageNumber - 1) * count;

        //for (long movieId : movieIdList) {
        for (int i = startIndex; i < startIndex + count; i++) {
            movieIdList.add(allMovieIdList.get(i));
        }

        return getMovieList(movieIdList);
    }

    // movieIdList 에 포함된 영화들의 정보 리스트 반환
    public ArrayList<Movie> getMovieList(ArrayList<Long> movieIdList) {

        // 반환값을 담을 Movie 리스트 선언
        ArrayList<Movie> movies = new ArrayList<>();
        // genre 정보를 담을 리스트 선언
        ArrayList<Integer> genres;

        for (long movieId : movieIdList) {

            try {
                JsonObject mv = getMovieById(movieId);

                Movie movie = new Movie();

                mv.get("id").getAsLong();

                movies.add(movie);

            } catch (Exception e) {
                System.out.println(movieId);
                e.printStackTrace();
            }
        }

        return movies;
    }

    // id 로 프로그램의 모든 정보 JsonObject 로 반환
    public JsonObject getMovieById(long id) {

        String url = StaticData.API_MAIN_URL;
        url += "/movie/" + id;
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader(url);

        return getJson();
    }

    // 파일을 불러와 allMovieIdList 정보 업데이트
    // 넷플릭스에서 방영되는 모든 영화들의 movie_id 목록 반환
    public void getAllMovieIds() {

        FileReader fr = null;
        BufferedReader br = null;
        StringTokenizer st;

        String line;
        allMovieIdList = new ArrayList<>();

        try {
            fr = new FileReader(new File(StaticData.MOVIE_ID_LIST_FILE_PATH));
            br = new BufferedReader(fr);

            // file 한줄씩 읽기
            while ((line = br.readLine()) != null) {

                // StringTokenizer 에 한 줄 담기
                st = new StringTokenizer(line);

                // StringTokenizer 에 담긴 토큰을 list 에 추가
                while (st.hasMoreTokens()) {
                    allMovieIdList.add(Long.parseLong(st.nextToken()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                if (fr != null) fr.close();
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
