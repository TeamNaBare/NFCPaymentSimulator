package com.cvika.mobv.nfcpaymentsimulator.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;
import com.cvika.mobv.nfcpaymentsimulator.models.OrderItem;

import java.util.List;

/**
 * Created by rybec on 12.11.2017.
 */

@Dao
public interface OrderDao {

    @Query("SELECT * FROM order_items")
    List<OrderItem> getAll();

    @Query("SELECT * FROM order_items WHERE id IN (:orderItems)")
    List<OrderItem> loadAllByIds(int[] orderItems);

    @Query("SELECT * FROM order_items WHERE title LIKE :name LIMIT 1")
    OrderItem findByName(String name);

    @Insert
    void insertAll(OrderItem... orderItems);

    @Delete
    void delete(OrderItem orderItem);

}
