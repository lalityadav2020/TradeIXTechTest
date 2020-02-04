package com.tradeix.pet;

import java.util.List;

public interface PetAPI {

    Pet createPet(Pet pet);

    Pet getPetById(Integer petId);

    List<Pet> getAllPets();

    boolean deletePet(Integer petId);

    Pet updatePet(Integer petId, Pet pets);
}
