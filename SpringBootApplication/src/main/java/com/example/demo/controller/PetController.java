package com.example.demo.controller;

import com.example.demo.model.Pet;
import com.example.demo.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Pet> findAllPets() {
        return petService.getAllPets();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pet findPetById(@PathVariable Integer id) {
        return petService.getPetById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Pet addPet(@RequestBody Pet pet) {
        return petService.createPet(pet);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Pet updatePet(@RequestBody Pet pet, @PathVariable Integer id) {
        return petService.updatePet(id, pet);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePet(@PathVariable Integer id) {
        petService.deletePet(id);
    }
}
