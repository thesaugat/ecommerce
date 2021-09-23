package com.thesaugat.ecommerce.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.home.fragmets.CartFragment;
import com.thesaugat.ecommerce.home.fragmets.HomeFragment;
import com.thesaugat.ecommerce.home.fragmets.ProfileFragmnet;
import com.thesaugat.ecommerce.home.fragmets.WishListFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    CartFragment cartFragment;
    WishListFragment wishListFragment;
    ProfileFragmnet profileFragmnet;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                } else if (item.getTitle().equals(getString(R.string.cart))) {

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