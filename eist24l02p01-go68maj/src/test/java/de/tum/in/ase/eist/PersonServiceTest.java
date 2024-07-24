package de.tum.in.ase.eist;

import de.tum.in.ase.eist.model.Person;
import de.tum.in.ase.eist.repository.PersonRepository;
import de.tum.in.ase.eist.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @Test
    void testAddPerson() {
        var person = new Person();
        person.setFirstName("Max");
        person.setLastName("Mustermann");
        person.setBirthday(LocalDate.now());

        personService.save(person);

        assertEquals(1, personRepository.findAll().size());
    }

    @Test
    void testDeletePerson() {
        var person = new Person();
        person.setFirstName("Max");
        person.setLastName("Mustermann");
        person.setBirthday(LocalDate.now());

        person = personRepository.save(person);

        personService.delete(person);

        assertTrue(personRepository.findAll().isEmpty());
    }



    // TODO: Add more test cases here

    @Test
    void testAddParent(){
        Person parent = new Person();
        Person child = new Person();
        personRepository.save(parent);
        personRepository.save(child);
        personService.addParent(child,parent);
        assertEquals(2, personRepository.findAll().size());
        assertEquals(1,child.getParents().size());

    }

    @Test
    void testAddThreeParents(){
        var parent1 = new Person();
        var parent2 = new Person();
        var parent3 = new Person();
        var child = new Person();
        parent1.setFirstName("mohsen");
        parent1.setLastName("abdelahmid");
        parent1.setBirthday(LocalDate.EPOCH);

        parent2.setFirstName("manga");
        parent2.setLastName("kusaw");
        parent2.setBirthday(LocalDate.now());
        parent3.setFirstName("batta");
        parent3.setLastName("namla");
        parent3.setBirthday(LocalDate.now());
        child.setFirstName("hamada");
        child.setLastName("bolbol");
        child.setBirthday(LocalDate.now());

        personRepository.save(parent1);
        personRepository.save(parent2);
        personRepository.save(parent3);
        personRepository.save(child);
       // long childId = child.getId();
        assertEquals(4, personRepository.findAll().size());
        personService.addParent(child,parent1);
        child.setId(null);
        assertEquals(1,child.getParents().size());
        personService.addParent(child,parent2);
        assertEquals(2,child.getParents().size());



        try {
            personService.addParent(child,parent3);
        }
        catch (ResponseStatusException ignored){

        }




    }
}
