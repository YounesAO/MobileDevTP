package com.sny.ratingapp.beans;

public class Car {
    private int id;
    private String marque;
    private String model ;
    private String categorie;
    private String img;
    private Float stars;
    private static int comp;

    public Car(String marque, String model, String categorie, String img, Float stars) {
        this.id = ++comp;
        this.marque = marque;
        this.model = model;
        this.categorie = categorie;
        this.img = img;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Float getStars() {
        return stars;
    }

    public void setStars(Float stars) {
        this.stars = stars;
    }
}
