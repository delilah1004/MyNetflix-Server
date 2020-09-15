package com.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.api.data.Reader;
import com.api.model.TVProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TVService extends Reader {

    @Autowired
    AllService allService;

    // 한 페이지에 띄울 TV Program 의 개수
    private final int count = 15;

    // 넷플릭스에서 방영되는 모든 TV Program 목록 반환
    public ArrayList<TVProgram> getAllTVPrograms(int pageNumber) {

        ArrayList<Long> allTvIdList = allService.getAllTVIds();

        // 매칭된 tvId 만 담을 list 초기화
        ArrayList<Long> tvIdList = new ArrayList<>();

        // 검색을 시작할 인덱스
        int startIndex = (pageNumber - 1) * count;

        //for (long tvId : tvIdList) {
        for (int i = startIndex; i < startIndex + count; i++) {
            tvIdList.add(allTvIdList.get(i));
        }

        return allService.getTVProgramList(tvIdList);
    }

    // 장르 id 목록에 매칭되는 TV Program 목록 반환
    public ArrayList<TVProgram> getTVProgramsByGenreIds(long lastId, ArrayList<Integer> genreIds) {

        ArrayList<Long> allTvIdList = allService.getAllTVIds();

        // 매칭된 tvId 만 담을 list 초기화
        ArrayList<Long> tvIdList = new ArrayList<>();

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

            JsonObject tv = allService.getTVJsonById(allTvIdList.get(i));

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

        return allService.getTVProgramList(tvIdList);
    }

    // 연도별 TV Program 목록 반환
    public ArrayList<TVProgram> getTVProgramsByYear(long lastId, String year) {

        ArrayList<Long> allTvIdList = allService.getAllTVIds();

        // 매칭된 tvId 만 담을 list 초기화
        ArrayList<Long> tvIdList = new ArrayList<>();

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

            JsonObject tv = allService.getTVJsonById(allTvIdList.get(i));

            try {
                // 해당 TV 프로그램의 첫 방영일의 연도가 year 값과 동일한지 검사
                if (year.equals(tv.get("first_air_date").getAsString().split("-")[0])) {
                    tvIdList.add(allTvIdList.get(i));
                }
            } catch (Exception e) {
                continue;
            }

            // 한 페이지에 띄울 TV Program 의 개수를 충족하면 종료
            if (tvIdList.size() == count) break;
        }

        return allService.getTVProgramList(tvIdList);
    }

    // 최신순 TV Program 목록 반환

    // 인기순 TV Program 목록 반환

}

