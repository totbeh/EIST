package de.tum.in.ase.eist;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tum.in.ase.eist.model.Person;
import de.tum.in.ase.eist.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class PersonIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository personRepository;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void testAddPerson() throws Exception {
        var person = new Person();
        person.setFirstName("Max");
        person.setLastName("Mustermann");
        person.setBirthday(LocalDate.now());

        var response = this.mvc.perform(
                post("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType("application/json")
        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(1, personRepository.findAll().size());
    }


    @Test
    void testDeletePerson() throws Exception {
        var person = new Person();
        person.setFirstName("Max");
        person.setLastName("Mustermann");
        person.setBirthday(LocalDate.now());

        person = personRepository.save(person);

        var response = this.mvc.perform(
                delete("/persons/" + person.getId())
                        .contentType("application/json")
        ).andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertTrue(personRepository.findAll().isEmpty());
    }

    @Test
    void testAddParent()throws Exception{
       //initialize parent
        var parent = new Person();
        parent.setFirstName("Max");
        parent.setLastName("Mustermann");
        parent.setBirthday(LocalDate.now());
        //initialize child
        var child = new Person();
        child.setFirstName("Thomas");
        child.setLastName("Müller");
        child.setBirthday(LocalDate.now());
        //saving parent and child
        personRepository.save(parent);
        personRepository.save(child);
        //first assertion
        assertEquals(2, personRepository.findAll().size());
        var response2 = this.mvc.perform(
                put("/persons/"+child.getId()+"/parents")
                        .content(objectMapper.writeValueAsString(parent))
                        .contentType("application/json")
        ).andReturn().getResponse();
        child = personRepository.findWithParentsById(child.getId()).orElseThrow();
        assertEquals(HttpStatus.OK.value(), response2.getStatus());
        assertEquals(1,child.getParents().size()); //failed assertion: expected 1; actual 0

    }
@Test
    void testAddThreeParents() throws Exception{
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
        assertEquals(4, personRepository.findAll().size());


        var response1 = this.mvc.perform(
                put("/persons/"+child.getId()+"/parents")
                        .content(objectMapper.writeValueAsString(parent1))
                        .contentType("application/json")
        ).andReturn().getResponse();
        child = personRepository.findWithParentsById(child.getId()).orElseThrow();
        assertEquals(HttpStatus.OK.value(), response1.getStatus());
        assertEquals(1,child.getParents().size());


        var response2 = this.mvc.perform(
                put("/persons/"+child.getId()+"/parents")
                        .content(objectMapper.writeValueAsString(parent2))
                        .contentType("application/json")
        ).andReturn().getResponse();
        child = personRepository.findWithParentsById(child.getId()).orElseThrow();
        assertEquals(HttpStatus.OK.value(), response2.getStatus());
        assertEquals(2,child.getParents().size());



        try {
            var response3 = this.mvc.perform(
                    put("/persons/"+child.getId()+"/parents")
                            .content(objectMapper.writeValueAsString(parent3))
                            .contentType("application/json")
            ).andReturn().getResponse();        }
        catch (ResponseStatusException ignored){

        }




    }


}
