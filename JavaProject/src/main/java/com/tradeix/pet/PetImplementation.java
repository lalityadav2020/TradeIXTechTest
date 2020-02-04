package com.tradeix.pet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetImplementation implements PetAPI {

    private Map<Integer, Pet> petStorage = new HashMap<>();

    public Pet createPet(Pet pet) {
        petStorage.put(pet.getId(), pet);
        return pet;
    }

    public Pet getPetById(Integer petId) {
        return petStorage.get(petId);
    }

    public List<Pet> getAllPets() {
        return new ArrayList<>(petStorage.values());
    }

    public boolean deletePet(Integer petId) {
        return petStorage.remove(petId) != null;
    }

    public Pet updatePet(Integer petId, Pet pets) {
        if (petStorage.containsKey(petId)) {
            petStorage.remove(petId);
            petStorage.put(pets.getId(), pets);
            return getPetById(pets.getId());
        } else {
            return null;
        }
    }
}
