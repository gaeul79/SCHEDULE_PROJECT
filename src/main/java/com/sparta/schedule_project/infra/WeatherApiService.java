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

@Service
public class WeatherApiService {
    private final RestTemplate restTemplate;

    public WeatherApiService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

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

    private String fromJSONtoWeather(String responseEntity) {
        JSONArray weatherItems = new JSONArray(responseEntity);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd");
        String today = dateFormat.format(LocalDate.now());

        for(int idx = 0; idx < weatherItems.length(); idx++) {
            JSONObject weatherItem = weatherItems.getJSONObject(idx);
            String date = weatherItem.getString("date");

            if(date.equals(today)) {
                return weatherItem.getString("weather");
            }
        }

        return "Not Found";
    }
}
