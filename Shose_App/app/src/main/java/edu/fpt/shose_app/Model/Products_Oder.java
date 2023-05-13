package edu.fpt.shose_app.Model;

import java.io.Serializable;

public class Products_Oder implements Serializable {
    private String attributes;
    private String image;
    private String name;
    private int price;
    private int product_id;

    private int quantity;
    private int sale;
    private int status;


    public Products_Oder(String attributes, String image, String name, int price, int product_id, int quantity, int sale, int status) {
        this.attributes = attributes;
        this.image = image;
        this.name = name;
        this.price = price;
        this.product_id = product_id;

        this.quantity = quantity;
        this.sale = sale;
        this.status = status;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
