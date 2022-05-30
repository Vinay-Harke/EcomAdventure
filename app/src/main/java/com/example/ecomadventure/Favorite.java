package com.example.ecomadventure;

public class Favorite {
   String image;
   String price;
   String ratings;
   String title;
   String information;
   String name;

    public Favorite() {
    }

    public Favorite(String image, String price, String ratings, String title, String information, String name) {
        this.image = image;
        this.price = price;
        this.ratings = ratings;
        this.title = title;
        this.information = information;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "image=" + image +
                ", price=" + price +
                ", ratings=" + ratings +
                ", title='" + title + '\'' +
                ", information='" + information + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public void setInformation(String information) {
        this.information = information;
    }
}


