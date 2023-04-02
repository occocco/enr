package com.enr.task.api.dto.forecast;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 오픈 api 응답 스펙에 맞춘 DTO클래스.
 */
@Data
@ToString
public class ForecastDto {
    
    private Response response;

    @Data
    public static class Response {
        private Header header;
        private Body body;

        @Data
        public static class Header {
            private String resultCode; // 응답코드 ResultCode Enum 참조.
            private String resultMsg; // 응답메시지 ResultCode Enum 참조.
        }

        @Data
        public static class Body {
            private String dataType; // 응답 데이터 타입 (XML, JSON)
            private Items items;
            private int pageNo; // 페이지 번호
            private int numOfRows; // 한 페이지 결과 개수
            private int totalCount; // 데이터 총 개수

            @Data
            public static class Items {
                private List<Item> item;

                @Data
                public static class Item {
                    private String baseDate; // 발표일자
                    private String baseTime; // 발표시각
                    private String category; // 자료구분문자. ForecastCategory enum 참조
                    private String fcstDate; // 예보일자
                    private String fcstTime; // 예보시각
                    private String fcstValue; //예보 값.
                    private int nx; // 경도
                    private int ny; // 위도
                }
            }
        }
    }
}


