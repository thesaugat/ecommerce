package com.thesaugat.ecommerce.checkout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.ApiClient;
import com.thesaugat.ecommerce.api.responses.Adress;
import com.thesaugat.ecommerce.api.responses.AllProductResponse;
import com.thesaugat.ecommerce.api.responses.Product;
import com.thesaugat.ecommerce.api.responses.RegisterResponse;
import com.thesaugat.ecommerce.checkout.address.AddressSelectionActivity;
import com.thesaugat.ecommerce.home.fragmets.home.adapters.ShopAdapter;
import com.thesaugat.ecommerce.utils.SharedPrefUtils;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {
    public static String CHECK_OUT_PRODUCTS = "sd";
    AllProductResponse allProductResponse;
    ImageView backIv;
    RecyclerView allProductsRV;
    LinearLayout addressLL;
    Adress address;
    TextView emptyAddressTv,  cityStreetTV , provinceTV;
    List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_check_out);
        backIv = findViewById(R.id.backIv);
        emptyAddressTv = findViewById(R.id.emptyAddressTv);
        addressLL = findViewById(R.id.addressLL);
        cityStreetTV = findViewById(R.id.cityStreetTV);
        provinceTV = findViewById(R.id.provinceTV);
        allProductsRV = findViewById(R.id.allProductsRV);
        setClickListners();
        allProductResponse = (AllProductResponse) getIntent().getSerializableExtra(CHECK_OUT_PRODUCTS);
        products = allProductResponse.getProducts();
        loadCartList();

    }

    private void setClickListners() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addressLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutActivity.this, AddressSelectionActivity.class);
                startActivity(intent);
            }
        });
        emptyAddressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutActivity.this, AddressSelectionActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }

    private void loadCartList() {
        allProductsRV.setHasFixedSize(true);
        allProductsRV.setLayoutManager(new LinearLayoutManager(this));
        ShopAdapter shopAdapter = new ShopAdapter(products, this, true);
        shopAdapter.setRemoveEnabled(false);
        allProductsRV.setAdapter(shopAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.getSerializableExtra(AddressSelectionActivity.ADDRESS_SELECTED_KEY) != null) {
                showSelectedAddress((Adress) data.getSerializableExtra(AddressSelectionActivity.ADDRESS_SELECTED_KEY));

            }
        }
    }

    private void showSelectedAddress(Adress adress) {
        address = adress;
        emptyAddressTv.setVisibility(View.GONE);
        cityStreetTV.setText(adress.getCity() + " "+ adress.getStreet());
        provinceTV.setText(adress.getProvince() );
        addressLL.setVisibility(View.VISIBLE);

    }
}