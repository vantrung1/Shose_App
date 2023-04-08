package edu.fpt.shose_app.Model;

public class Cart {
    private int idProduct;
    private String image;
    private String cartName;
    private int price;
    private int quantity;
    private String color;
    private int size;
    private boolean ischeck;

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public Cart(int idProduct, String image, String cartName, int price, int quantity, String color, int size, boolean ischeck) {
        this.idProduct = idProduct;
        this.image = image;
        this.cartName = cartName;
        this.price = price;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.ischeck = ischeck;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
