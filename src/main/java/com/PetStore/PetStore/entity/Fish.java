package com.PetStore.PetStore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fish")
@DiscriminatorValue("FISH")
public class Fish extends Animal {

    @Enumerated(EnumType.STRING)
    @Column(name = "living_env")
    private FishLivEnv livingEnv;

    public FishLivEnv getLivingEnv() {
        return livingEnv;
    }

    public void setLivingEnv(FishLivEnv livingEnv) {
        this.livingEnv = livingEnv;
    }
}