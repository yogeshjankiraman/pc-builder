package com.example.pcbuilder.model;

public class Part {
    private String id;
    private String category;
    private String name;
    private long priceInINR;

    public Part() {
    }

    public Part(String id, String category, String name, long priceInINR) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.priceInINR = priceInINR;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPriceInINR() {
        return priceInINR;
    }

    public void setPriceInINR(long priceInINR) {
        this.priceInINR = priceInINR;
    }
}
