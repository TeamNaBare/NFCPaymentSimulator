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
public interface ProductDao {

    @Query("SELECT * FROM products")
    List<Product> getAll();


    @Query("SELECT * FROM products WHERE id=:id")
    Product getById(int id);

    @Query("SELECT * FROM products WHERE id IN (:productIds)")
    List<Product> loadAllByIds(int[] productIds);

    @Query("SELECT * FROM products WHERE title LIKE :name LIMIT 1")
    Product findByName(String name);

//    @Query("SELECT p.id, p.title, p.description, p.price FROM products p")
    @Query("SELECT ai.id as automatItemId, ai.sold as isSold, p.id, p.title, p.description, p.price FROM automat_items ai LEFT JOIN products p ON p.id = ai.product_id")
    List<AutomatProduct> getAvailableProducts();

    @Insert
    void insertAll(Product... products);

    @Delete
    void delete(Product product);


}
