package com.tradeix.pet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class PetImplementationTest {

    protected PetAPI petAPI;

    private static Pet pet1() throws URISyntaxException {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setCategoryName("Category1");
        pet.setPetName("Pet1");
        pet.setStatus(Status.AVAILABLE);
        pet.setTagName("Tag1");
        pet.setPhotoUrl(new URI("http://www.pet1.com/1.png"));
        return pet;
    }

    private static Pet pet2() throws URISyntaxException {
        Pet pet = new Pet();
        pet.setId(2);
        pet.setCategoryName("Category2");
        pet.setPetName("Pet2");
        pet.setStatus(Status.SOLD);
        pet.setTagName("Tag2");
        pet.setPhotoUrl(new URI("http://www.pet2.com/2.png"));
        return pet;
    }

    @Before
    public void setup() {
        petAPI = new PetImplementation();
    }

    /**
     * Create a pet and assert pet is created with appropriate Data
     */
    @Test
    public void addPet() throws Exception {
        Pet createdPet = petAPI.createPet(pet1());
        assertDetails(pet1(), createdPet);
    }

    /**
     * Create a pet, Update pet details and assert details updated
     * Get the updated pet and check details
     */
    @Test
    public void updatePetDetails() throws URISyntaxException {
        petAPI.createPet(pet1());

        Pet pet = petAPI.updatePet(1, pet2());
        assertDetails(pet, pet2());

        pet = petAPI.getPetById(pet2().getId());
        assertDetails(pet, pet2());

    }

    /***
     * Delete the pet and check pet is deleted
     * Get deleted pet and check it returns Null
     * Also check other pets are not deleted
     *
     * @throws URISyntaxException
     */
    @Test
    public void deletePet() throws URISyntaxException {
        petAPI.createPet(pet1());
        petAPI.createPet(pet2());

        boolean status = petAPI.deletePet(pet1().getId());
        Assert.assertTrue(status);

        Pet pet = petAPI.getPetById(pet1().getId());
        Assert.assertNull(pet);

        Assert.assertEquals(petAPI.getAllPets().size(), 1);
        Assert.assertEquals(petAPI.getAllPets().get(0).getId(), pet2().getId());


    }

    /***
     * Create pets and getPet by Id
     * @throws URISyntaxException
     */
    @Test
    public void getPet() throws URISyntaxException {
        petAPI.createPet(pet1());
        petAPI.createPet(pet2());

        List<Pet> allPets = petAPI.getAllPets();
        Assert.assertEquals(allPets.size(), 2);
        assertDetails(allPets.get(0), pet1());
        assertDetails(allPets.get(1), pet2());
    }

    /***
     * Create pets and getAll pets
     * @throws URISyntaxException
     */
    @Test
    public void getPetById() throws URISyntaxException {
        petAPI.createPet(pet1());
        petAPI.createPet(pet2());

        Pet pet = petAPI.getPetById(1);
        assertDetails(pet, pet1());
    }

    private void assertDetails(Pet expected, Pet actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getCategoryName(), actual.getCategoryName());
        Assert.assertEquals(expected.getPetName(), actual.getPetName());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
        Assert.assertEquals(expected.getTagName(), actual.getTagName());
        Assert.assertEquals(expected.getPhotoUrl(), actual.getPhotoUrl());
    }


}
