package edu.spring.rest.controllers;

import edu.spring.domain.Country;
import edu.spring.repostory.CountryRepository;
import edu.spring.rest.NotFoundException;
import edu.spring.rest.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryRepository repository;

    @GetMapping("/api/country")
    public List<CountryDto> getAll() {
        return repository.findAll().stream()
                .map(CountryDto::toDto)
                .collect(toList());
    }

    @GetMapping("/api/country/{id}")
    public CountryDto getById(@PathVariable("id") int id) {
        Country country = repository.findById(id);
        if(country == null) {
            throw new NotFoundException("Country ID not found");
        }
        return CountryDto.toDto(country);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/country")
    public CountryDto create(@RequestBody CountryDto dto) {
        Country fromRequest = CountryDto.toDomainObject(dto);
        Country fromDb = repository.save(fromRequest);
        return CountryDto.toDto(fromDb);
    }

    @PutMapping("/api/country/{id}")
    public CountryDto update(@RequestBody CountryDto dto) {
        Country fromRequest = CountryDto.toDomainObject(dto);
        Country fromDb = repository.save(fromRequest);
        return CountryDto.toDto(fromDb);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/country/{id}")
    public CountryDto delete(@PathVariable("id") int id) {
        Country country = repository.findById(id);
        if(country == null) {
            throw new NotFoundException("Country ID not found");
        }
        repository.delete(country);
        return CountryDto.toDto(country);
    }
}