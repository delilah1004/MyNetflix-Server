package com.tv.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tv.data.StaticData;
import com.tv.model.TVProgram;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Service
public class TVService {

    private String url = StaticData.API_MAIN_URL;
    private BufferedReader br;
    private JsonObject json;
    private ArrayList<Long> tvIdList;

    // id 로 프로그램의 모든 정보 JsonObject 로 반환
    public JsonObject getTVById(long id) {

        url += "/tv/" + id;
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader();
        getJson();

        return json;
    }

    // 넷플릭스에서 방영중인 모든 TV 프로그램의 정보 리스트 반환
    public ArrayList<TVProgram> getAllTV() {

        getTVId();

        TVProgram tvProgram;
        ArrayList<TVProgram> tvPrograms = new ArrayList<>();

        ArrayList<Integer> seasons, genres;

        for (int i = 0; i < 10; i++) {
            JsonObject tv = getTVById(tvIdList.get(i));

            tvProgram = new TVProgram();

            // tv_id
            tvProgram.setId(tv.get("id").getAsLong());

            // 제목
            tvProgram.setName(tv.get("name").getAsString());
            // 영상 길이
            try {
                tvProgram.setEpisodeRunTime(tv.get("episode_run_time").getAsInt());
            } catch (Exception e) {
                tvProgram.setEpisodeRunTime(0);
            }

            // 장르
            genres = new ArrayList<>();

            for (JsonElement element : tv.get("genres").getAsJsonArray()) {
                genres.add(element.getAsJsonObject().get("id").getAsInt());
            }

            tvProgram.setGenres(genres);

            // 개요
            tvProgram.setOverview(tv.get("overview").getAsString());
            // 포스터 URI
            tvProgram.setPosterPath(tv.get("poster_path").getAsString());
            // 영상 스트리밍 URL
            tvProgram.setHomepage(tv.get("homepage").getAsString());

            // 방영일 정보
            tvProgram.setFirstAirDate(tv.get("first_air_date").getAsString());
            tvProgram.setLastAirDate(tv.get("last_air_date").getAsString());

            // 인기도
            tvProgram.setPopularity(tv.get("popularity").getAsDouble());

            // 시즌 정보
            seasons = new ArrayList<>();

            for (JsonElement element : tv.get("seasons").getAsJsonArray()) {
                seasons.add(element.getAsJsonObject().get("season_number").getAsInt());
            }

            tvProgram.setSeasons(seasons);

            // 종영 여부
            tvProgram.setStatus(tv.get("status").getAsString());

            tvPrograms.add(tvProgram);
        }

        return tvPrograms;
    }

    // 파일을 불러와 tvIdList 정보 업데이트
    public void getTVId() {

        FileReader fr = null;
        BufferedReader br = null;
        StringTokenizer st;

        String line;
        tvIdList = new ArrayList<>();

        try {
            fr = new FileReader(new File(StaticData.TV_ID_LIST_FILE_PATH));
            br = new BufferedReader(fr);

            // file 한줄씩 읽기
            while ((line = br.readLine()) != null) {

                // StringTokenizer 에 한 줄 담기
                st = new StringTokenizer(line);

                // StringTokenizer 에 담긴 토큰을 list 에 추가
                while (st.hasMoreTokens()) {
                    tvIdList.add(Long.parseLong(st.nextToken()));
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


    // 공통 함수
    // API URL 반환값 읽어오기
    public void getReader() {

        try {
            URL getTvURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) getTvURL.openConnection();
            conn.setRequestMethod(StaticData.protocol);
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 읽어온 정보 json 값으로 받기
    public void getJson() {

        json = JsonParser.parseReader(br).getAsJsonObject();
    }
}
