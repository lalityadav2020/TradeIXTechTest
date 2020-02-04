package com.example.demo.service.impl;

import com.example.demo.exception.NoPetFoundException;
import com.example.demo.model.Pet;
import com.example.demo.repository.PetRepository;
import com.example.demo.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPetById(Integer petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        if (pet.isPresent()) {
            return pet.get();
        } else {
            throw new NoPetFoundException("pet.not.found");
        }
    }

    public Iterable<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public void deletePet(Integer petId) {
        petRepository.deleteById(petId);
    }

    public Pet updatePet(Integer petId, Pet pet) {
        Pet existingPet = getPetById(petId);
        existingPet.setCategoryName(pet.getCategoryName());
        existingPet.setPetName(pet.getPetName());
        existingPet.setStatus(pet.getStatus());
        existingPet.setTagName(pet.getTagName());
        existingPet.setPhotoUrl(pet.getPhotoUrl());
        return petRepository.save(existingPet);
    }

}
