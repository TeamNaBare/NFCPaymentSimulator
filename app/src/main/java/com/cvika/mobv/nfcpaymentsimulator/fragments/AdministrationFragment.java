package com.cvika.mobv.nfcpaymentsimulator.fragments;


import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cvika.mobv.nfcpaymentsimulator.R;
import com.cvika.mobv.nfcpaymentsimulator.db.AppDatabase;
import com.cvika.mobv.nfcpaymentsimulator.helpers.SpinnerProductsAdapter;
import com.cvika.mobv.nfcpaymentsimulator.models.AutomatItem;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdministrationFragment extends Fragment {

    Context context;
    List<Product> prodcuts;

    public AdministrationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.prodcuts = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_administration, container, false);
        view.getBackground().setAlpha(60);
        Button addOneProduct = (Button) view.findViewById(R.id.add_one_product);
        addOneProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct(v);
            }
        });

        Button deleteProduct = (Button) view.findViewById(R.id.delete_product);
        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(v);
            }
        });

        Button generateProducts = (Button) view.findViewById(R.id.generate_products);
        generateProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateProducts(v);
            }
        });

        Button addOneOfEach = (Button) view.findViewById(R.id.add_one_of_each);
        addOneOfEach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAllProductsToAutomatOneTime(v);
            }
        });
        context = this.getActivity().getApplicationContext();
        return view;
    }

    private void deleteProduct(View v) {
        showDeleteDialog();
    }

    private void showDeleteDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.delete_product_dialog);
        dialog.setTitle(R.string.add_prod);
        final Spinner productsSpinner = (Spinner) dialog.findViewById(R.id.products);
        final View view = getView().findViewById(R.id.add_one_product);
        view.setClickable(false);
        try {
            List<Product> products = new DatabaseFinder(getActivity().getApplicationContext(), view, false).execute().get();
            SpinnerProductsAdapter productsAdapter = new SpinnerProductsAdapter(getActivity(),android.R.layout.simple_spinner_item,products);
            productsSpinner.setAdapter(productsAdapter);
            // set the custom dialog components - text, image and button;
            Button deleteButton = (Button) dialog.findViewById(R.id.delete_btn);

            // if button is clicked, close the custom dialog
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product p = (Product) productsSpinner.getSelectedItem();

                    view.setClickable(false);
                    new DatabaseProductDeleter(getActivity().getApplicationContext(), view).execute(p);
                    dialog.dismiss();
                }
            });

            Button cancelButton = (Button) dialog.findViewById(R.id.cancel_btn);
            // if button is clicked, close the custom dialog
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            dialog.show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(View view) {
        showAddDialog();
    }

    private void showAddDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_product_dialog);
        dialog.setTitle(R.string.add_prod);

        // set the custom dialog components - text, image and button;
        Button addButton = (Button) dialog.findViewById(R.id.add_btn);
        // if button is clicked, close the custom dialog
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText) dialog.findViewById(R.id.prod_title);
                EditText desc = (EditText) dialog.findViewById(R.id.prod_desc);
                EditText price = (EditText) dialog.findViewById(R.id.prod_price);

                Product p = new Product();
                p.setTitle(title.getText().toString());
                p.setDescription(desc.getText().toString());
                p.setPrice(Float.parseFloat(price.getText().toString()));

                View view = getView().findViewById(R.id.add_one_product);
                view.setClickable(false);
                new DatabaseFiller(getActivity().getApplicationContext(), view).execute(p);
                dialog.dismiss();
            }
        });

        Button cancelButton = (Button) dialog.findViewById(R.id.cancel_btn);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void generateProducts(View view) {

        view.setClickable(false);

        List<Product> products = new ArrayList<>();

        Product snickers = new Product();
        snickers.setTitle("Snickers");
        snickers.setDescription("Čokoládová tyčinka");
        snickers.setPrice(0.59f);
        products.add(snickers);

        Product kofola = new Product();
        kofola.setTitle("Kofola");
        kofola.setDescription("0.5L");
        kofola.setPrice(0.79f);
        products.add(kofola);

        Product coffee = new Product();
        coffee.setTitle("Káva");
        coffee.setDescription("0.2L");
        coffee.setPrice(0.50f);
        products.add(coffee);

        for (Product p : products) {
            new DatabaseFiller(getActivity().getApplicationContext(), view).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, p);
        }
    }

    /* Prida z kazdeho produktu po jednom do tabulky automat */
    public void addAllProductsToAutomatOneTime(final View view) {

        view.setClickable(false);
        new DatabaseFinder(getActivity().getApplicationContext(), view, false).execute();
    }

    private void onProductsFound(List<Product> products, boolean toAddAutomatItems) {
        if (toAddAutomatItems) {
            AutomatItem[] automatItems = new AutomatItem[products.size()];

            int idx = 0;
            for (Product p : products) {
                AutomatItem item = new AutomatItem();
                item.setUid("CARD654321");
                item.setProductId(p.getProductId());

                automatItems[idx] = item;
                idx++;
            }

            new AutomatItemsFiller(getActivity().getApplicationContext(), getView().findViewById(R.id.add_one_of_each)).execute(automatItems);
        } else {
            this.prodcuts = products;
        }

    }

    private static class DatabaseFiller extends AsyncTask<Product, Void, Void> {

        private Context context;
        private View view;

        DatabaseFiller(Context ctx, View view) {
            this.context = ctx;
            this.view = view;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.view.setClickable(true);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Product... products) {
            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, AppDatabase.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

            db.productDao().insertAll(products);

            return null;
        }
    }

    private class DatabaseFinder extends AsyncTask<String, Void, List<Product>> {

        private final Context context;
        private final boolean toAddAutomatItems;

        DatabaseFinder(Context ctx, View view, boolean addAutomatItems) {
            this.context = ctx;
            this.toAddAutomatItems = addAutomatItems;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            onProductsFound(products, toAddAutomatItems);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Product> doInBackground(String... strings) {
            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, AppDatabase.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();


            return db.productDao().getAll();
        }
    }

    private class AutomatItemsFiller extends AsyncTask<AutomatItem, Void, Void> {

        private final Context context;
        private View view;

        AutomatItemsFiller(Context ctx, View view) {
            this.context = ctx;
            this.view = view;
        }

        @Override
        protected Void doInBackground(AutomatItem... automatItems) {
            AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
                    AppDatabase.class, AppDatabase.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

            db.automatDao().insertAll(automatItems);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getActivity().getApplicationContext(), "Produkty boli pridané do automatu", Toast.LENGTH_SHORT).show();
            view.setClickable(true);
        }
    }

    private class DatabaseProductDeleter extends AsyncTask<Product, Void, Void>{
        private View view;
        private Context context;
        public DatabaseProductDeleter(Context applicationContext, View view) {
            this.view = view;
            this.context = applicationContext;
        }

        @Override
        protected Void doInBackground(Product... products) {
            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, AppDatabase.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();


            db.automatDao().deleteByProductId(products[0].getProductId());
            db.productDao().delete(products[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.view.setClickable(true);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
