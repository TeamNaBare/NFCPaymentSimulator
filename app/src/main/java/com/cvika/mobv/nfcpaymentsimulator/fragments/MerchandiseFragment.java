package com.cvika.mobv.nfcpaymentsimulator.fragments;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cvika.mobv.nfcpaymentsimulator.MainActivity;
import com.cvika.mobv.nfcpaymentsimulator.R;
import com.cvika.mobv.nfcpaymentsimulator.db.AppDatabase;
import com.cvika.mobv.nfcpaymentsimulator.helpers.ProductsAdapter;
import com.cvika.mobv.nfcpaymentsimulator.models.AutomatProduct;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchandiseFragment extends Fragment {

    public static final String LOG_TAG = "M_LOG";

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
        FloatingActionButton cartButton = (FloatingActionButton) view.findViewById(R.id.showBasket);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasketFragment basketFragment= new BasketFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, basketFragment,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);



        // vyber produktov z automatu a vylistovanie v recyclerView
        setProducts();
        return view;
    }

    public String loadCardId(){

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        // TODO: poslat z mainActivity
        String uid = sharedPref.getString(MerchandiseFragment.LOG_TAG, "USER123456");

        return uid;
    }

    public void setProducts(){
        new AsyncTask<Void, Void, List<AutomatProduct>>() {

            @Override
            protected List<AutomatProduct> doInBackground(Void... params) {

                AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, AppDatabase.DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();

                return db.automatDao().getAvailableProducts();
            }

            @Override
            protected void onPostExecute(List<AutomatProduct> products) {

                mAdapter = new ProductsAdapter(products, context, loadCardId(),MerchandiseFragment.this);
                mRecyclerView.setAdapter(mAdapter);
            }

        }.execute();

    }

}
