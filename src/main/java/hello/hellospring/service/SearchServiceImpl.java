package hello.hellospring.service;

import hello.hellospring.repository.CommonRepositoryImpl;
import hello.hellospring.repository.KSearchRepositoryImpl;
import hello.hellospring.repository.NSearchRepositoryImpl;
import hello.hellospring.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private KSearchRepositoryImpl kSearchRepository;

    @Autowired
    private NSearchRepositoryImpl nSearchRepository;

    @Autowired
    private CommonRepositoryImpl commonRepository;

    @Override
    public String keywordSearch(String keyword) {

        SearchVo searchVo = new SearchVo();
        searchVo.setSearchKeyword(keyword);
        this.keywordSave(searchVo);

        try {
            System.out.println("SearchServiceImpl.keywordSearch.Start!! ");
            //kSearchRepository.apiCall(keyword);
            nSearchRepository.apiCall(keyword);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void keywordSave(SearchVo searchVo) {
        commonRepository.save(searchVo);
    }

    @Override
    public void keywordList() {

    }

    @Override
    public ArrayList<SearchVo> keywordSearchList() {
        ArrayList<SearchVo> result = commonRepository.keywordSearchList();
        System.out.println("result : ");

        for(SearchVo vo : result){

            System.out.println(vo.getSearchKeyword()+" "+vo.getSearchCount());

        }


        return result;
    }

    @Override
    public void testPrint() {
        System.out.println("testPrint~!!!");
    }
}
