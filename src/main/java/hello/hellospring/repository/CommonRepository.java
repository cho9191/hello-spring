package hello.hellospring.repository;

import hello.hellospring.vo.SearchVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface CommonRepository {

    SearchVo save(SearchVo searchVo);

    public ArrayList<SearchVo> keywordSearchList();
}
