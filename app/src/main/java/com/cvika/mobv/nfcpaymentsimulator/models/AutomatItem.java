package com.cvika.mobv.nfcpaymentsimulator.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by rybec on 13.11.2017.
 */

@Entity(tableName = "automat_items", foreignKeys = {
        @ForeignKey(
                entity = Product.class,
                parentColumns = "id",
                childColumns = "product_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
}, indices = {@Index(value = "product_id")})
public class AutomatItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "createdtime")
    private long createdTimestamp;

    @ColumnInfo(name = "sold")
    private int sold;

    // added by card ID
    @ColumnInfo(name = "uid")
    private String uid;

    @ColumnInfo(name = "product_id")
    private int productId;


    public AutomatItem(int id, String uid, int productId) {
        this.id = id;
        this.uid = uid;
        this.productId = productId;
        this.sold = 0;
        this.createdTimestamp = new Date().getTime();
    }

    @Ignore
    public AutomatItem() {
        this.createdTimestamp = new Date().getTime();
        this.sold = 0;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
