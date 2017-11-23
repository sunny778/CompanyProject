package com.example.sunny.companiesproject;

/**
 * Created by Sunny on 22/11/2017.
 */

public class Company {

    private long id;
    private String name;
    private String address;
    private String image;

    public Company(long id, String name, String address, String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
    }

    public Company(String name, String address, String image) {
        this.name = name;
        this.address = address;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
