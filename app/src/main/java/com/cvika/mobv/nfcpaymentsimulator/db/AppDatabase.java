package com.cvika.mobv.nfcpaymentsimulator.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.cvika.mobv.nfcpaymentsimulator.interfaces.AutomatDao;
import com.cvika.mobv.nfcpaymentsimulator.interfaces.CartDao;
import com.cvika.mobv.nfcpaymentsimulator.interfaces.OrderDao;
import com.cvika.mobv.nfcpaymentsimulator.interfaces.ProductDao;
import com.cvika.mobv.nfcpaymentsimulator.models.AutomatItem;
import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;
import com.cvika.mobv.nfcpaymentsimulator.models.OrderItem;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

/**
 * Created by rybec on 12.11.2017.
 */

@Database(entities = {Product.class, CartItem.class, AutomatItem.class, OrderItem.class}, version = 8)
public abstract class AppDatabase extends RoomDatabase {

    public static String DB_NAME = "nfc_automat";

    public abstract ProductDao productDao();
    public abstract CartDao cartDao();
    public abstract AutomatDao automatDao();
    public abstract OrderDao orderDao();
}