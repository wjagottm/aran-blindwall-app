package com.blacetec.blindwalls;

import java.io.Serializable;

/**
 * Created by arant on 2/20/2018.
 */

public class BlindWall implements Serializable {
    private int id;
    private String title;
    private String address;
    private String photographer;
    private int addressNumber;
    private String description;
    private String imageUrl;
    private String material;

    public BlindWall(int id,String title, String address, String photographer, int addressNumber, String description, String imageUrl, String material) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.photographer = photographer;
        this.addressNumber = addressNumber;
        this.description = description;
        this.imageUrl = imageUrl;
        this.material = material;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public void setAddressNumber(int addressNumber) {
        this.addressNumber = addressNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAddress() {
        return address;
    }

    public String getPhotographer() {
        return photographer;
    }

    public int getAddressNumber() {
        return addressNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMaterial() {
        return material;
    }
}
