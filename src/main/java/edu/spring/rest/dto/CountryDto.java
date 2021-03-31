package edu.spring.rest.dto;

import edu.spring.domain.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO that represents Account
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CountryDto {
    private int id = 1;

    private String name;

    private String code_name;

    public static Country toDomainObject(CountryDto dto) {
        return new Country(dto.getId(), dto.getName(), dto.getCode_name());
    }

    public static CountryDto toDto(Country country) {
        return new CountryDto(country.getId(), country.getName(), country.getCodeName());
    }
}
