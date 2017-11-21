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
import android.widget.Toast;

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

        return view;
    }


}
