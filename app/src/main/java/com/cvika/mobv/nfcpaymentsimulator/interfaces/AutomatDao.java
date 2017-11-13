package com.cvika.mobv.nfcpaymentsimulator.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cvika.mobv.nfcpaymentsimulator.models.AutomatItem;
import com.cvika.mobv.nfcpaymentsimulator.models.AutomatProduct;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.List;

/**
 * Created by rybec on 12.11.2017.
 */

@Dao
public interface AutomatDao {

    @Query("SELECT * FROM automat_items")
    List<AutomatItem> getAll();

    @Query("SELECT * FROM automat_items WHERE id IN (:automatItemIds)")
    List<AutomatItem> loadAllByIds(int[] automatItemIds);

    @Insert
    void insertAll(AutomatItem... automatItems);

    @Query("SELECT ai.id as automatItemId, ai.sold as isSold, p.id, p.title, p.description, p.price FROM automat_items ai JOIN products p ON p.id = ai.product_id")
    List<AutomatProduct> getAvailableProducts();

    @Delete
    void delete(AutomatItem automatItem);

}
