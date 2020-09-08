package com.tv.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tv.data.StaticData;
import com.tv.model.TVProgram;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Service
public class TVService extends Reader {

    private ArrayList<Long> tvIdList;

    // id 로 프로그램의 모든 정보 JsonObject 로 반환
    public JsonObject getTVById(long id) {

        String url = StaticData.API_MAIN_URL;
        url += "/tv/" + id;
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        JsonObject jsonObject = null;

        getReader(url);
        jsonObject = getJson();

        return jsonObject;
    }

    // 넷플릭스에서 방영되는 모든 TV Program 목록 반환
    public ArrayList<TVProgram> getAllTVPrograms() {

        // 넷플릭스에서 방영되는 모든 TV Program 들의 tv_id 목록 반환
        getAllTVIds();

        TVProgram tvProgram;
        ArrayList<TVProgram> tvPrograms = new ArrayList<>();

        ArrayList<Integer> seasons, genres;

        for (long tvId : tvIdList) {
            JsonObject tv = getTVById(tvId);

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
    // 넷플릭스에서 방영되는 모든 TV Program 들의 tv_id 목록 반환
    public void getAllTVIds() {

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

    // 장르 id 목록에 매칭되는 TV Program 목록 반환
    public ArrayList<TVProgram> getTVProgramsByGenreId(ArrayList<Integer> genreIds) {

        ArrayList<TVProgram> tvPrograms = new ArrayList<>();

        for (TVProgram tvProgram : getAllTVPrograms()) {

            for (int genre : tvProgram.getGenres()) {
                if (genreIds.contains(genre)) {
                    tvPrograms.add(tvProgram);
                    break;
                }
            }

        }

        return tvPrograms;
    }

    // 장르 id 목록에 매칭되는 TV Program 목록 반환
    public ArrayList<TVProgram> getTVProgramByGenreId(int genreId) {

        ArrayList<TVProgram> tvPrograms = new ArrayList<>();

        for (TVProgram tvProgram : getAllTVPrograms()) {

            if (tvProgram.getGenres().contains(genreId)) {
                tvPrograms.add(tvProgram);
            }
        }

        return tvPrograms;
    }


}

