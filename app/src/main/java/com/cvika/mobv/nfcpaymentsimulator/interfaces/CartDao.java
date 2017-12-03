package com.cvika.mobv.nfcpaymentsimulator.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cvika.mobv.nfcpaymentsimulator.models.AutomatProduct;
import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;
import com.cvika.mobv.nfcpaymentsimulator.models.CartProduct;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.List;

/**
 * Created by rybec on 12.11.2017.
 */

@Dao
public interface CartDao {

    @Query("SELECT * FROM cart")
    List<CartItem> getAll();

    @Query("SELECT * FROM cart WHERE uid = :userId AND paid = 0")
    List<CartItem> getAllNotPaidByUser(String userId);

    @Query("SELECT * FROM cart WHERE id IN (:cartItems)")
    List<CartItem> loadAllByIds(int[] cartItems);

    @Query("SELECT * FROM cart WHERE title LIKE :name LIMIT 1")
    CartItem findByName(String name);

    @Query("SELECT p.id as id, p.title, p.description, p.price, c.id as cartItemId, c.uid as user FROM cart c JOIN products p ON p.id = c.product_id WHERE c.uid = :userId AND c.paid = 0")
    List<CartProduct> getNotPaidCartProducts(String userId);

    @Query("UPDATE cart SET paid=1 WHERE id=:id")
    void setAsPaid(int id);

    @Query("SELECT * FROM cart where id=:id")
    List<CartItem> findById(int id);

    @Insert
    void insertAll(CartItem... cartItems);

    @Delete
    void delete(CartItem cartItem);

}
