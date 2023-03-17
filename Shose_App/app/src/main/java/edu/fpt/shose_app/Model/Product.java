package edu.fpt.shose_app.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String image;
    private int price;
    private String sale;
    private int brandID;
    private String desc;
    private String content;
    private String status;
    private String create_at;
    private String update_at;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public Product(int id, String name, String image, int price, String sale, int brandID, String desc, String content, String status, String create_at, String update_at) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.sale = sale;
        this.brandID = brandID;
        this.desc = desc;
        this.content = content;
        this.status = status;
        this.create_at = create_at;
        this.update_at = update_at;
    }
}
