package edu.fpt.shose_app.Model;

import java.io.Serializable;

public class Brand implements Serializable {
    private boolean ischeck = false;

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    private int Id;
    private String brandName;
    private String desc;
    private String image;

    public Brand() {
    }

    public Brand(int id, String brandName, String desc, String image) {
        Id = id;
        this.brandName = brandName;
        this.desc = desc;
        this.image = image;
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
