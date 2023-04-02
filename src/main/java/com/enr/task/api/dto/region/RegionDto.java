package com.enr.task.api.dto.region;

import com.enr.task.domain.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {

    private Integer id;
    private String name;

    public static RegionDto fromEntity(Region region) {
        return new RegionDto(
                region.getId(),
                region.getName()
        );
    }
}
