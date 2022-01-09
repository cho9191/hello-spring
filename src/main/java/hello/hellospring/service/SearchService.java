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

    public ArrayList<SearchVo> makeReturnList(String keyword, ArrayList<SearchVo> list1, ArrayList<SearchVo> list2);

    public void testPrint();
}
