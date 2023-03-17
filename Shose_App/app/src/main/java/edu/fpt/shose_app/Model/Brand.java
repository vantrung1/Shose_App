package edu.fpt.shose_app.Model;

import java.io.Serializable;

public class Brand implements Serializable {


    private int Id;
    private String brandName;
    private String desc;
    private String image;
    private String create_at;
    private String update_at;

    public Brand() {
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

    public Brand(int id, String brandName, String desc, String image, String create_at, String update_at) {
        Id = id;
        this.brandName = brandName;
        this.desc = desc;
        this.image = image;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
