package com.PetStore.PetStore.service;

import com.PetStore.PetStore.entity.*;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

public class PetStoreService {
    public static void main(String[] args) {
        // Instancier l'EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("petstore");

        // Instancier un EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Créer et persister des entités en base de données
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Création d'un PetStore
        PetStore petStore = new PetStore();
        petStore.setName("Animalerie_1");
        petStore.setManagerName("John Doe");

        // Création d'une adresse
        Address address = new Address();
        address.setNumber("10");
        address.setStreet("Rue de la Paix");
        address.setZipCode("44000");
        address.setCity("Nantes");

        // Association entre le PetStore et l'Adresse
        petStore.setAddress(address);
        address.setPetStore(petStore);

        // Création d'un Produit
        Product product = new Product();
        product.setCode("P-001");
        product.setLabel("Nourriture pour chats");
        product.setType(ProdType.FOOD);
        product.setPrice(5.99);

        // Association entre le PetStore et le Produit
        petStore.getProducts().add(product);
        product.getPetStores().add(petStore);

        // Création d'un Chat
        Cat cat = new Cat();
        cat.setBirth(new Date());
        cat.setCouleur("blanc");
        cat.setChipId("123456");

        // Association entre le PetStore et le Chat
        petStore.getAnimals().add(cat);
        cat.setPetStore(petStore);

        // Persistance des entités
        entityManager.persist(petStore);
        entityManager.persist(address);
        entityManager.persist(product);
        entityManager.persist(cat);

        transaction.commit();

        // Récupération des entités depuis la base de données
        // Récupération des animaux d'un PetStore donné
        Query query = entityManager.createQuery("SELECT a FROM Animal a WHERE a.petStore.name = :name");
        query.setParameter("name", "Animalerie_1");
        List<Animal> animals = query.getResultList();
        for (Animal animal : animals) {
            System.out.println(animal);
        }
        // Fermer l'EntityManager et l'EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
}