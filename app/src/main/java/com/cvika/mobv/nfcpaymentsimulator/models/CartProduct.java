package com.cvika.mobv.nfcpaymentsimulator.models;

/**
 * Created by rybec on 13.11.2017.
 */

public class CartProduct extends Product{

    public int cartItemId;
    public String user;

    public CartProduct(int productId, String title, String description, float price, int cartItemId, String user) {
        super(productId, title, description, price);
        this.cartItemId = cartItemId;
        this.user = user;
    }
}
