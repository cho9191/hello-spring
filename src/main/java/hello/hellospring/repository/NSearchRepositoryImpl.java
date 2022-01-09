package hello.hellospring.repository;

import hello.hellospring.vo.SearchVo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
//import org.json.simple.JSONObject;

@Repository
public class NSearchRepositoryImpl implements SearchRepository{

    ArrayList<SearchVo> arrayList;

    @Override
    public ArrayList<SearchVo>  apiCall(String keyword) throws IOException {

        System.out.println("NSearchRepositoryImpl.apiCall.Start!! ");
        String encodeKeyword = URLEncoder.encode(keyword,"UTF-8");

        System.out.println("keyword 검색 : "+encodeKeyword);

        String url = "https://openapi.naver.com/v1/search/local.json?query="+ encodeKeyword+"&display=100";

        String jsonString = new String();

        String buf;

        URL Url = new URL(url);

        HttpsURLConnection conn = (HttpsURLConnection) Url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Requested-With", "curl");
        conn.setRequestProperty("X-Naver-Client-Id", "9YE5wtSwDCNQPfhAVgyd");
        conn.setRequestProperty("X-Naver-Client-Secret", "eqd0vk89jn");
        conn.setDoOutput(true);
        int responseCode = conn.getResponseCode();

        System.out.println("responseCode : "+responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        System.out.println("여기 테스트!");

        while((buf = br.readLine()) != null) {
            jsonString += buf;
        }
        System.out.println("jsonString : "+jsonString);


        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse( jsonString );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = (JSONObject) obj;

        JSONArray jArray = (JSONArray) jsonObj.get("items");

        System.out.println("jArray.size() : "+jArray.size());
        arrayList = new ArrayList<SearchVo>();
        for(int i=0; i<jArray.size(); i++){
            SearchVo insertVo = new SearchVo();

            JSONObject test = (JSONObject)jArray.get(i);
            String title = (String) test.get("title");
            String category = (String) test.get("category");
            //String description = (String) test.get("description");
            String address = (String) test.get("address");
            String roadAddress = (String) test.get("roadAddress");
            System.out.println("title : "+title+" category : "+category+" address : "+address+" roadAddress : "+roadAddress);

            insertVo.setSeq(2);
            insertVo.setFromData("N");
            insertVo.setSearchKeyword(keyword);
            insertVo.setTitle(title);
            insertVo.setCategory(category);
            insertVo.setAddress(address);
            insertVo.setRoadAddress(roadAddress);

            arrayList.add(insertVo);

        }



        return arrayList;
    }

    @Override
    public SearchVo save(SearchVo searchVo) {
        return null;
    }
}
