package edu.fpt.shose_app.Model;

public class products {
    private int product_id;
    private String name;
    private String image;
    private int quantity;
    private int price;
    private int sale;
    private int status;
    private String attributes;

    public products(int product_id, String name, String image, int quantity, int price, int sale, int status, String attributes) {
        this.product_id = product_id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.sale = sale;
        this.status = status;
        this.attributes = attributes;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIamge() {
        return image;
    }

    public void setIamge(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
}