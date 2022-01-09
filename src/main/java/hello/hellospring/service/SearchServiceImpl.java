package hello.hellospring.service;

import hello.hellospring.repository.CommonRepositoryImpl;
import hello.hellospring.repository.KSearchRepositoryImpl;
import hello.hellospring.repository.NSearchRepositoryImpl;
import hello.hellospring.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    private final double SIMILAR_POINT = 0.4;

    private final int RESPONSE_COUNT = 10;

    @Autowired
    private KSearchRepositoryImpl kSearchRepository;

    @Autowired
    private NSearchRepositoryImpl nSearchRepository;

    @Autowired
    private CommonRepositoryImpl commonRepository;

    @Override
    public ArrayList<SearchVo> keywordSearch(String keyword) {

        SearchVo searchVo = new SearchVo();
        searchVo.setSearchKeyword(keyword);
        this.keywordSave(searchVo);

        ArrayList<SearchVo> returnList = new ArrayList<SearchVo>();

        try {
            System.out.println("SearchServiceImpl.keywordSearch.Start!! ");
            ArrayList<SearchVo> kList = kSearchRepository.apiCall(keyword);
            ArrayList<SearchVo> nList = nSearchRepository.apiCall(keyword);

            System.out.println("kList :  "+kList);
            System.out.println("nList :  "+nList);

            returnList = makeReturnList(keyword, kList, nList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnList;
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

        Collections.sort(result, new Comparator<SearchVo>() {
            @Override
            public int compare(SearchVo o1, SearchVo o2) {
                return o2.getSearchCount()-o1.getSearchCount();
            }
        });

        if(result.size() <= RESPONSE_COUNT){
            return result;

        }else{

            int size = result.size();
            for(int i=RESPONSE_COUNT; i<size; i++){
                result.remove(i);
            }
        }
        return result;
    }

    @Override
    public ArrayList<SearchVo> makeReturnList(String keyword, ArrayList<SearchVo> list1, ArrayList<SearchVo> list2) {
        System.out.println("makeReturnList Start~!");

        System.out.println("list1.size() : "+list1.size() + "     list2.size() : "+list2.size());

        ArrayList<SearchVo> returnVoList = new ArrayList<SearchVo>();

        for(int i=0; i< list1.size(); i++){
            String kTitle = list1.get(i).getTitle().replaceAll(keyword, "");
            for(int j=0; j< list2.size(); j++){
                String nTitle = list2.get(j).getTitle().replaceAll(keyword, "");

                System.out.println("kTitle : "+kTitle+"  nTitle : "+nTitle+"  유사도 :"+similarity(kTitle, nTitle));

                if(similarity(kTitle, nTitle) >= SIMILAR_POINT){
                    list1.get(i).setFromData("KN");
                    returnVoList.add(list1.get(i));
                    list1.remove(i);
                    list2.remove(j);
                }
            }
        }

        if(returnVoList.size() >= RESPONSE_COUNT
                || list1.size() + list2.size() <= RESPONSE_COUNT-returnVoList.size()){
            for(int i=0; i<list1.size(); i++){
                returnVoList.add(list1.get(i));
            }
            for(int j=0; j<list2.size(); j++){
                returnVoList.add(list2.get(j));
            }
            return returnVoList;
        }

        System.out.println("returnVoList.size() : "+returnVoList.size());
        int remainKCount =  (RESPONSE_COUNT-returnVoList.size()) < list1.size() ? (RESPONSE_COUNT-returnVoList.size()) : list1.size();

        for(int i=0; i<remainKCount; i++){
            returnVoList.add(list1.get(i));
        }

        if(returnVoList.size()==RESPONSE_COUNT){
            for(int j=0; j<list2.size(); j++){
                System.out.println("값교체 list2.size : "+list2.size());
                returnVoList.set(RESPONSE_COUNT-(j+1), list2.get(j));
            }
        }else{
            for(int j=0; j<list2.size(); j++){
                returnVoList.add(list2.get(j));
                if(returnVoList.size() >= RESPONSE_COUNT) break;
            }
        }


        System.out.println("*********returnVoList.size() : "+returnVoList.size());
        return returnVoList;
    }


    private double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;

        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }

        int longerLength = longer.length();
        if (longerLength == 0) return 1.0;
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }
    private int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costs = new int[s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];

                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        }

                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }

            if (i > 0) costs[s2.length()] = lastValue;
        }

        return costs[s2.length()];
    }

    @Override
    public void testPrint() {
        System.out.println("testPrint~!!!");
    }
}
