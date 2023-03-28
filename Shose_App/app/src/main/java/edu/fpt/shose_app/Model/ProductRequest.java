package edu.fpt.shose_app.Model;

import java.util.ArrayList;

public class ProductRequest {
    private String status;
    private String message;
    private ArrayList<Product> data;

    public ProductRequest(String status, String message, ArrayList<Product> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Product> getData() {
        return data;
    }

    public void setData(ArrayList<Product> data) {
        this.data = data;
    }
}
