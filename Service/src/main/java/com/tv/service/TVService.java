package com.tv.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tv.data.Reader;
import com.tv.data.StaticData;
import com.tv.model.TVProgram;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Service
public class TVService extends Reader {

    private ArrayList<Long> allTvIdList, tvIdList;
    // 한 페이지에 띄울 TV Program 의 개수
    private final int count = 15;

    // 넷플릭스에서 방영되는 모든 TV Program 목록 반환
    public ArrayList<TVProgram> getAllTVPrograms(int pageNumber) {

        // allTvIdList 초기화
        getAllTVIds();

        // 매칭된 tvId 만 담을 list 초기화
        tvIdList = new ArrayList<>();

        // 검색을 시작할 인덱스
        int startIndex = (pageNumber - 1) * count;

        //for (long tvId : tvIdList) {
        for (int i = startIndex; i < startIndex + count; i++) {
            tvIdList.add(allTvIdList.get(i));
        }

        return getTVProgramList(tvIdList);
    }

    // 장르 id 목록에 매칭되는 TV Program 목록 반환
    public ArrayList<TVProgram> getTVProgramsByGenreIds(long lastId, ArrayList<Integer> genreIds) {

        // allTvIdList 목록 초기화
        getAllTVIds();

        // 매칭된 tvId 만 담을 list 초기화
        tvIdList = new ArrayList<>();

        // 검색을 시작할 인덱스
        int startIndex;

        if (lastId == 0) {
            // 첫 번째 페이지면 0부터 시작
            startIndex = 0;
        } else {
            // 이전 페이지의 마지막 프로그램 다음 index 부터 검색
            startIndex = allTvIdList.indexOf(lastId) + 1;
        }

        // 선택된 장르에 매칭되는 TV Program 의 id 리스트 생성 (최대 15개)
        for (int i = startIndex; i < allTvIdList.size(); i++) {

            JsonObject tv = getTVById(allTvIdList.get(i));

            // 해당 TV 프로그램의 장르 중 검색할 장르가 포함되어 있는지 검사
            for (JsonElement element : tv.get("genres").getAsJsonArray()) {
                if (genreIds.contains(element.getAsJsonObject().get("id").getAsInt())) {
                    tvIdList.add(allTvIdList.get(i));
                    break;
                }
            }

            // 한 페이지에 띄울 TV Program 의 개수를 충족하면 종료
            if (tvIdList.size() == count) break;
        }

        return getTVProgramList(tvIdList);
    }

    // 연도별 TV Program 목록 반환
    public ArrayList<TVProgram> getTVProgramsByYear(long lastId, String year) {

        // allTvIdList 목록 초기화
        getAllTVIds();

        // 매칭된 tvId 만 담을 list 초기화
        tvIdList = new ArrayList<>();

        // 검색을 시작할 인덱스
        int startIndex;

        if (lastId == 0) {
            // 첫 번째 페이지면 0부터 시작
            startIndex = 0;
        } else {
            // 이전 페이지의 마지막 프로그램 다음 index 부터 검색
            startIndex = allTvIdList.indexOf(lastId) + 1;
        }

        // 선택된 장르에 매칭되는 TV Program 의 id 리스트 생성 (최대 15개)
        for (int i = startIndex; i < allTvIdList.size(); i++) {

            JsonObject tv = getTVById(allTvIdList.get(i));

            try {
                // 해당 TV 프로그램의 장르 중 검색할 장르가 포함되어 있는지 검사
                if (year.equals(tv.get("first_air_date").getAsString().split("-")[0])) {
                    tvIdList.add(allTvIdList.get(i));
                }
            } catch (Exception e) {
                continue;
            }

            // 한 페이지에 띄울 TV Program 의 개수를 충족하면 종료
            if (tvIdList.size() == count) break;
        }

        return getTVProgramList(tvIdList);
    }

