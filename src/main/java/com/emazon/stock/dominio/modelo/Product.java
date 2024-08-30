package com.emazon.stock.dominio.modelo;

import java.util.List;

public class Product extends ModelBase{
    private Integer amount;
    private Integer price;
    private Brand brand;
    private List<Category> categoryList;
    public Product(Long id, String name, String description, Integer amount, Integer price, Brand brand, List<Category> categoryList) {
        super(id, name, description);
        this.amount = amount;
        this.price = price;
        this.brand = brand;
        this.categoryList = categoryList;
    }
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}