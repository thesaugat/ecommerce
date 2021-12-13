package com.thesaugat.ecommerce.categoryPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.ApiClient;
import com.thesaugat.ecommerce.api.responses.AllProductResponse;
import com.thesaugat.ecommerce.api.responses.Category;
import com.thesaugat.ecommerce.api.responses.Product;
import com.thesaugat.ecommerce.home.fragmets.home.adapters.ShopAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    public static String CAT_KEY = "ctk";
    Category category;
    RecyclerView allProductsRV;
    ProgressBar loadingProgress;
    LinearLayout emptyIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        allProductsRV = findViewById(R.id.allProductsRV);
        loadingProgress = findViewById(R.id.loadingProgress);
        emptyIV = findViewById(R.id.emptyIV);
        if (getIntent().getSerializableExtra(CAT_KEY) == null)
            finish();
        category = (Category) getIntent().getSerializableExtra(CAT_KEY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(category.getName());
        getCategoryProducts();


    }

    private void toggleLoading(Boolean toogle) {
        if (toogle)
            loadingProgress.setVisibility(View.VISIBLE);
        else
            loadingProgress.setVisibility(View.GONE);
    }


    private void getCategoryProducts() {
        toggleLoading(true);
        Call<AllProductResponse> productResponseCall = ApiClient.getClient().getProductsByCategory(category.getId());
        productResponseCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                toggleLoading(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        if (response.body().getProducts().size() > 0)
                            loadProductsRV(response.body().getProducts());
                        else 
                            showEmptyMessage();

                    } else {
                        Toast.makeText(CategoryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                toggleLoading(false);
                Toast.makeText(CategoryActivity.this, "An Unknown Error Ocurred !!!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showEmptyMessage() {
        emptyIV.setVisibility(View.VISIBLE);
    }

    private void loadProductsRV(List<Product> products) {
        allProductsRV.setHasFixedSize(true);
        allProductsRV.setLayoutManager(new GridLayoutManager(this, 2));
        ShopAdapter shopAdapter = new ShopAdapter(products, this, false);
        allProductsRV.setAdapter(shopAdapter);
    }
}