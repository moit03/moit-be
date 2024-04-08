package com.sparta.moit.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moit.global.common.dto.AddressResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j(topic = "좌표변환 Util")
@Service
public class AddressUtil {

    private final RestTemplate restTemplate;

    public AddressUtil(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
    /* DB 위도-경도 저장용 메서드 (사용 중지) */
    public AddressResponseDto searchAddress(String regionFirstName, String regionSecondName) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://sgisapi.kostat.go.kr/OpenAPI3/addr/geocodewgs84.json")
                .queryParam("accessToken", "f68447c5-ecdd-4e40-98af-d3ecf40dfb27")
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
