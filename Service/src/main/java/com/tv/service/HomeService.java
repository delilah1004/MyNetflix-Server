package com.tv.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tv.model.TVProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
public class HomeService{

    @Autowired
    AllService allService;

    // 콘텐츠 개수
    private static final int count = 12;

    // 최신 영화 6개

    // 인기 영화 6개

    // 최신 TV 프로그램 6개

    // 인기 TV 프로그램 6개
    public ArrayList<TVProgram> getBestPopularTVPrograms() {

        return allService.getTVProgramList(getBestPopularTVIds());
    }

    // 인기 TV 프로그램 6개 Id 반환
    public ArrayList<Long> getBestPopularTVIds() {

        ArrayList<Long> allTvIdList = allService.getAllTVIds();
        ArrayList<Long> popularTvIdList = new ArrayList<>();

        for(int i=1; i<6; i++) {

            JsonArray results = allService.getPopularTVProgramIdList(i);

            for (JsonElement element : results) {
                long id = element.getAsJsonObject().get("id").getAsLong();

                if (allTvIdList.contains(id)) popularTvIdList.add(element.getAsJsonObject().get("id").getAsLong());

                if (popularTvIdList.size() == count) break;
            }

            if (popularTvIdList.size() == count) break;
        }

        return popularTvIdList;
    }

}
