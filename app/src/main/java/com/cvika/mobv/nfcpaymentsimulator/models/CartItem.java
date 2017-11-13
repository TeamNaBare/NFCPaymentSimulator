package com.cvika.mobv.nfcpaymentsimulator.models;

import android.app.Activity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.SharedPreferences;

import com.cvika.mobv.nfcpaymentsimulator.R;

/**
 * Created by rybec on 13.11.2017.
 */

@Entity(tableName = "cart",
        foreignKeys = {
        @ForeignKey(
                entity = Product.class,
                parentColumns = "id",
                childColumns = "product_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )},
        indices = {
            @Index("product_id")
        })
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "product_id")
    private int productId;

    public CartItem() {
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
