package com.thesaugat.ecommerce.singleProductScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.ApiClient;
import com.thesaugat.ecommerce.api.responses.Product;
import com.thesaugat.ecommerce.api.responses.RegisterResponse;
import com.thesaugat.ecommerce.api.responses.SingleProductResponse;
import com.thesaugat.ecommerce.api.responses.Slider;
import com.thesaugat.ecommerce.home.fragmets.home.adapters.SliderAdapter;
import com.thesaugat.ecommerce.utils.SharedPrefUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleProductActivity extends AppCompatActivity {
    public static String DATA_KEY = "ds";
    public static String SINGLE_DATA_KEY = "sds";
    Product product;
    ProgressBar addTOCartProgress;
    ImageView backIV, plusIV, minusIV;
    SliderView productImageSlider;
    LinearLayout addToCartLL;
    TextView name, price, desc, quantityTV;
    int quantity = 1;
    boolean isAdding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        productImageSlider = findViewById(R.id.productImageSlider);
        name = findViewById(R.id.productNameTV);
        price = findViewById(R.id.productPriceTV);
        plusIV = findViewById(R.id.plusClick);
        minusIV = findViewById(R.id.minusClick);
        desc = findViewById(R.id.descTV);
        addToCartLL = findViewById(R.id.addToCartLL);
        addTOCartProgress = findViewById(R.id.addTOCartProgress);
        quantityTV = findViewById(R.id.quantityTV);
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

    private void toggleAddToCart(Boolean toogle) {
        if(toogle)
            addTOCartProgress.setVisibility(View.VISIBLE);
        else
            addTOCartProgress.setVisibility(View.GONE);

    }

    private void setOnCLickListeners() {
        backIV.setOnClickListener(v -> {
            finish();
        });
        addToCartLL.setOnClickListener(v -> {
            if(!isAdding) {
                String key = SharedPrefUtils.getString(SingleProductActivity.this, getString(R.string.api_key));
                Call<RegisterResponse> addToCartResponse = ApiClient.getClient().addToCart(key, product.getId(), quantity);
                toggleAddToCart(true);
                addToCartResponse.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SingleProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        toggleAddToCart(false);
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        toggleAddToCart(false);

                    }
                });
            }
            else {
                Toast.makeText(SingleProductActivity.this, "Your request is already under way", Toast.LENGTH_SHORT).show();
            }

        });
        plusIV.setOnClickListener(v -> {
            if (quantity <= 9)
                quantity++;
            else
                Toast.makeText(SingleProductActivity.this, "You cannot buy more than 10 items", Toast.LENGTH_SHORT).show();
            setQuanity();
        });
        minusIV.setOnClickListener(v -> {
            if (quantity < 2)
                Toast.makeText(SingleProductActivity.this, "Quantity Should atleat be 1", Toast.LENGTH_SHORT).show();
            else
                quantity--;
            setQuanity();

        });
    }

    private void setQuanity() {
        quantityTV.setText(quantity + "");
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