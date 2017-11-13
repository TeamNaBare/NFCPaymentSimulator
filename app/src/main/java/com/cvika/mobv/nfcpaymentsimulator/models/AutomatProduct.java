package com.cvika.mobv.nfcpaymentsimulator.models;

/**
 * Created by rybec on 13.11.2017.
 */

public class AutomatProduct extends Product{


    public int automatItemId;
    public int isSold;

    public AutomatProduct(int productId, String title, String description, float price, int automatItemId, int isSold) {
        super(productId, title, description, price);
        this.automatItemId = automatItemId;
        this.isSold = isSold;
    }
}
