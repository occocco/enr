package com.enr.task.api.dto.location;

import com.enr.task.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    private Integer id;
    private String city;
    private String stateProvince;

    public static LocationDto fromEntity(Location location) {
        return new LocationDto(
                location.getId(),
                location.getCity(),
                location.getStateProvince()
        );
    }
}
