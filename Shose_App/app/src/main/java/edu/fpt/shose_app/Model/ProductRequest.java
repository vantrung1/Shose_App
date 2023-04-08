package edu.fpt.shose_app.Model;

import java.util.ArrayList;

public class ProductRequest {

    private ArrayList<Product> products;

    public ProductRequest(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
