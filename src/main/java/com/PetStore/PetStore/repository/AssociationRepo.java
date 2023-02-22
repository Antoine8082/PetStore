package com.PetStore.PetStore.repository;

import com.PetStore.PetStore.entity.Address;
import com.PetStore.PetStore.entity.Animal;
import com.PetStore.PetStore.entity.PetStore;
import com.PetStore.PetStore.entity.Product;

public interface AssociationRepo {
    void associatePetStoreWithAddress(PetStore petStore, Address address);
    void associatePetStoreWithProduct(PetStore petStore, Product product);
    void associatePetStoreWithAnimal(PetStore petStore, Animal animal);
}
