package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import hello.hellospring.vo.SearchVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommonRepositoryImpl implements CommonRepository{

    private  static Map<String, SearchVo> store = new HashMap<String, SearchVo>();
    private static long sequence = 0L;

    @Override
    public SearchVo save(SearchVo searchVo) {
        searchVo.setSearchKeyword(searchVo.getSearchKeyword());

        String key = searchVo.getSearchKeyword();

        SearchVo insertVo = new SearchVo();
        insertVo.setSearchKeyword(key);

        if(store.containsKey(key)){
            insertVo = store.get(key);
            insertVo.setSearchCount(insertVo.getSearchCount()+1);
        }else{
            insertVo.setSearchCount(1);
        }
        store.put(key, insertVo);

        return searchVo;
    }

    @Override
    public ArrayList<SearchVo> keywordSearchList() {

        return new ArrayList<SearchVo>(store.values());
    }
}
