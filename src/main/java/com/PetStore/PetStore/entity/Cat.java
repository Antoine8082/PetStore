package com.PetStore.PetStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "cat")
@DiscriminatorValue("CAT")
public class Cat extends Animal {

    @Column(name = "chip_id")
    private String chipId;
    public Cat() {
    }

    public String getChipId() {
        return chipId;
    }

    public void setChipId(String chipId) {
        this.chipId = chipId;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + getId() +
                ", birth='" + getBirth() + '\'' +
                ", color=" + getCouleur() +
                ", chipId='" + getChipId() + '\'' +
                '}';
    }
}
