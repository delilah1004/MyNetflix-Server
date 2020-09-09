package com.tv.service;

import com.tv.data.Reader;
import com.tv.data.StaticData;
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

    // 파일을 불러와 allMovieIdList 정보 업데이트
    // 넷플릭스에서 방영되는 모든 영화들의 movie_id 목록 반환
    public void getAllTVIds() {

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
