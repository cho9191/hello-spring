package hello.hellospring.repository;

import hello.hellospring.vo.SearchVo;
import org.springframework.stereotype.Repository;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;


@Repository
public class KSearchRepositoryImpl implements SearchRepository{

    @Override
    public String apiCall(String keyword) throws IOException {
        System.out.println("KSearchRepositoryImpl.apiCall.Start!! ");
        keyword = URLEncoder.encode(keyword,"UTF-8");

        System.out.println("keyword 검색 : "+keyword);

        String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query="+ keyword;

        String jsonString = new String();

        String buf;

        URL Url = new URL(url);

        HttpsURLConnection conn = (HttpsURLConnection) Url.openConnection();
        System.out.println("conn : "+conn);
        String auth ="KakaoAK " +"0fe1a937c8a494f8161d0a4e083ae43b";
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Requested-With", "curl");
        conn.setRequestProperty("Authorization", auth);
        conn.setDoOutput(true);
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : "+responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        System.out.println("여기 테스트!");
        //System.out.println(br.readLine());
        while((buf = br.readLine()) != null) {
            jsonString += buf;
        }
        System.out.println("jsonString : "+jsonString);

        /*JSONParser jsonParse = new JSONParser();
        JSONObject obj =  (JSONObject)jsonParse.parse(jsonString);
        System.out.println("JsonObject 결과 값 :: " + obj);
*/

        return "";
    }

    @Override
    public SearchVo save(SearchVo searchVo) {
        return null;
    }
}
