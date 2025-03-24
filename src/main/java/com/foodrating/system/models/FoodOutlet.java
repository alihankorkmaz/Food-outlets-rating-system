package com.foodrating.system.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FoodOutlet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double averageRating = 0.0; // Ortalama değeri burada tutuyoruz

    @OneToMany(mappedBy = "foodOutlet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    public FoodOutlet() {
    }

    public FoodOutlet(String name, String type, String address) {
        this.name = name;
        this.type = type;
        this.address = address;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAverageRating() {
        return averageRating;
    }
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setFoodOutlet(this);
        updateAverageRating();
    }

    public void removeRating(Rating rating) {
        ratings.remove(rating);
        rating.setFoodOutlet(null);
        updateAverageRating();
    }

    public void updateDetails(String name, String type, String address) {
        if (!name.isBlank()) {
            this.name = name;
        }
        if (!type.isBlank()) {
            this.type = type;
        }
        if (!address.isBlank()) {
            this.address = address;
        }
    }

    // Ortalama hesaplama işlemini burada yapıyoruz
    private void updateAverageRating() {
        if (ratings.isEmpty()) {
            averageRating = 0.0;
        } else {
            averageRating = ratings.stream()
                    .mapToInt(Rating::getRatingValue)
                    .average()
                    .orElse(0);
        }
    }


    @Override
    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Type: " + type +
                ", Address: " + address +
                ", Average Rating: " + averageRating;
    }
}
