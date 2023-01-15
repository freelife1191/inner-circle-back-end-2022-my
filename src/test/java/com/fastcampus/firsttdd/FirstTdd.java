package com.fastcampus.firsttdd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://developers.naver.com/apps/#/myapps/QzUV33zanFblmZgdlj_4/overview
 * https://developers.naver.com/docs/search/local/
 * https://developers.naver.com/docs/serviceapi/search/local/local.md#%EC%A7%80%EC%97%AD
 */
@Component
class NaverApiConfig {
    public String clientId = "QzUV33zanFblmZgdlj_4";

    public String clientSecret = "ofsoMMVSoa";
}

@SpringBootTest
class FirstTdd {
    @Autowired
    NaverApiConfig config;

    @Test
        // stair step test
    void read_environment_variable_using_spring_annotations() {
        assertThat(config.clientId).isNotEmpty();
        assertThat(config.clientSecret).isNotEmpty();
    }

    @Test
    public void search_using_naver_api() throws JsonProcessingException, UnsupportedEncodingException {
        String clientId = config.clientId; // 애플리케이션 클라이언트 아이디
        String clientSecret = config.clientSecret; // 애플리케이션 클라이언트 시크릿

        String query = new String("갈비집".getBytes(), UTF_8);
        String url = "https://openapi.naver.com/v1/search/local.json?query=" +
                query +
                "&display=10&start=1&sort=random";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", clientId);
        headers.add("X-Naver-Client-Secret", clientSecret);
        headers.add("Content-Type", "application/json");

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class,
                1
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        NaverSearchResult naverSearchResult = new ObjectMapper().readValue(response.getBody(), NaverSearchResult.class);
        System.out.println(naverSearchResult);
    }
}

@Getter
@Setter
@ToString
class NaverSearchResult {
    private String lastBuildDate;
    private String total;
    private String start;
    private String display;
    private List<Item> items;
}

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
class Item {
    private String title;
    private String link;
    private String category;
    private String description;
    private String telephone;
    private String address;
    private String roadAddress;
}