package edu.fpt.shose_app.Model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Oder implements Serializable {
    private int id;
    private int id_Oder;
    private int quantity;
    private int price;
    private List< Products_Oder> products;
    private int status;
    private int user_id;
    private int id_address;
    private String number;
    private String total;
    private String note;
    private String paymentAmount;
    private String created_at;
    private String updated_at;

    public Oder(int id, int id_Oder, int quantity, int price, List<Products_Oder> products, int status, int user_id, int id_address, String number, String total, String note, String paymentAmount, String created_at, String updated_at) {
        this.id = id;
        this.id_Oder = id_Oder;
        this.quantity = quantity;
        this.price = price;
        this.products = products;
        this.status = status;
        this.user_id = user_id;
        this.id_address = id_address;
        this.number = number;
        this.total = total;
        this.note = note;
        this.paymentAmount = paymentAmount;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_Oder() {
        return id_Oder;
    }

    public void setId_Oder(int id_Oder) {
        this.id_Oder = id_Oder;
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

    public List<Products_Oder> getProducts() {
        return products;
    }

    public void setProducts(List<Products_Oder> products) {
        this.products = products;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId_address() {
        return id_address;
    }

    public void setId_address(int id_address) {
        this.id_address = id_address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
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
}
