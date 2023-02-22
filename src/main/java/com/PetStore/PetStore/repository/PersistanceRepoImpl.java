package com.PetStore.PetStore.repository;

import com.PetStore.PetStore.entity.Address;
import com.PetStore.PetStore.entity.Animal;
import com.PetStore.PetStore.entity.PetStore;
import com.PetStore.PetStore.entity.Product;
import jakarta.persistence.EntityManager;

public class PersistanceRepoImpl implements PersistanceRepo {
    private EntityManager entityManager;

    public PersistanceRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persistPetStore(PetStore petStore) {
        entityManager.persist(petStore);
    }

    @Override
    public void persistAddress(Address address) {
        entityManager.persist(address);
    }

    @Override
    public void persistProduct(Product product) {
        entityManager.persist(product);
    }

    @Override
    public void persistAnimal(Animal animal) {
        entityManager.persist(animal);
    }
}