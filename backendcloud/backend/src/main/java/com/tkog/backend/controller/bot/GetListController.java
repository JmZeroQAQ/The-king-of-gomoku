package com.tkog.backend.controller.bot;

import com.tkog.backend.pojo.Bot;
import com.tkog.backend.service.bot.GetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetListController {
    @Autowired
    private GetListService getListService;

    @GetMapping("/bot/getList/")
    public List<Bot> getList() {
        return getListService.getList();
    }
}
