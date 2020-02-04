package com.example.demo.service;

import com.example.demo.model.Pet;


public interface PetService {

    Pet createPet(Pet pet);

    Pet getPetById(Integer petId);

    Iterable<Pet> getAllPets();

    void deletePet(Integer petId);

    Pet updatePet(Integer petId, Pet pets);

}
