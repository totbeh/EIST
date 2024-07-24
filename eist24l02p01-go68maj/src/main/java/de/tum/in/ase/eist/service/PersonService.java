package de.tum.in.ase.eist.service;

import de.tum.in.ase.eist.model.Person;
import de.tum.in.ase.eist.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        if (person.getBirthday().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Birthday may not be in the future");
        }
        return personRepository.save(person);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }

    public Optional<Person> getById(Long id) {
        return personRepository.findWithParentsAndChildrenById(id);
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person addParent(Person person, Person parent){
        if (person.getParents().size() > 1){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
        else {
            person.getParents().add(parent);
            return personRepository.save(person);
        }

    }

    public Person addChild(Person person, Person child) {
        if (child.getParents().size() > 1){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
        person.getChildren().add(child);
        return personRepository.save(person);
    }

    public Person removeParent(Person person, Person parent) {
        if (person.getParents().size() > 1){
            person.getParents().remove(parent);
            return personRepository.save(person);
        }
        throw new ResponseStatusException(HttpStatusCode.valueOf(400));

    }

    public Person removeChild(Person person, Person child) {
        if (child.getParents().size() <= 1){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
        person.getChildren().remove(child);
        return personRepository.save(person);
    }
}
