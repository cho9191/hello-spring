package hello.hellospring.controller;

import hello.hellospring.service.SearchServiceImpl;
import hello.hellospring.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class SearchController {

    @Autowired
    private SearchServiceImpl searchService;

    @GetMapping("keyword")
    @ResponseBody
    public  HashMap<String, ArrayList<SearchVo>> searchKeyword(@RequestParam(value = "keyword", required = false) String keyword, Model model) {

        HashMap<String, ArrayList<SearchVo>> returnMap = new HashMap<String, ArrayList<SearchVo>>();
        returnMap.put("places", searchService.keywordSearch(keyword));

        return returnMap;
    }

    @GetMapping("/searchKeywordList")
    @ResponseBody
    public HashMap<String, ArrayList<SearchVo>>  searchKeywordList(Model model){
        HashMap<String, ArrayList<SearchVo>> returnMap = new HashMap<String, ArrayList<SearchVo>>();
        returnMap.put("placesCount", searchService.keywordSearchList());
        return returnMap;
    }
}