    // 최신순 TV Program 목록 반환
    public ArrayList<TVProgram> getTVProgramsLatest(long lastId, ArrayList<Integer> genreIds) {

        // allTvIdList 목록 초기화
        getAllTVIds();

        // 매칭된 tvId 만 담을 list 초기화
        tvIdList = new ArrayList<>();

        // 검색을 시작할 인덱스
        int startIndex;

        if (lastId == 0) {
            // 첫 번째 페이지면 0부터 시작
            startIndex = 0;
        } else {
            // 이전 페이지의 마지막 프로그램 다음 index 부터 검색
            startIndex = allTvIdList.indexOf(lastId) + 1;
        }

        // 선택된 장르에 매칭되는 TV Program 의 id 리스트 생성 (최대 15개)
        for (int i = startIndex; i < allTvIdList.size(); i++) {

            JsonObject tv = getTVById(allTvIdList.get(i));

            // 해당 TV 프로그램의 장르 중 검색할 장르가 포함되어 있는지 검사
            for (JsonElement element : tv.get("genres").getAsJsonArray()) {
                if (genreIds.contains(element.getAsJsonObject().get("id").getAsInt())) {
                    tvIdList.add(allTvIdList.get(i));
                    break;
                }
            }

            // 한 페이지에 띄울 TV Program 의 개수를 충족하면 종료
            if (tvIdList.size() == count) break;
        }

        return getTVProgramList(tvIdList);
    }

    // 인기순 TV Program 목록 반환


    // tvIdList 에 포함된 TV Program 들의 정보 리스트 반환
    public ArrayList<TVProgram> getTVProgramList(ArrayList<Long> tvIdList) {

        // 반환값을 담을 TVProgram 리스트 선언
        ArrayList<TVProgram> tvPrograms = new ArrayList<>();
        // season 정보와 genre 정보를 담을 리스트 선언
        ArrayList<Integer> seasons, genres;

        for (long tvId : tvIdList) {

            try {
                JsonObject tv = getTVById(tvId);

                TVProgram tvProgram = new TVProgram();

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
                try {
                    tvProgram.setPosterPath(tv.get("poster_path").getAsString());
                } catch (Exception e) {
                    tvProgram.setPosterPath(null);
                }

                // 영상 스트리밍 URL
                tvProgram.setHomepage(tv.get("homepage").getAsString());

                // 방영일 정보
                try {
                    tvProgram.setFirstAirDate(tv.get("first_air_date").getAsString());
                } catch (Exception e) {
                    tvProgram.setFirstAirDate(null);
                }

                try {
                    tvProgram.setLastAirDate(tv.get("last_air_date").getAsString());
                } catch (Exception e) {
                    tvProgram.setLastAirDate(null);
                }

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
            } catch (Exception e) {
                System.out.println(tvId);
                e.printStackTrace();
            }
        }

        return tvPrograms;
    }

    // id 로 프로그램의 모든 정보 JsonObject 로 반환
    public JsonObject getTVById(long id) {

        String url = StaticData.API_MAIN_URL;
        url += "/tv/" + id;
        url += "?api_key=" + StaticData.API_KEY;
        url += "&language=" + StaticData.KOREAN;

        getReader(url);

        return getJson();
    }

    // 파일을 불러와 allTvIdList 정보 업데이트
    // 넷플릭스에서 방영되는 모든 TV Program 들의 tv_id 목록 반환
    public void getAllTVIds() {

        FileReader fr = null;
        BufferedReader br = null;
        StringTokenizer st;

        String line;
        allTvIdList = new ArrayList<>();

        try {
            fr = new FileReader(new File(StaticData.TV_ID_LIST_FILE_PATH));
            br = new BufferedReader(fr);

            // file 한줄씩 읽기
            while ((line = br.readLine()) != null) {

                // StringTokenizer 에 한 줄 담기
                st = new StringTokenizer(line);

                // StringTokenizer 에 담긴 토큰을 list 에 추가
                while (st.hasMoreTokens()) {
                    allTvIdList.add(Long.parseLong(st.nextToken()));
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

