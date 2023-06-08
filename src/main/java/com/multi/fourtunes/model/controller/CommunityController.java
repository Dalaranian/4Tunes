package com.multi.fourtunes.model.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.service.CommunityService;

@Controller
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @RequestMapping("/list")
    public String getCommunityList(Model model) {
        List<CommunityDto> communityList = communityService.getAll();
        model.addAttribute("communityList", communityList);
        return "community_list";
    }

    @RequestMapping("/write")
    public String getCommunityDetail() {
        return "community_write";
    }
}
