package com.tkog.backend.service.ranklist;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

public interface RankListService {
    JSONObject getList(Map<String, String> data);
}
