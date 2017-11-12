package com.cvika.mobv.nfcpaymentsimulator.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.cvika.mobv.nfcpaymentsimulator.interfaces.ProductDao;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

/**
 * Created by rybec on 12.11.2017.
 */

@Database(entities = {Product.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public static String DB_NAME = "nfc_automat";

    public abstract ProductDao productDao();
}