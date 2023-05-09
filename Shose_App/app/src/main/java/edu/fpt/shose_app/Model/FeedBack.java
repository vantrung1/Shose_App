package edu.fpt.shose_app.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FeedBack implements Serializable {
    private String username;
    private String avatar;
    private int id;
    private int user_id;
    private int product_id;
    private int star;
    @SerializedName("image")
    private String image;
    private String content;

    public FeedBack(String username, String avatar, int id, int user_id, int product_id, int star, String image, String content) {
        this.username = username;
        this.avatar = avatar;
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.star = star;
        this.image = image;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
