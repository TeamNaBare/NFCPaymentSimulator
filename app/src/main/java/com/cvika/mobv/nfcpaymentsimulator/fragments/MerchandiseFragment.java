package com.cvika.mobv.nfcpaymentsimulator.fragments;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cvika.mobv.nfcpaymentsimulator.R;
import com.cvika.mobv.nfcpaymentsimulator.db.AppDatabase;
import com.cvika.mobv.nfcpaymentsimulator.helpers.ProductsAdapter;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchandiseFragment extends Fragment {

    String LOG_TAG = "M_LOG";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchandise, container, false);




        context = this.getActivity().getApplicationContext();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.productsRecyclerView);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        new AsyncTask<Void, Void, List<Product>>() {

            @Override
            protected List<Product> doInBackground(Void... params) {

                AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, AppDatabase.DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();

                return db.productDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Product> products) {

                mAdapter = new ProductsAdapter(products);
                mRecyclerView.setAdapter(mAdapter);

            }

        }.execute();

        // INSERT
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//
//                AppDatabase db = Room.databaseBuilder(context,
//                        AppDatabase.class, AppDatabase.DB_NAME)
//                        .fallbackToDestructiveMigration()
//                        .build();
//
//                Product product = new Product();
//                product.setTitle("Snickers");
//                product.setDescription("Čokoládová tyčinka");
//                product.setPrice(0.59f);
//
//                db.productDao().insertAll(product);
//                return null;
//            }
//
//        }.execute();

        return view;
    }

}
