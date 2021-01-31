package vn.com.misa.cukcukstarterclone.ui.main;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import vn.com.misa.cukcukstarterclone.R;
import vn.com.misa.cukcukstarterclone.data.model.Cart;
import vn.com.misa.cukcukstarterclone.ui.main.addcartitem.NewCartItemFragment;
import vn.com.misa.cukcukstarterclone.ui.main.addorder.AddOrderFragment;
import vn.com.misa.cukcukstarterclone.ui.main.addorder.dto.MenuItemDto;
import vn.com.misa.cukcukstarterclone.ui.main.checkout.CheckoutFragment;
import vn.com.misa.cukcukstarterclone.ui.main.listorders.ListOrdersFragment;
import vn.com.misa.cukcukstarterclone.ui.menu.MenuActivity;
import vn.com.misa.cukcukstarterclone.ui.order.OrderActivity;
import vn.com.misa.cukcukstarterclone.utils.Utils;

public class MainActivity extends AppCompatActivity
        implements ListOrdersFragment.IListOrdersFragmentCallback,
        AddOrderFragment.IAddOrderFragmentListener {

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        initData();
    }

    protected void bindViews() {
        try {
            DrawerLayout drawerLayout = findViewById(R.id.drawableLayout);
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(drawerToggle);

            NavigationView navView = findViewById(R.id.navView);
            navView.setItemIconTintList(null);
            navView.setNavigationItemSelectedListener(menuItem -> {
                navigateTo(menuItem.getTitle().toString());
                drawerLayout.closeDrawers();

                return true;
            });

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        } catch (Exception e) {
            Utils.handleException(e);
        }
    }

    private void navigateTo(String title) {
        if ("Danh sách hóa đơn".equals(title)) {
            startActivity(new Intent(this, OrderActivity.class));
        } else if ("Danh sách thực đơn".equals(title)) {
            startActivity(new Intent(this, MenuActivity.class));
        }
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void initData() {
        try {
            ListOrdersFragment orderListFragment = new ListOrdersFragment();
            orderListFragment.setCallback(this);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.detailsContainer, orderListFragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            Utils.handleException(e);
        }
    }

    @Override
    public void startNewCart() {
        AddOrderFragment addOrderFragment = AddOrderFragment.newInstance(null, false);
        addOrderFragment.setCallback(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.drawableLayout, addOrderFragment, AddOrderFragment.TAG).addToBackStack(AddOrderFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void showCartDetails(Cart cart) {
        AddOrderFragment addOrderFragment = AddOrderFragment.newInstance(cart, false);
        addOrderFragment.setCallback(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.drawableLayout, addOrderFragment, AddOrderFragment.TAG).addToBackStack(AddOrderFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void checkoutCart(Cart cart) {
        AddOrderFragment addOrderFragment = AddOrderFragment.newInstance(cart, true);
        addOrderFragment.setCallback(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.drawableLayout, addOrderFragment, AddOrderFragment.TAG).addToBackStack(AddOrderFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void addNewItemCart(MenuItemDto itemDto, String carId) {
        NewCartItemFragment newCartItemFragment = NewCartItemFragment.newInstance(itemDto, carId);
        newCartItemFragment.show(getSupportFragmentManager(), NewCartItemFragment.TAG);
    }

    @Override
    public void checkout(Cart cart) {
        CheckoutFragment checkoutFragment = CheckoutFragment.newInstance(cart);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.drawableLayout, checkoutFragment, CheckoutFragment.TAG).addToBackStack(CheckoutFragment.TAG);
        fragmentTransaction.commit();
    }
}
