package cn.hd.test.file;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

public class FileTest {
    public static void main(String[] args) {
        String url = "https://static-uat.daoyitong.com/group1/M00/04/32/wKjcYGA-BVCAJoHvAATko8aBPsE072.png";
//        File file = new File(url);
//        System.out.println(file.length());
                      Date n = new Date();
                      
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(6000);
        requestFactory.setReadTimeout(6000);
        ResponseEntity<byte[]> response = new RestTemplate(requestFactory).exchange(
                url,
                HttpMethod.GET,
                null,
                byte[].class);
        byte[] bytes = response.getBody();
//        BASE64Encoder encoder = new BASE64Encoder();
//        System.out.println(encoder.encodeBuffer(bytes));
        System.out.println(new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8));
    }
}
