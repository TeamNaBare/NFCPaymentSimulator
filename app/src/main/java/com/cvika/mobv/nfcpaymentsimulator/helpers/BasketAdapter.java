package com.cvika.mobv.nfcpaymentsimulator.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvika.mobv.nfcpaymentsimulator.R;
import com.cvika.mobv.nfcpaymentsimulator.models.CartProduct;
import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;
import com.cvika.mobv.nfcpaymentsimulator.models.CartProduct;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private List<CartProduct> products;
    private Context context;
    private String uid;

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

    public BasketAdapter(List<CartProduct> products, Context context, String uid) {
        this.products = products;
        this.context = context;
        this.uid = uid;
    }

    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BasketAdapter.ViewHolder holder, int position) {

        final CartProduct product = products.get(position);

        // TextView's
        holder.productTitleView.setText(product.getTitle());
        holder.productDescriptionView.setText(product.getDescription());
        holder.productPriceView.setText(product.getPrice() + "");

        // ak pozname ID nfc karty, mozeme zaplatit => pridame button
        if(!this.uid.isEmpty()) {

            Button removeFromCartButton = new Button(context);
            removeFromCartButton.setText(context.getString(R.string.remove_from_cart));
            removeFromCartButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                v.setClickable(false);

                CartItem cartItem = new CartItem();
                cartItem.setTitle(product.getTitle());
                cartItem.setProductId(product.getProductId());
                cartItem.setUid(uid);

                Log.i("M_LOG", "Remove from cart " + product.getTitle() +" by user: " + uid);

                // TODO: odobrat z kosika
//                new AddToCartAsync(v, context).execute(cartItem);
//                new RemoveFromAutomatAsync(v, context).execute(product);
                }
            });

            holder.buttonWrapper.addView(removeFromCartButton);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
