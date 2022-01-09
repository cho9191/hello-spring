package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.SearchService;
import hello.hellospring.service.SearchServiceImpl;
import hello.hellospring.vo.SearchVo;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SearchServiceImpl searchService;

    @GetMapping("keyword")
    @ResponseBody
    public ArrayList<SearchVo> searchKeyword(@RequestParam(value = "keyword", required = false) String keyword, Model model) {

        //JSONArray json = new JSONArray(searchService.keywordSearch(keyword));
        return searchService.keywordSearch(keyword);
    }

    @GetMapping("/searchKeywordList")
    public List<SearchVo> searchKeywordList(Model model){
        List<SearchVo> returnVoList = searchService.keywordSearchList();
        return returnVoList;
    }

    @GetMapping("test")
    public void testPrint(){
        searchService.testPrint();
    }
}
