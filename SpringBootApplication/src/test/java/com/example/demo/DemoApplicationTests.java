package com.example.demo;

import com.example.demo.model.Pet;
import com.example.demo.model.Status;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void testCreateTicket() throws Exception {
        Pet pet = pet1();
        HttpEntity<Pet> entity = new HttpEntity<>(pet, headers);
        String URIToCreatePet = "/pet";
        ResponseEntity<Pet> response = testRestTemplate.exchange(
                formFullURLWithPort(URIToCreatePet),
                HttpMethod.POST, entity, Pet.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertPetDetails(pet, response);

    }

    @Test
    public void testGetPetById() throws Exception {

        //Create Pet
        Pet pet = pet2();
        String URIToGetPet = "/pet";
        HttpEntity<Pet> entity = new HttpEntity<>(pet, headers);
        testRestTemplate.exchange(formFullURLWithPort(URIToGetPet),
                HttpMethod.POST, entity, String.class);

        //Get Pet
        String URI = "/pet/1";
        ResponseEntity<Pet> response = testRestTemplate.exchange(formFullURLWithPort(URI), HttpMethod.GET, null, Pet.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertPetDetails(pet, response);
    }

    @Test
    public void testUpdatePet() throws Exception {

        //Create a Pet
        Pet pet = pet2();
        String URIToCreateTicket = "/pet";
        HttpEntity<Pet> entity = new HttpEntity<>(pet, headers);
        ResponseEntity<Pet> response = testRestTemplate.exchange(
                formFullURLWithPort(URIToCreateTicket),
                HttpMethod.POST, entity, Pet.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Integer petId = Objects.requireNonNull(response.getBody()).getId();

        //Update Pet
        String URIToUpdatePet = "/pet/" + petId;
        Pet updatedPet = new Pet();
        updatedPet.setId(petId);
        updatedPet.setCategoryName("updatePet2");
        updatedPet.setPetName("updateTom2");
        updatedPet.setTagName("updateNewTom2");
        updatedPet.setStatus(Status.SOLD);
        updatedPet.setPhotoUrl(new URI("http://updateNewTom2.jpg"));

        entity = new HttpEntity<>(updatedPet, headers);
        ResponseEntity<Pet> updateResponse = testRestTemplate.exchange(
                formFullURLWithPort(URIToUpdatePet),
                HttpMethod.PUT, entity, Pet.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertPetDetails(updatedPet, updateResponse);


        //assert pet is updated
        String URI = "/pet/" + petId;
        ResponseEntity<Pet> getResponse = testRestTemplate.exchange(formFullURLWithPort(URI), HttpMethod.GET, null, Pet.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertPetDetails(updatedPet, getResponse);

    }


    @Test
    public void testDeletePet() throws Exception {

        //Create a Pet
        Pet pet = pet2();
        String URIToCreateTicket = "/pet";
        HttpEntity<Pet> entity = new HttpEntity<>(pet, headers);
        ResponseEntity<Pet> response = testRestTemplate.exchange(
                formFullURLWithPort(URIToCreateTicket),
                HttpMethod.POST, entity, Pet.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Integer petId = Objects.requireNonNull(response.getBody()).getId();


        String URIToDeletePet = "/pet/" + petId;
        ResponseEntity<Object> deleteResponse = testRestTemplate.exchange(
                formFullURLWithPort(URIToDeletePet),
                HttpMethod.DELETE, entity, Object.class);
        Assert.assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        //assert pet is deleted
        String URI = "/pet/" + petId;
        ResponseEntity<Object> getResponse = testRestTemplate.exchange(formFullURLWithPort(URI), HttpMethod.GET, null, Object.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        Assert.assertNotNull(getResponse.getBody());
        assertThat(getResponse.getBody().toString().contains("pet.not.found"));


    }

    private Pet pet1() throws URISyntaxException {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setCategoryName("pet1");
        pet.setPetName("Tom");
        pet.setTagName("NewTom");
        pet.setStatus(Status.AVAILABLE);
        pet.setPhotoUrl(new URI("http://NewTom.jpg"));
        return pet;
    }

    private Pet pet2() throws URISyntaxException {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setCategoryName("pet2");
        pet.setPetName("Tom2");
        pet.setTagName("NewTom2");
        pet.setStatus(Status.SOLD);
        pet.setPhotoUrl(new URI("http://NewTom2.jpg"));
        return pet;
    }


    private void assertPetDetails(Pet expectedPet, ResponseEntity<Pet> response) {
        Assert.assertNotNull(response.getBody());
        assertThat(response.getBody().getCategoryName()).isEqualTo(expectedPet.getCategoryName());
        assertThat(response.getBody().getPetName()).isEqualTo(expectedPet.getPetName());
        assertThat(response.getBody().getStatus()).isEqualTo(expectedPet.getStatus());
        assertThat(response.getBody().getPhotoUrl()).isEqualTo(expectedPet.getPhotoUrl());
        assertThat(response.getBody().getTagName()).isEqualTo(expectedPet.getTagName());
    }


    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}