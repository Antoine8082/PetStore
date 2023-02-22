package com.PetStore.PetStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cat")
@DiscriminatorValue("CAT")
public class Cat extends Animal {

    @Column(name = "chip_id")
    private String chipId;

    public String getChipId() {
        return chipId;
    }

    public void setChipId(String chipId) {
        this.chipId = chipId;
    }
//    private String furColor;
//
//    public Cat(String name, int age, String furColor) {
//        super(name, age);
//        this.furColor = furColor;
//    }
//
//    // ajout de la méthode toString()
//    @Override
//    public String toString() {
//        return "Cat{" +
//                "id=" + getId() +
//                ", name='" + getName() + '\'' +
//                ", age=" + getAge() +
//                ", furColor='" + furColor + '\'' +
//                '}';
//    }
//
//    public String getFurColor() {
//        return furColor;
//    }
//
//    public void setFurColor(String furColor) {
//        this.furColor = furColor;
//    }
}
