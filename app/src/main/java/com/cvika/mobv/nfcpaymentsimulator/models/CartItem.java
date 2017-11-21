package com.cvika.mobv.nfcpaymentsimulator.models;

import android.app.Activity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.SharedPreferences;

import com.cvika.mobv.nfcpaymentsimulator.R;

import java.util.Date;

/**
 * Created by rybec on 13.11.2017.
 */

@Entity(tableName = "cart")
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "uid")
    private String uid;

    @ForeignKey(
            entity = Product.class,
            parentColumns = "id",
            childColumns = "product_id",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
    )
    @ColumnInfo(name = "product_id")
    private int productId;

    @ColumnInfo(name = "paid")
    private boolean paid;

    @ColumnInfo(name = "createdtime")
    private long createdTimestamp;

    @ColumnInfo(name = "paid_timestamp")
    private long paidTimestamp;

    public CartItem(String title, String uid, int productId) {
        this.title = title;
        this.uid = uid;
        this.productId = productId;
        this.paid = false;
        this.createdTimestamp = new Date().getTime();
        this.paidTimestamp = 0;
    }

    @Ignore
    public CartItem() {
        this.paid = false;
        this.createdTimestamp = new Date().getTime();
        this.paidTimestamp = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public long getPaidTimestamp() {
        return paidTimestamp;
    }

    public void setPaidTimestamp(long paidTimestamp) {
        this.paidTimestamp = paidTimestamp;
    }
}
