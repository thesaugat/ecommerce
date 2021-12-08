package com.thesaugat.ecommerce.singleProductScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.ApiClient;
import com.thesaugat.ecommerce.api.responses.Product;
import com.thesaugat.ecommerce.api.responses.SingleProductResponse;
import com.thesaugat.ecommerce.api.responses.Slider;
import com.thesaugat.ecommerce.home.fragmets.home.adapters.SliderAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleProductActivity extends AppCompatActivity {
    public static String DATA_KEY = "ds";
    public static String SINGLE_DATA_KEY = "sds";
    Product product;
    ImageView backIV;
    SliderView productImageSlider;
    TextView name, price, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        productImageSlider = findViewById(R.id.productImageSlider);
        name = findViewById(R.id.productNameTV);
        price = findViewById(R.id.productPriceTV);
        desc = findViewById(R.id.descTV);
        backIV = findViewById(R.id.backIV);
        setOnCLickListeners();

        if (getIntent().getSerializableExtra(DATA_KEY) != null) {
            product = (Product) getIntent().getSerializableExtra(DATA_KEY);
            showProduct(product);
        } else if (getIntent().getSerializableExtra(SINGLE_DATA_KEY) != null)
            getProductOnline(getIntent().getIntExtra(SINGLE_DATA_KEY, 1));
    }

    private void getProductOnline(int intExtra) {
        Call<SingleProductResponse> productResponseCall = ApiClient.getClient().getProductById(intExtra);
        productResponseCall.enqueue(new Callback<SingleProductResponse>() {
            @Override
            public void onResponse(Call<SingleProductResponse> call, Response<SingleProductResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        product = response.body().getProduct();
                        showProduct(product);
                    }

                }
            }

            @Override
            public void onFailure(Call<SingleProductResponse> call, Throwable t) {

            }
        });

    }

    private void setOnCLickListeners() {
        backIV.setOnClickListener(v -> {
            finish();
        });
    }

    private void showProduct(Product product) {

        setImageSlider(product.getImages());
        desc.setText(product.getDescription());
        price.setText("RS. " + product.getPrice());
        name.setText(product.getName());


    }

    private void setImageSlider(List<String> images) {
        List<Slider> sliders = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            Slider slider = new Slider();
            slider.setImage(images.get(i));
            sliders.add(slider);

        }
        SliderAdapter adapter = new SliderAdapter(sliders, this, false);
        adapter.setSliderClickLister(new SliderAdapter.OnSliderClickLister() {
            @Override
            public void onItemClick(int position, Slider slider) {

            }
        });
        productImageSlider.setSliderAdapter(adapter);
        productImageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        productImageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        productImageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        productImageSlider.setIndicatorSelectedColor(Color.WHITE);
        productImageSlider.setIndicatorUnselectedColor(Color.GRAY);

    }

}