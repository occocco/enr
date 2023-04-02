package com.enr.task.api.dto.forecast;

import com.enr.task.api.forecast.ForecastCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastResponseDto {

    private String baseDate;
    private String baseTime;
    private String fcstDate;
    private String fcstTime;
    private String category;
    private String fcstValue;

    public static ForecastResponseDto convertResponseDto(ForecastDto.Response.Body.Items.Item item) {
        ForecastCategory forecastCategory = ForecastCategory.valueOf(item.getCategory());
        return new ForecastResponseDto(
                item.getBaseDate(),
                item.getBaseTime(),
                item.getFcstDate(),
                item.getFcstTime(),
                forecastCategory.getName(),
                item.getFcstValue() + forecastCategory.getUnit()
        );
    }
}
