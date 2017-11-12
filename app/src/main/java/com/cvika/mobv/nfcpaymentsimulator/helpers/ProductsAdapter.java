package com.cvika.mobv.nfcpaymentsimulator.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvika.mobv.nfcpaymentsimulator.R;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.List;

/**
 * Created by rybec on 12.11.2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> products;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productTitleView;
        private TextView productDescriptionView;
        private TextView productPriceView;


        public ViewHolder(View v) {
            super(v);

            productTitleView = (TextView)v.findViewById(R.id.productTitleView);
            productDescriptionView = (TextView)v.findViewById(R.id.productDescriptionView);
            productPriceView = (TextView)v.findViewById(R.id.productPriceView);
        }
    }

    public ProductsAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.ViewHolder holder, int position) {

        Product product = products.get(position);

        holder.productTitleView.setText(product.getTitle());
        holder.productDescriptionView.setText(product.getDescription());
        holder.productPriceView.setText(product.getPrice() + "");

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
