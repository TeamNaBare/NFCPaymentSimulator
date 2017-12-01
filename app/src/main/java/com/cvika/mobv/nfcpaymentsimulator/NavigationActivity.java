package com.cvika.mobv.nfcpaymentsimulator;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cvika.mobv.nfcpaymentsimulator.db.AppDatabase;
import com.cvika.mobv.nfcpaymentsimulator.fragments.AdministrationFragment;
import com.cvika.mobv.nfcpaymentsimulator.fragments.BasketFragment;
import com.cvika.mobv.nfcpaymentsimulator.fragments.InfoFragment;
import com.cvika.mobv.nfcpaymentsimulator.fragments.MerchandiseFragment;
import com.cvika.mobv.nfcpaymentsimulator.helpers.AddOrderAsync;
import com.cvika.mobv.nfcpaymentsimulator.helpers.ProductsAdapter;
import com.cvika.mobv.nfcpaymentsimulator.models.AutomatItem;
import com.cvika.mobv.nfcpaymentsimulator.models.CartItem;
import com.cvika.mobv.nfcpaymentsimulator.models.CartProduct;
import com.cvika.mobv.nfcpaymentsimulator.models.OrderItem;
import com.cvika.mobv.nfcpaymentsimulator.models.Product;
import com.cvika.mobv.nfcpaymentsimulator.services.PayAllCartItemsService;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import static com.cvika.mobv.nfcpaymentsimulator.fragments.MerchandiseFragment.LOG_TAG;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String cardId;
    private static final String ADMIN_CARD = "048E610ADA2580";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        cardId = preferences.getString(LOG_TAG,"");

        if(!cardId.equalsIgnoreCase(ADMIN_CARD)) {
            navigationView.getMenu().findItem(R.id.nav_administration).setVisible(false);
        }
        //TO DO: Toto potom vymazat
        Log.d("ISIC ID",cardId);
        Toast.makeText(this,cardId,Toast.LENGTH_LONG).show();

        if (findViewById(R.id.flContent) != null) {
            if (savedInstanceState != null) {
                return;
            }

            InfoFragment infoFragment = new InfoFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.flContent, infoFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Class fragmentClass = null;

        switch(item.getItemId()) {
            case R.id.nav_info:
                //TODO: vytvorenie fragmentu s informaciami o karte
                fragmentClass = InfoFragment.class;
                break;
            case R.id.nav_merchandise:
                //TODO: vytvorenie masterdetail fragmentu so zoznamom tovaru
                fragmentClass = MerchandiseFragment.class;
                break;
            case R.id.nav_basket:
                //TODO: vytvorenie fragmentu s kosikom v ktorom bude zobrazeny zvoleny tovar a jeho nakup
                fragmentClass = BasketFragment.class;
                break;
            case R.id.nav_administration:
                //TODO: vytvorenie masterdetail fragmentu so zoznamom tovaru
                fragmentClass = AdministrationFragment.class;
                break;
            case R.id.nav_logout:
                //TODO: vymazanie informacii o karte z SharedPreferences a ukoncenie aktivity
                break;
            default:
                fragmentClass = InfoFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void payAllCartItems(View view){

        // 1. get all from cart by user and paid = 0
        // 2. insert into order table
        // 3. delete from cart

        // uid from sharedPreferences

        final Context context = this;

        String uid = "USER123456";
        Intent intent = new Intent(this, PayAllCartItemsService.class);
        intent.setAction(PayAllCartItemsService.PAY_ALL_CART_ITEMS);
        intent.putExtra(PayAllCartItemsService.EXTRA_USER_ID, uid);
        startService(intent);
    }


    /* Vygeneruje 3 produkty do tabulky product */


}
