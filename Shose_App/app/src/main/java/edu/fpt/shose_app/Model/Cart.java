package edu.fpt.shose_app.Model;

public class Cart {
    private int idCart;
    private String image;
    private String cartName;
    private int price;
    private int quantity;
    private String color;
    private int size;

    public Cart() {
    }

    public Cart(int idCart, String image, String cartName, int price, int quantity, String color, int size) {
        this.idCart = idCart;
        this.image = image;
        this.cartName = cartName;
        this.price = price;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
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

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }
}
