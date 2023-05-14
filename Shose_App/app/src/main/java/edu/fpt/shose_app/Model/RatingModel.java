package edu.fpt.shose_app.Model;

import java.io.Serializable;
import java.util.List;

public class RatingModel implements Serializable {
    List<rating> data;

    public RatingModel() {
    }

    public RatingModel(List<rating> data) {
        this.data = data;
    }

    public List<rating> getData() {
        return data;
    }

    public void setData(List<rating> data) {
        this.data = data;
    }

    public class rating implements Serializable {
        private String username;
        private String avatar;
        private int id;
        private String user_id;
        private String product_id;
        private String star;
        private String image;
        private String content;
        private String created_at;
        private String updated_at;

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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public rating(String username, String avatar, int id, String user_id, String product_id, String star, String image, String content, String created_at, String updated_at) {
            this.username = username;
            this.avatar = avatar;
            this.id = id;
            this.user_id = user_id;
            this.product_id = product_id;
            this.star = star;
            this.image = image;
            this.content = content;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }
    }
}

