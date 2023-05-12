package edu.fpt.shose_app.Model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String address_id;
    private String role_id;
    private String phoneNumber;
    private String email;
    private String password;
    private String avatar;
    private String token;
    private String status;
    private String created_at;
    private String updated_at;
    private String filebase_id;

    public User() {
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

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getFilebase_id() {
        return filebase_id;
    }

    public void setFilebase_id(String filebase_id) {
        this.filebase_id = filebase_id;
    }

    public User(int id, String name, String address_id, String role_id, String phoneNumber, String email, String password, String avatar, String token, String status, String created_at, String updated_at, String filebase_id) {
        this.id = id;
        this.name = name;
        this.address_id = address_id;
        this.role_id = role_id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.token = token;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.filebase_id = filebase_id;
    }
}
