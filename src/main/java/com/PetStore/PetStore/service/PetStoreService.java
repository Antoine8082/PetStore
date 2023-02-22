package com.PetStore.PetStore.service;

import com.PetStore.PetStore.entity.*;
import jakarta.persistence.*;

import java.sql.Date;

import java.util.List;

public class PetStoreService {
    public static void main(String[] args) {
        // Instancier l'EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PetStore");

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

        // Création de trois Produits
        Product product1 = new Product();
        product1.setCode("F-001");
        product1.setLabel("Nourriture pour chats");
        product1.setType(ProdType.FOOD);
        product1.setPrice(5.99);

        Product product2 = new Product();
        product2.setCode("C-002");
        product2.setLabel("Litière pour chats");
        product2.setType(ProdType.CLEANING);
        product2.setPrice(7.99);

        Product product3 = new Product();
        product3.setCode("A-003");
        product3.setLabel("Jouet pour chats");
        product3.setType(ProdType.ACCESSORY);
        product3.setPrice(3.99);

        // Association entre le PetStore et les Produits
        petStore.getProducts().add(product1);
        product1.getPetStores().add(petStore);

        petStore.getProducts().add(product2);
        product2.getPetStores().add(petStore);

        petStore.getProducts().add(product3);
        product3.getPetStores().add(petStore);

        // Création de trois Chat
        Cat cat1 = new Cat();
        String str1="2020-12-31";
        Date date1=Date.valueOf(str1);
        cat1.setBirth(date1);
        cat1.setCouleur("blanc");
        cat1.setChipId("123456");

        Cat cat2 = new Cat();
        String str2="2021-10-17";
        Date date2=Date.valueOf(str2);
        cat2.setBirth(date2);
        cat2.setCouleur("noir");
        cat2.setChipId("789012");

        Cat cat3 = new Cat();
        String str3="2022-09-19";
        Date date3=Date.valueOf(str3);
        cat3.setBirth(date3);
        cat3.setCouleur("roux");
        cat3.setChipId("345678");

        // Association entre le PetStore et les Chats
        petStore.getAnimals().add(cat1);
        cat1.setPetStore(petStore);

        petStore.getAnimals().add(cat2);
        cat2.setPetStore(petStore);

        petStore.getAnimals().add(cat3);
        cat3.setPetStore(petStore);

        // Création de trois Poisons
        Fish fish1 = new Fish();
        String str4="2021-10-17";
        Date date4=Date.valueOf(str4);
        fish1.setBirth(date4);
        fish1.setCouleur("vert");
        fish1.setLivingEnv(FishLivEnv.FRESH_WATER);

        Fish fish2 = new Fish();
        String str5="2019-03-06";
        Date date5=Date.valueOf(str5);
        fish2.setBirth(date5);
        fish2.setCouleur("orange");
        fish2.setLivingEnv(FishLivEnv.SEA_WATER);

        Fish fish3 = new Fish();
        String str6="2023-02-11";
        Date date6=Date.valueOf(str6);
        fish3.setBirth(date6);
        fish3.setCouleur("jaune");
        fish3.setLivingEnv(FishLivEnv.FRESH_WATER);

        // Association entre le PetStore et les Poisons
        petStore.getAnimals().add(fish1);
        fish1.setPetStore(petStore);

        petStore.getAnimals().add(fish2);
        fish2.setPetStore(petStore);

        petStore.getAnimals().add(fish3);
        fish3.setPetStore(petStore);

        // Persistance des entités
        entityManager.persist(petStore);
        entityManager.persist(address);
        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(product3);
        entityManager.persist(cat1);
        entityManager.persist(cat2);
        entityManager.persist(cat3);
        entityManager.persist(fish1);
        entityManager.persist(fish2);
        entityManager.persist(fish3);

        transaction.commit();

        // Récupération des entités depuis la base de données
        // Récupération des animaux d'un PetStore donné
        Query query = entityManager.createQuery("SELECT a FROM Animal a WHERE a.petStore.name = :name");
        query.setParameter("name", "Animalerie_1");
        List<Animal> animals = query.getResultList();
        for (Animal animal : animals) {
            System.out.println(animal);
        }
        // Fermeture de l'EntityManager et l'EntityManagerFactory
//        entityManager.close();
//        entityManagerFactory.close();
    }
}