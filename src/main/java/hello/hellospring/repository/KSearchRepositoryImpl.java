package hello.hellospring.repository;

import hello.hellospring.vo.SearchVo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


@Repository
public class KSearchRepositoryImpl implements SearchRepository{

    ArrayList<SearchVo> arrayList;

    @Value("${k.url}")
    private String k_url;

    @Value("${k.api.key}")
    private String k_api_key;

    @Override
    public ArrayList<SearchVo>  apiCall(String keyword) throws IOException {
        System.out.println("KSearchRepositoryImpl.apiCall.Start!! ");
        String encodeKeyword = URLEncoder.encode(keyword,"UTF-8");
        System.out.println("keyword 검색 : "+encodeKeyword);

        //size (최소: 1, 최대: 45, 기본값: 15)
        String url = k_url+ encodeKeyword+"&size=15";

        String jsonString = new String();

        String buf;

        URL Url = new URL(url);

        HttpsURLConnection conn = (HttpsURLConnection) Url.openConnection();
        String auth ="KakaoAK " +k_api_key;
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Requested-With", "curl");
        conn.setRequestProperty("Authorization", auth);
        conn.setDoOutput(true);
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : "+responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        while((buf = br.readLine()) != null) {
            jsonString += buf;
        }
        System.out.println("kkop jsonString : "+jsonString);

        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse( jsonString );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = (JSONObject) obj;
        JSONArray jArray = (JSONArray) jsonObj.get("documents");

        System.out.println("카카오 스타트");
        arrayList = new ArrayList<SearchVo>();
        for(int i=0; i<jArray.size(); i++){

            SearchVo insertVo = new SearchVo();

            JSONObject test = (JSONObject)jArray.get(i);
            String title = (String) test.get("place_name");
            String category = (String) test.get("category_group_name");
            //String description = (String) test.get("description");
            String address = (String) test.get("address_name");
            String roadAddress = (String) test.get("road_address_name");
            System.out.println("title : "+title+" category : "+category+" address : "+address+" roadAddress : "+roadAddress);

            insertVo.setSeq(1);
            insertVo.setFromData("K");
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
