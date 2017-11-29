package com.cvika.mobv.nfcpaymentsimulator.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cvika.mobv.nfcpaymentsimulator.R;

import static com.cvika.mobv.nfcpaymentsimulator.fragments.MerchandiseFragment.LOG_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
    private String cardId;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        cardId = preferences.getString(LOG_TAG,"");
        Log.d("ISIC ID",cardId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

}
