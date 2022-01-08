package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import hello.hellospring.vo.SearchVo;

import java.io.IOException;

public interface SearchRepository {

    public String apiCall(String keyword) throws IOException;

    SearchVo save(SearchVo searchVo);
}
