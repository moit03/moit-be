package com.sparta.moit.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moit.global.common.dto.AddressResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "좌표변환 Util")
@Service
public class AddressUtil {

    private final RestTemplate restTemplate;

    public AddressUtil(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public AddressResponseDto searchAddress(String regionFirstName, String regionSecondName) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://sgisapi.kostat.go.kr/OpenAPI3/addr/geocodewgs84.json")
                .queryParam("accessToken", "f739ec35-e148-45c0-bf70-54528bdbbb38")
                .queryParam("address", regionFirstName + " " + regionSecondName)
                .encode()
                .build()
                .toUri();
        log.info("uri = " + uri);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .headers(headers)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        log.info("주소 API Status Code : " + responseEntity.getStatusCode());
        JsonNode jsonNode = new ObjectMapper().readTree(responseEntity.getBody());
        JsonNode resultData = jsonNode.get("result").get("resultdata");

        log.info("jsonNode.get(id) " + jsonNode.get("id").asText());
        String lat = resultData.get(0).get("y").asText();
        String lng = resultData.get(0).get("x").asText();

        return new AddressResponseDto(lat, lng);
    }

}
