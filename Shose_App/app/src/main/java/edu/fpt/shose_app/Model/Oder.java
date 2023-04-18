package edu.fpt.shose_app.Model;

import java.util.List;
import java.util.Map;

public class Oder {
    private int id;
    private int id_Oder;
    private int quantity;
    private int price;
    private List<Map<String, Products_Oder>> products_oder;
    private int status;
    private int user_id;
    private int id_address;
    private int number;
    private int total;
    private String note;
    private int paymentAmount;
    private String create_at;
    private String update_at;

    public Oder(int id, int id_Oder, int quantity, int price, List<Map<String, Products_Oder>> products_oder, int status, int user_id, int id_address, int number, int total, String note, int paymentAmount, String create_at, String update_at) {
        this.id = id;
        this.id_Oder = id_Oder;
        this.quantity = quantity;
        this.price = price;
        this.products_oder = products_oder;
        this.status = status;
        this.user_id = user_id;
        this.id_address = id_address;
        this.number = number;
        this.total = total;
        this.note = note;
        this.paymentAmount = paymentAmount;
        this.create_at = create_at;
        this.update_at = update_at;
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

    public List<Map<String, Products_Oder>> getProducts_oder() {
        return products_oder;
    }

    public void setProducts_oder(List<Map<String, Products_Oder>> products_oder) {
        this.products_oder = products_oder;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
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
}
