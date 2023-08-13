package com.tkog.backend.service.record;

import com.alibaba.fastjson2.JSONObject;

public interface GetRecordListService {
    JSONObject getAllRecord(Integer page);

    JSONObject getRecordInfo(Integer recordId);
}
