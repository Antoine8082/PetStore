package com.PetStore.PetStore.repository;

import com.PetStore.PetStore.entity.Address;
import com.PetStore.PetStore.entity.Animal;
import com.PetStore.PetStore.entity.PetStore;
import com.PetStore.PetStore.entity.Product;

import java.util.List;

public interface AssociationRepo {
    void associatePetStoreWithAddress(PetStore petStore, Address address);
    void associatePetStoreWithProduct(PetStore petStore, Product product);

    void associatePetStoreWithProduct(PetStore petStore, List<Product> products);

    void associatePetStoreWithAnimal(PetStore petStore, Animal animal);

    void associatePetStoreWithAnimal(PetStore petStore, List<Animal> animals);
}
