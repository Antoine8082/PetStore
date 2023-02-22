package com.PetStore.PetStore.repository;

import com.PetStore.PetStore.entity.Address;
import com.PetStore.PetStore.entity.Animal;
import com.PetStore.PetStore.entity.PetStore;
import com.PetStore.PetStore.entity.Product;

import jakarta.persistence.EntityManager;

public class AssociationRepoImpl implements AssociationRepo {
    private EntityManager entityManager;

    public AssociationRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void associatePetStoreWithAddress(PetStore petStore, Address address) {
        petStore.setAddress(address);
        address.setPetStore(petStore);
        entityManager.persist(petStore);
        entityManager.persist(address);
    }

    @Override
    public void associatePetStoreWithProduct(PetStore petStore, Product product) {
        petStore.getProducts().add(product);
        product.getPetStores().add(petStore);
        entityManager.persist(petStore);
        entityManager.persist(product);
    }

    @Override
    public void associatePetStoreWithAnimal(PetStore petStore, Animal animal) {
        petStore.getAnimals().add(animal);
        animal.setPetStore(petStore);
        entityManager.persist(petStore);
        entityManager.persist(animal);
    }
}
