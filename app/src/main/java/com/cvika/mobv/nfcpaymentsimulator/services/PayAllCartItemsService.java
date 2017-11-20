package com.cvika.mobv.nfcpaymentsimulator.services;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cvika.mobv.nfcpaymentsimulator.db.AppDatabase;
import com.cvika.mobv.nfcpaymentsimulator.helpers.AddOrderAsync;
import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;
import com.cvika.mobv.nfcpaymentsimulator.models.OrderItem;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class PayAllCartItemsService extends IntentService {

    public static final String PAY_ALL_CART_ITEMS = "com.cvika.mobv.nfcpaymentsimulator.services.action.PAY_ALL_CART_ITEMS";

    public static final String EXTRA_USER_ID = "com.cvika.mobv.nfcpaymentsimulator.services.extra.USER_ID";

    public PayAllCartItemsService() {
        super("PayAllCartItemsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (PAY_ALL_CART_ITEMS.equals(action)) {
                final String userId = intent.getStringExtra(EXTRA_USER_ID);
                handlePayAllCartItems(userId);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handlePayAllCartItems(String userId) {

//        Executor singleThreadExecutor = Executors.newSingleThreadExecutor();

        // database
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, AppDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();

        // vyberieme vsetky nezaplatene produkty v kosiku
        List<CartItem> cartItems = null;
        AsyncTask<String, Void, List<CartItem>> cartItemsTask = new AsyncTask<String, Void, List<CartItem>>() {

            @Override
            protected List<CartItem> doInBackground(String... params) {
                Log.i("M_LOG", "UserID: " + params[0]);
                return db.cartDao().getAllNotPaidByUser(params[0]);
            }

            @Override
            protected void onPostExecute(List<CartItem> cartItems) {
                Log.i("M_LOG", "Pocet poloziek: " + cartItems.size());
            }
        };


        final List<OrderItem> orderItems = new ArrayList<>();
        try {
            cartItems = (List<CartItem>) cartItemsTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, userId).get();

            // ak nejake polozky mame v kosiku ideme platit
            Log.i("M_LOG", "Pocet poloziek: " + cartItems.size());
            if(cartItems != null && !cartItems.isEmpty()){

                // prechadzame cely kosik
                for(final CartItem cartItem : cartItems){

                    // TODO: check & update penazi
                    // vytvori objednavku
                    new AsyncTask<Integer, Void, Product>() {
                        @Override
                        protected Product doInBackground(Integer... params) {
                            return db.productDao().getById(params[0]);
                        }

                        @Override
                        protected void onPostExecute(Product product) {
                            OrderItem orderItem = new OrderItem(
                                    cartItem.getUid(),
                                    product.getTitle(),
                                    product.getDescription(),
                                    product.getPrice(),
                                    new Date().getTime()
                            );

                            // add to order
                            orderItems.add(orderItem);
                            Log.i("M_LOG", "Vytvorena objednavka");
                        }
                    }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, cartItem.getProductId());

                    // nastavi polozku v kosiku ako zaplatenu
                    new AsyncTask<CartItem, Void, Void>() {
                        @Override
                        protected Void doInBackground(CartItem... params) {
                            db.cartDao().setAsPaid(params[0].getId());
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            Log.i("M_LOG", "Zaplatene");
                        }
                    }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, cartItem);

                }
            }else{
                Log.i("M_LOG", "Ziadne polozky v kosiku");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
