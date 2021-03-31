package edu.spring.rest.controllers;

import edu.spring.domain.Person;
import edu.spring.repostory.PersonRepository;
import edu.spring.rest.NotFoundException;
import edu.spring.rest.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository repository;

    @GetMapping("/api/person")
    public List<PersonDto> getAll() {
        return repository.findAll().stream()
                .map(PersonDto::toDto)
                .collect(toList());
    }

    @GetMapping("/api/person/{id}")
    public PersonDto getById(@PathVariable("id") int id) {
        Person person = repository.findById(id).orElse(null);
        if(person == null) {
            throw new NotFoundException("Person ID not found");
        }
        return PersonDto.toDto(person);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/person")
    public PersonDto create(@RequestBody PersonDto dto) {
        Person fromRequest = PersonDto.toDomainObject(dto);
        Person fromDb = repository.save(fromRequest);
        return PersonDto.toDto(fromDb);
    }

    @PutMapping("/api/person")
    public PersonDto update(@RequestBody PersonDto dto) {
        Person fromRequest = PersonDto.toDomainObject(dto);
        Person fromDb = repository.save(fromRequest);
        return PersonDto.toDto(fromDb);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/person/{id}")
    public PersonDto delete(@PathVariable("id") int id) {
        Person person = repository.findById(id).orElse(null);
        if(person == null) {
            throw new NotFoundException("Person ID not found");
        }
        repository.delete(person);
        return PersonDto.toDto(person);
    }

    @PutMapping("/api/person/{id}/name")
    public ResponseEntity<PersonDto> updateName(@PathVariable("id") int id, @RequestParam("name") String name) {
        Person person = repository.findById(id).orElse(null);
        if(person == null) {
            throw new NotFoundException("Person ID not found");
        }
        if(name != null && !name.equals("")) {
            person.setName(name);
            repository.save(person);
            return new ResponseEntity<>(PersonDto.toDto(person), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
