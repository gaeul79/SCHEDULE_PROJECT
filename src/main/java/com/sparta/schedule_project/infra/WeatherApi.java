package com.sparta.schedule_project.infra;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 날씨 정보를 제공하는 서비스 클래스입니다.
 * zewnętrzne 날씨 API(https://f-api.github.io)를 호출하여 오늘 날씨 정보를 가져옵니다.
 *
 * @since 2024-10-17
 */
@Service
public class WeatherApi {
    private final RestTemplate restTemplate;

    public WeatherApi(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    /**
     * 오늘 날씨 정보를 String 형태로 반환합니다.
     * 외부 날씨 API를 호출하여 JSON 데이터를 파싱하고, 오늘 날씨 정보를 추출합니다.
     *
     * @return 오늘 날씨 정보 (예: 맑음, 흐림, 비)
     * @throws RuntimeException API 호출中に 예외가 발생하는 경우
     * @since 2024-10-17
     */
    public String getTodayWeather() {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://f-api.github.io")
                .path("/f-api/weather.json")
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        return fromJSONtoWeather(responseEntity.getBody());
    }

    /**
     * JSON 데이터를 파싱하여 오늘 날씨 정보를 추출합니다.
     *
     * @param responseEntity API 응답 본문 (String 형태의 JSON 데이터)
     * @return 오늘 날씨 정보 (예: 맑음, 흐림, 비), 데이터를 찾을 수 없는 경우 "Not Found" 문자열 반환
     * @since 2024-10-17
     */
    private String fromJSONtoWeather(String responseEntity) {
        JSONArray weatherItems = new JSONArray(responseEntity);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd");
        String today = dateFormat.format(LocalDate.now());

        for (int idx = 0; idx < weatherItems.length(); idx++) {
            JSONObject weatherItem = weatherItems.getJSONObject(idx);
            String date = weatherItem.getString("date");

            if (date.equals(today)) {
                return weatherItem.getString("weather");
            }
        }

        return "Not Found";
    }
}
