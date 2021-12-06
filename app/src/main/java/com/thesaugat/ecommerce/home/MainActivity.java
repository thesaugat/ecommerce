package com.thesaugat.ecommerce.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.home.fragmets.CartFragment;
import com.thesaugat.ecommerce.home.fragmets.CategoryFragment;
import com.thesaugat.ecommerce.home.fragmets.home.HomeFragment;
import com.thesaugat.ecommerce.home.fragmets.ProfileFragmnet;
import com.thesaugat.ecommerce.home.fragmets.WishListFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    CartFragment cartFragment;
    WishListFragment wishListFragment;
    ProfileFragmnet profileFragmnet;
    CategoryFragment categoryFragment;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.mainBottomNav);
        homeFragment = new HomeFragment();
        currentFragment = homeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.mainFrameContainer, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getTitle().equals(getString(R.string.home))) {
                    if (homeFragment == null)
                        homeFragment = new HomeFragment();
                    changeFragment(homeFragment);
                    return true;
                } else if (item.getTitle().equals(getString(R.string.categories))) {

                    if (categoryFragment == null)
                        categoryFragment = new CategoryFragment();
                    changeFragment(categoryFragment);
                    return true;

                }else if (item.getTitle().equals(getString(R.string.cart))) {

                    if (cartFragment == null)
                        cartFragment = new CartFragment();
                    changeFragment(cartFragment);
                    return true;

                } else if (item.getTitle().equals(getString(R.string.wishlist))) {
                    if (wishListFragment == null)
                        wishListFragment = new WishListFragment();
                    changeFragment(wishListFragment);
                    return true;
                } else if (item.getTitle().equals(getString(R.string.profile))) {
                    if (profileFragmnet == null)
                        profileFragmnet = new ProfileFragmnet();
                    changeFragment(profileFragmnet);
                    return true;
                }
                return false;

            }
        });


    }

    public  void  onSearchClicked(View v){
        Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
    }
    void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();

        if (fragment.isAdded()) {

            getSupportFragmentManager().beginTransaction().show(fragment).commit();

        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.mainFrameContainer, fragment).commit();
        }
        currentFragment = fragment;
    }
}