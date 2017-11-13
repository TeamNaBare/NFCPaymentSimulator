package com.cvika.mobv.nfcpaymentsimulator.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.List;

/**
 * Created by rybec on 12.11.2017.
 */

@Dao
public interface CartDao {

    @Query("SELECT * FROM cart")
    List<CartItem> getAll();

    @Query("SELECT * FROM cart WHERE id IN (:cartItems)")
    List<CartItem> loadAllByIds(int[] cartItems);

    @Query("SELECT * FROM cart WHERE title LIKE :name LIMIT 1")
    CartItem findByName(String name);

    @Insert
    void insertAll(CartItem... cartItems);

    @Delete
    void delete(CartItem cartItem);

}
