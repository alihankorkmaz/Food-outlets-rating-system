package com.foodrating.system.models;

import javax.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_outlet_id", nullable = false)
    private FoodOutlet foodOutlet;

    private int ratingValue;

    public Rating() {
    }

    public Rating(FoodOutlet foodOutlet, int ratingValue) {
        this.foodOutlet = foodOutlet;
        this.ratingValue = ratingValue;
    }

    public Long getId() {
        return id;
    }

    public FoodOutlet getFoodOutlet() {
        return foodOutlet;
    }

    public void setFoodOutlet(FoodOutlet foodOutlet) {
        this.foodOutlet = foodOutlet;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    @Override
    public String toString() {
        return "foodOutlet= " + foodOutlet.getName() +
                ", ratingValue= " + ratingValue;
    }
}
