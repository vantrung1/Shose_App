package edu.fpt.shose_app.Model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("id")
    private String id;
    @SerializedName("image")
    private String image;

    public Image(String id, String image) {
        this.id = id;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
