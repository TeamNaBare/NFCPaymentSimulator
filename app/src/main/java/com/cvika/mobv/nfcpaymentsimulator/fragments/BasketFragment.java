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
import android.widget.Button;

import com.cvika.mobv.nfcpaymentsimulator.MainActivity;
import com.cvika.mobv.nfcpaymentsimulator.R;
import com.cvika.mobv.nfcpaymentsimulator.db.AppDatabase;
import com.cvika.mobv.nfcpaymentsimulator.helpers.BasketAdapter;
import com.cvika.mobv.nfcpaymentsimulator.models.CartProduct;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends Fragment {

    public static final String LOG_TAG = "M_LOG";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton payBasketBtn;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        view.getBackground().setAlpha(30);
        context = this.getActivity().getApplicationContext();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.productsRecyclerView);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Floating button ktorym sa bude platit
        FloatingActionButton payBasketBtn = (FloatingActionButton) view.findViewById(R.id.payBasket);

        // vyber nezaplatenych produktov z kosika a vylistovanie v recyclerView
        setCartItems();

        return view;
    }

    public void setCartItems(){
        new AsyncTask<Void, Void, List<CartProduct>>() {

            @Override
            protected List<CartProduct> doInBackground(Void... params) {

                AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, AppDatabase.DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();

                return db.cartDao().getNotPaidCartProducts(loadCardId());
            }

            @Override
            protected void onPostExecute(List<CartProduct> products) {

                mAdapter = new BasketAdapter(products, context, loadCardId(),BasketFragment.this);
                mRecyclerView.setAdapter(mAdapter);
            }

        }.execute();
    }

    public String loadCardId(){

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        // TODO: poslat z mainActivity
        String uid = sharedPref.getString(MerchandiseFragment.LOG_TAG, "USER123456");

        return uid;
    }
}
