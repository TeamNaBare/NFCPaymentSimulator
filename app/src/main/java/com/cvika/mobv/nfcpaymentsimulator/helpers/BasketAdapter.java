package com.cvika.mobv.nfcpaymentsimulator.helpers;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
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
import com.cvika.mobv.nfcpaymentsimulator.fragments.BasketFragment;
import com.cvika.mobv.nfcpaymentsimulator.fragments.MerchandiseFragment;
import com.cvika.mobv.nfcpaymentsimulator.models.AutomatItem;
import com.cvika.mobv.nfcpaymentsimulator.models.CartProduct;
import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;
import com.cvika.mobv.nfcpaymentsimulator.models.CartProduct;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private List<CartProduct> products;
    private Context context;
    private String uid;
    private BasketFragment fragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productTitleView;
        private TextView productDescriptionView;
        private TextView productPriceView;
        private Button buttonWrapper;


        public ViewHolder(View v) {
            super(v);

            productTitleView = (TextView)v.findViewById(R.id.productTitleView);
            productDescriptionView = (TextView)v.findViewById(R.id.productDescriptionView);
            productPriceView = (TextView)v.findViewById(R.id.productPriceView);
            buttonWrapper = (Button)v.findViewById(R.id.delete_from_cart);
        }
    }

    public BasketAdapter(List<CartProduct> products, Context context, String uid, BasketFragment fragment) {
        this.products = products;
        this.context = context;
        this.uid = uid;
        this.fragment = fragment;
    }

    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basket_item, parent, false);

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
        holder.buttonWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new BasketDeleter(context).execute(product).get();
                    fragment.setCartItems();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private static class BasketDeleter extends AsyncTask<CartProduct,Void, Void>{
        private Context context;
        public BasketDeleter(Context context){
            this.context = context;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(CartProduct... cartProducts) {
            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, AppDatabase.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
            CartItem ci = db.cartDao().findById(cartProducts[0].cartItemId).get(0);
            db.cartDao().delete(ci);

            AutomatItem ai = new AutomatItem();
            ai.setProductId(ci.getProductId());
            ai.setUid(PreferenceManager.getDefaultSharedPreferences(context).getString(MerchandiseFragment.LOG_TAG,""));
            db.automatDao().insertAll(ai);
            return null;
        }
    }

}
