package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.vo.SearchVo;

import java.util.ArrayList;
import java.util.List;

public interface SearchService {

    public ArrayList<SearchVo> keywordSearch(String keyword);

    public void keywordSave(SearchVo searchVo);

    public void keywordList();

    public ArrayList<SearchVo> keywordSearchList();

    public void testPrint();
}
