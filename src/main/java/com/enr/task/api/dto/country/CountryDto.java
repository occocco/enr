package com.enr.task.api.dto.country;

import com.enr.task.domain.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {

    private String countryId;
    private String countryName;

    public static CountryDto fromEntity(Country country) {
        return new CountryDto(
                country.getId(),
                country.getName()
        );
    }
}
