package com.cvika.mobv.nfcpaymentsimulator.fragments;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cvika.mobv.nfcpaymentsimulator.R;
import com.cvika.mobv.nfcpaymentsimulator.db.AppDatabase;
import com.cvika.mobv.nfcpaymentsimulator.helpers.ProductsAdapter;
import com.cvika.mobv.nfcpaymentsimulator.models.AutomatItem;
import com.cvika.mobv.nfcpaymentsimulator.models.AutomatProduct;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdministrationFragment extends Fragment {

    Context context;

    public AdministrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_administration, container, false);

        context = this.getActivity().getApplicationContext();


        // vytvorime produkt
        new AsyncTask<Void, Void, List<Product>>() {

            @Override
            protected  List<Product> doInBackground(Void... params) {

                AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, AppDatabase.DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();

//                Product product = new Product();
//                product.setTitle("Snickers");
//                product.setDescription("Čokoládová tyčinka");
//                product.setPrice(0.59f);

                return db.productDao().getAll();
            }

            @Override
            protected void onPostExecute(final List<Product> products) {


                // pridame 3 produkty do automatu
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {

                        AppDatabase db = Room.databaseBuilder(context,
                                AppDatabase.class, AppDatabase.DB_NAME)
                                .fallbackToDestructiveMigration()
                                .build();

                        AutomatItem[] automatItems = new AutomatItem[products.size()];
                        int idx = 0;

                        for(Product p : products){
                            AutomatItem item = new AutomatItem();
                            item.setUid("CARD654321");
                            item.setProductId(p.getProductId());

                            automatItems[idx] = item;
                            idx++;
                        }


//                        items[1] = new AutomatItem();
//                        items[1].setUid("CARD654321");
//                        items[1].setProductId(product.getId());
//
//                        items[2] = new AutomatItem();
//                        items[2].setUid("CARD654321");
//                        items[2].setProductId(product.getId());


                        db.automatDao().insertAll(automatItems);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {

                        Log.i("M_LOG", "products added");

                    }

                }.execute();

            }
        }.execute();




        return view;
    }

}
