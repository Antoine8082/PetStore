package com.PetStore.PetStore.service;

import com.PetStore.PetStore.entity.*;
import com.PetStore.PetStore.repository.AssociationRepo;
import com.PetStore.PetStore.repository.AssociationRepoImpl;
import com.PetStore.PetStore.repository.PersistanceRepo;
import com.PetStore.PetStore.repository.PersistanceRepoImpl;
import jakarta.persistence.*;


import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

public class PetStoreService {
    private static PersistanceRepo persistanceRepo;
    public PetStoreService(PersistanceRepo persistanceRepo) {
        this.persistanceRepo = persistanceRepo;
    }
    public static void main(String[] args) {
        // Instancier l'EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PetStore");

        // Instancier un EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        PersistanceRepo persistanceRepo = new PersistanceRepoImpl(entityManager);
        AssociationRepo associationRepo = new AssociationRepoImpl(entityManager);

        // Créer et persister des entités en base de données
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Création d'un PetStore
        PetStore petStore = new PetStore();
        petStore.setName("Animalerie_Doe");
        petStore.setManagerName("John Doe");

        // Création d'une adresse
        Address address = new Address();
        address.setNumber("10");
        address.setStreet("Rue de la Paix");
        address.setZipCode("44000");
        address.setCity("Nantes");

        // Association entre le PetStore et l'Adresse
        associationRepo.associatePetStoreWithAddress(petStore, address);


        // Création de trois Produits
        Product product1 = new Product();
        product1.setCode("F-001");
        product1.setLabel("Nourriture pour chats");
        product1.setType(ProdType.FOOD);
        product1.setPrice(7.99);

        Product product2 = new Product();
        product2.setCode("C-001");
        product2.setLabel("Litière pour chats");
        product2.setType(ProdType.CLEANING);
        product2.setPrice(5.99);

        Product product3 = new Product();
        product3.setCode("A-001");
        product3.setLabel("Jouet pour chats");
        product3.setType(ProdType.ACCESSORY);
        product3.setPrice(3.99);

        // Association entre le PetStore et les Produits
        associationRepo.associatePetStoreWithProduct(petStore, product1);
        associationRepo.associatePetStoreWithProduct(petStore, product2);
        associationRepo.associatePetStoreWithProduct(petStore, product3);


        // Création de trois Chat
        Cat cat1 = new Cat();
        String str1 = "2020-12-31";
        Date date1 = Date.valueOf(str1);
        cat1.setBirth(date1);
        cat1.setCouleur("blanc");
        cat1.setChipId("123456");

        Cat cat2 = new Cat();
        String str2 = "2021-10-17";
        Date date2 = Date.valueOf(str2);
        cat2.setBirth(date2);
        cat2.setCouleur("noir");
        cat2.setChipId("789012");

        Cat cat3 = new Cat();
        String str3 = "2022-09-19";
        Date date3 = Date.valueOf(str3);
        cat3.setBirth(date3);
        cat3.setCouleur("roux");
        cat3.setChipId("345678");


        // Création de trois Poisons
        Fish fish1 = new Fish();
        String str4 = "2021-10-17";
        Date date4 = Date.valueOf(str4);
        fish1.setBirth(date4);
        fish1.setCouleur("vert");
        fish1.setLivingEnv(FishLivEnv.FRESH_WATER);

        Fish fish2 = new Fish();
        String str5 = "2019-03-06";
        Date date5 = Date.valueOf(str5);
        fish2.setBirth(date5);
        fish2.setCouleur("orange");
        fish2.setLivingEnv(FishLivEnv.SEA_WATER);

        Fish fish3 = new Fish();
        String str6 = "2023-02-11";
        Date date6 = Date.valueOf(str6);
        fish3.setBirth(date6);
        fish3.setCouleur("jaune");
        fish3.setLivingEnv(FishLivEnv.FRESH_WATER);

        List<Product> productsList = new ArrayList<>();
        productsList.add(product1);
        productsList.add(product2);
        productsList.add(product3);

        associationRepo.associatePetStoreWithProduct(petStore, productsList);

        List<Animal> animalsList = new ArrayList<>();
        animalsList.add(cat1);
        animalsList.add(cat2);
        animalsList.add(cat3);
        animalsList.add(fish1);
        animalsList.add(fish2);
        animalsList.add(fish3);

        associationRepo.associatePetStoreWithAnimal(petStore, animalsList);
        // Persistance des entités

        persistanceRepo.persistPetStore(petStore);
        persistanceRepo.persistAddress(address);
        persistanceRepo.persistProduct(product1);
        persistanceRepo.persistProduct(product2);
        persistanceRepo.persistProduct(product3);
        persistanceRepo.persistAnimal(cat1);
        persistanceRepo.persistAnimal(cat2);
        persistanceRepo.persistAnimal(cat3);
        persistanceRepo.persistAnimal(fish1);
        persistanceRepo.persistAnimal(fish2);
        persistanceRepo.persistAnimal(fish3);


        transaction.commit();

        // Récupération des entités depuis la base de données
        // Récupération des animaux d'un PetStore donné
        Query query = entityManager.createQuery("SELECT a FROM Animal a WHERE a.petStore.name = :name");
        query.setParameter("name", "Animalerie_Doe");
        List<Animal> animals = query.getResultList();
        for (Animal animal : animals) {
            System.out.println(animal);
        }
//        // Fermer l'EntityManager
//        entityManager.close();
//
//        // Fermer l'EntityManagerFactory
//        entityManagerFactory.close();
    }
}
