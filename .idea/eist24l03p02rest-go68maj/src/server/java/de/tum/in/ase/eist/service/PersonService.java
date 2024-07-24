package de.tum.in.ase.eist.service;

import de.tum.in.ase.eist.model.Person;
import de.tum.in.ase.eist.util.PersonSortingOptions;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {
  	// do not change this
    private final List<Person> persons;

    public PersonService() {
        this.persons = new ArrayList<>();
    }

    public Person savePerson(Person person) {
        var optionalPerson = persons.stream().filter(existingPerson -> existingPerson.getId().equals(person.getId())).findFirst();
        if (optionalPerson.isEmpty()) {
            person.setId(UUID.randomUUID());
            persons.add(person);
            return person;
        } else {
            var existingPerson = optionalPerson.get();
            existingPerson.setFirstName(person.getFirstName());
            existingPerson.setLastName(person.getLastName());
            existingPerson.setBirthday(person.getBirthday());
            return existingPerson;
        }
    }

    public void deletePerson(UUID personId) {
        this.persons.removeIf(person -> person.getId().equals(personId));
    }

    public List<Person> getAllPersons(PersonSortingOptions sortingOptions) {
        List<Person> sortedPersons = new ArrayList<>(persons);
        Comparator<Person> comparator = getComparatorBasedOnOptions(sortingOptions);

        if (sortingOptions.getSortingOrder() == PersonSortingOptions.SortingOrder.DESCENDING) {
            comparator = comparator.reversed();
        }

        sortedPersons.sort(comparator);
        return sortedPersons;
    }

    private Comparator<Person> getComparatorBasedOnOptions(PersonSortingOptions options) {
        return switch (options.getSortField()) {
            case FIRST_NAME -> Comparator.comparing(Person::getFirstName, String.CASE_INSENSITIVE_ORDER);
            case LAST_NAME -> Comparator.comparing(Person::getLastName, String.CASE_INSENSITIVE_ORDER);
            case BIRTHDAY -> Comparator.comparing(Person::getBirthday);
            default -> Comparator.comparing(Person::getId);
        };
    }

}
