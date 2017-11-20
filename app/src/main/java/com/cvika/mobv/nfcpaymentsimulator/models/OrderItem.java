package com.cvika.mobv.nfcpaymentsimulator.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by rybec on 16.11.2017.
 */

@Entity(tableName = "order_items")
public class OrderItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "uid")
    private String uid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private float price;

    @ColumnInfo(name = "createdtime")
    private long createdTimestamp;

    public OrderItem(int id, String uid, String title, String description, float price, long createdTimestamp) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.price = price;
        this.createdTimestamp = createdTimestamp;
    }

    @Ignore
    public OrderItem(String uid, String title, String description, float price, long createdTimestamp){
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.price = price;
        this.createdTimestamp = createdTimestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
}
