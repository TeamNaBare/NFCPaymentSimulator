package com.cvika.mobv.nfcpaymentsimulator.helpers;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.cvika.mobv.nfcpaymentsimulator.db.AppDatabase;
import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;

/**
 * Created by rybec on 13.11.2017.
 */

public class AddToCartAsync extends AsyncTask<CartItem, Void, Void> {

    private View view;
    private Context context;

    public AddToCartAsync(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    protected Void doInBackground(CartItem... cartItems) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, AppDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();

        db.cartDao().insertAll(cartItems);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        Log.i("M_LOG", "Product added to cart");
        if(!view.isClickable()){
            view.setClickable(true);
        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
