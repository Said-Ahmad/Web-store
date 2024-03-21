package com.example.webstore.models;

public class Product {
    public int id;
    public int idOfPersonProduct;
    public String  name;
    public Integer price;
    public String  description;
    public String  icon_base64;
    public Integer count;

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOfPersonProduct() {
        return idOfPersonProduct;
    }

    public void setIdOfPersonProduct(int idOfPersonProduct) {
        this.idOfPersonProduct = idOfPersonProduct;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getIcon_base64() {
        return icon_base64;
    }

    public void setIcon_base64(String icon_base64) {
        this.icon_base64 = icon_base64;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", idOfPersonProduct=" + idOfPersonProduct +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", count=" + count +
                '}';
    }
}
