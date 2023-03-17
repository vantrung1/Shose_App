package edu.fpt.shose_app.Model;

public class Cart {

    private String image;
    private String cartName;
    private int price;
    private int quantity;

    private  int size;

    public Cart() {
    }

    public Cart( String image, String cartName, int price, int quantity, int size) {
        this.image = image;
        this.cartName = cartName;
        this.price = price;
        this.quantity = quantity;
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
}
