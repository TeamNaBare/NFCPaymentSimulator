package com.cvika.mobv.nfcpaymentsimulator.helpers;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvika.mobv.nfcpaymentsimulator.NavigationActivity;
import com.cvika.mobv.nfcpaymentsimulator.R;
import com.cvika.mobv.nfcpaymentsimulator.db.AppDatabase;
import com.cvika.mobv.nfcpaymentsimulator.models.AutomatProduct;
import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.List;

/**
 * Created by rybec on 12.11.2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<AutomatProduct> products;
    private Context context;
    private String uid;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productTitleView;
        private TextView productDescriptionView;
        private TextView productPriceView;
        private LinearLayout buttonWrapper;
        private Button addToCartButton;


        public ViewHolder(View v) {
            super(v);

            productTitleView = (TextView)v.findViewById(R.id.productTitleView);
            productDescriptionView = (TextView)v.findViewById(R.id.productDescriptionView);
            productPriceView = (TextView)v.findViewById(R.id.productPriceView);
            buttonWrapper = (LinearLayout)v.findViewById(R.id.addToCartButtonWrapper);
            addToCartButton = (Button) v.findViewById(R.id.addToCartButton);
        }
    }

    public ProductsAdapter(List<AutomatProduct> products, Context context, String uid) {
        this.products = products;
        this.context = context;
        this.uid = uid;
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

        final AutomatProduct product = products.get(position);

        // TextView's
        holder.productTitleView.setText(product.getTitle());
        holder.productDescriptionView.setText(product.getDescription());
        holder.productPriceView.setText(product.getPrice() + " €");

        // ak pozname ID nfc karty, mozeme nakupovat => pridame button
        //if(!this.uid.isEmpty()) {

            //Button addToCartButton = new Button(context);
            holder.addToCartButton.setText(context.getString(R.string.add_to_cart));
            holder.addToCartButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                v.setClickable(false);

                CartItem cartItem = new CartItem();
                cartItem.setTitle(product.getTitle());
                cartItem.setProductId(product.getProductId());
                cartItem.setUid(uid);

                Log.i("M_LOG", "Add to cart " + product.getTitle() +" by user: " + uid);

                // pridat do kosika
                new AddToCartAsync(v, context).execute(cartItem);
                new RemoveFromAutomatAsync(v, context).execute(product);
                }
            });

            //holder.buttonWrapper.addView(addToCartButton);
        //}
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
