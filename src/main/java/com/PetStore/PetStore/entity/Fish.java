package com.PetStore.PetStore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fish")
@DiscriminatorValue("FISH")
public class Fish extends Animal {

    @Enumerated(EnumType.STRING)
    @Column(name = "living_env")
    private FishLivEnv livingEnv;

    public Fish() {
    }

    public FishLivEnv getLivingEnv() {
        return livingEnv;
    }

    public void setLivingEnv(FishLivEnv livingEnv) {
        this.livingEnv = livingEnv;
    }
    @Override
    public String toString() {
        return "Fish{" +
                "id=" + getId() +
                ", naissance='" + getBirth() + '\'' +
                ", couleur=" + getCouleur() +
                ", environnement='" + getLivingEnv() + '\'' +
                '}';
    }
}