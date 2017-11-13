package com.cvika.mobv.nfcpaymentsimulator.helpers;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvika.mobv.nfcpaymentsimulator.R;
import com.cvika.mobv.nfcpaymentsimulator.db.AppDatabase;
import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.List;

/**
 * Created by rybec on 12.11.2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> products;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productTitleView;
        private TextView productDescriptionView;
        private TextView productPriceView;
        private LinearLayout buttonWrapper;


        public ViewHolder(View v) {
            super(v);

            productTitleView = (TextView)v.findViewById(R.id.productTitleView);
            productDescriptionView = (TextView)v.findViewById(R.id.productDescriptionView);
            productPriceView = (TextView)v.findViewById(R.id.productPriceView);
            buttonWrapper = (LinearLayout)v.findViewById(R.id.addToCartButtonWrapper);
        }
    }

    public ProductsAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
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

        final Product product = products.get(position);


        holder.productTitleView.setText(product.getTitle());
        holder.productDescriptionView.setText(product.getDescription());
        holder.productPriceView.setText(product.getPrice() + "");

        Button addToCartButton = new Button(context);
        addToCartButton.setText(context.getString(R.string.add_to_cart));
        addToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CartItem cartItem = new CartItem();
                cartItem.setTitle(product.getTitle());
                cartItem.setProductId(product.getId());

                new AsyncTask<CartItem, Void, Void>() {

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
                    }
                }.execute(cartItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
