package com.tkog.backend.service.record;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

public interface GetRecordListService {
    JSONObject getAllRecord(Map<String, String> data);

    JSONObject getRecordInfo(Map<String, String> data);

    JSONObject getMyRecords(Map<String, String> data);
}
