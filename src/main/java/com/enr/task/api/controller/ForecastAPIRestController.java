package com.enr.task.api.controller;

import com.enr.task.api.dto.forecast.ForecastDto;
import com.enr.task.api.dto.forecast.ForecastResponseDto;
import com.enr.task.util.CsvUtil;
import com.enr.task.util.LocationGrid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 시, 군, 구 문자 파라미터를 통해 기상 예보를 조회. ex>"서울특별시", "종로구", "창신동"
 * base_time, base_date를 파라미터로 받으면 원하는 시점의 예보 조회 가능.
 * 편의를 위해 현재 시간을 기준으로 함.
 */
@RestController
public class ForecastAPIRestController {

    @Value("${forecast.api.key}")
    private String apiKey;

    private final List<LocationGrid> locationGrids = CsvUtil.LOCATION_GRIDS;
    private final RestTemplate restTemplate;

    public ForecastAPIRestController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/forecasts/{location}")
    public ResponseEntity<Map<String, List<ForecastResponseDto>>> getForecastByLocation(@PathVariable("location") String location) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        URI uri = getUri(location);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        // api 응답 body JOSN 파싱.
        ForecastDto forecastDto = new ObjectMapper().readValue(response.getBody(), ForecastDto.class);

        List<ForecastDto.Response.Body.Items.Item> forecasts =
                forecastDto.getResponse().getBody().getItems().getItem();

        // 예보 날짜와 시간으로 그룹핑 후 오름차순 정렬
        Map<String, List<ForecastResponseDto>> groupedForecasts =
                forecasts.stream()
                        .map(ForecastResponseDto::convertResponseDto)
                        .collect(Collectors.groupingBy(dto -> dto.getFcstDate() + dto.getFcstTime(), TreeMap::new, Collectors.toList()));

        return ResponseEntity.ok().body(groupedForecasts);

    }


    private URI getUri(String location) {
        int[] xy = getCoordinateByLocation(location);
        String base_time = getHour();
        String base_date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return UriComponentsBuilder.fromUriString("https://apis.data.go.kr")
                .path("/1360000/VilageFcstInfoService_2.0/getVilageFcst")
                .queryParam("serviceKey", apiKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "100")
                .queryParam("dataType", "JSON")
                .queryParam("base_date", base_date)
                .queryParam("base_time", base_time)
                .queryParam("nx", xy[0])
                .queryParam("ny", xy[1])
                .build()
                .encode()
                .toUri();
    }

    /**
     * 스펙 상 nx, ny 파라미터로 예보 조회, 스프링 구동 시 상수로 만든 리스트에서 주소로 xy 값을 찾는 메서드.
     * searchStr는 위치 이름(시, 구, 동)을 입력 받음. 동 -> 구 ->  시 순서로 xy을 찾음.
     */
    private int[] getCoordinateByLocation(String searchStr) {
        return locationGrids.stream()
                .filter(l -> l.getThirdDepth().contains(searchStr))
                .findFirst()
                .orElse(locationGrids.stream()
                        .filter(l -> l.getSecondDepth().contains(searchStr))
                        .findFirst()
                        .orElse(locationGrids.stream()
                                .filter(l -> l.getFirstDepth().contains(searchStr))
                                .findFirst()
                                .orElse(null)))
                .getXy();
    }

    /**
     * 예보 시간이 0200 0500 0800 1100 1400 1700 2000 2300. 이 외 시간으로 요청 시 오류.
     * 현재 시각에서 가까운 위의 시각을 구하는 메서드.
     */
    private String getHour() {

        LocalDateTime now = LocalDateTime.now();
        LocalTime[] times = {
                LocalTime.of(2, 0), LocalTime.of(5, 0), LocalTime.of(8, 0),
                LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(17, 0),
                LocalTime.of(20, 0), LocalTime.of(23, 0)};
        LocalTime closestTime = LocalTime.from(Arrays.stream(times)
                .map(time -> LocalDateTime.of(now.toLocalDate(), time))
                .min(Comparator.comparing(time -> Math.abs(Duration.between(now, time).toMinutes())))
                .orElse(null));
        return Arrays.stream(times)
                .filter(time -> time.isBefore(closestTime))
                .max(Comparator.naturalOrder())
                .orElse(null)
                .format(DateTimeFormatter.ofPattern("HHmm"));
    }
}
