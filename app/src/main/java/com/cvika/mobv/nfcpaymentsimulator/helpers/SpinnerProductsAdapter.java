package com.cvika.mobv.nfcpaymentsimulator.helpers;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.List;

/**
 * Created by jakub on 01.12.2017.
 */

public class SpinnerProductsAdapter extends ArrayAdapter<Product> {
    public SpinnerProductsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    private Context context;
    // Your custom products for the spinner (User)
    private List<Product> products;

    public SpinnerProductsAdapter(Context context, int textViewResourceId,
                       List<Product> products) {
        super(context, textViewResourceId, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount(){
        return products.size();
    }

    @Override
    public Product getItem(int position){
        return products.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(products.get(position).getTitle());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(products.get(position).getTitle());

        return label;
    }
}


