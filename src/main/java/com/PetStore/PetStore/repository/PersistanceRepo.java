package com.PetStore.PetStore.repository;

import com.PetStore.PetStore.entity.Address;
import com.PetStore.PetStore.entity.Animal;
import com.PetStore.PetStore.entity.PetStore;
import com.PetStore.PetStore.entity.Product;

public interface PersistanceRepo {
    void persistPetStore(PetStore petStore);

    void persistAddress(Address address);

    void persistProduct(Product product);

    void persistAnimal(Animal animal);

}




