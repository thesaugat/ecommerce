package com.thesaugat.ecommerce.home.fragmets.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.ApiClient;
import com.thesaugat.ecommerce.api.responses.AllProductResponse;
import com.thesaugat.ecommerce.api.responses.Category;
import com.thesaugat.ecommerce.api.responses.CategoryResponse;
import com.thesaugat.ecommerce.api.responses.Product;
import com.thesaugat.ecommerce.api.responses.Slider;
import com.thesaugat.ecommerce.api.responses.SliderResponse;
import com.thesaugat.ecommerce.categoryPage.CategoryActivity;
import com.thesaugat.ecommerce.home.fragmets.home.adapters.CategoryAdapter;
import com.thesaugat.ecommerce.home.fragmets.home.adapters.ShopAdapter;
import com.thesaugat.ecommerce.home.fragmets.home.adapters.SliderAdapter;
import com.thesaugat.ecommerce.singleProductScreen.SingleProductActivity;
import com.thesaugat.ecommerce.utils.DataHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    RecyclerView allProductsRV, categoryRV;
    ProgressBar loadingProgress;
    SliderView imageSlider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allProductsRV = view.findViewById(R.id.allProductsRV);
        categoryRV = view.findViewById(R.id.categoryRV);
        imageSlider = view.findViewById(R.id.imageSlider);
        loadingProgress = view.findViewById(R.id.loadingProgress);
        getProductsOnline();
        getSliders();
        getCategoriesOnline();
    }

    private void getSliders() {
        Call<SliderResponse> sliderResponseCall = ApiClient.getClient().getSliders();
        sliderResponseCall.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        loadSliders(response.body().getSliders());
                    }
                }
            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {

            }
        });

    }

    private void loadSliders(List<Slider> sliders) {
        SliderAdapter adapter = new SliderAdapter(sliders, getContext(), true);
        adapter.setSliderClickLister(new SliderAdapter.OnSliderClickLister() {
            @Override
            public void onItemClick(int position, Slider slider) {

                if (slider.getType() == 1) {
                    Intent intent = new Intent(getContext(), SingleProductActivity.class);

                    intent.putExtra(SingleProductActivity.SINGLE_DATA_KEY, slider.getRelatedId());
                    getContext().startActivity(intent);
                } else if (slider.getType() == 2) {
                    Intent cat = new Intent(getContext(), CategoryActivity.class);
                    Category category = new Category();
                    category.setId(slider.getRelatedId());
                    category.setName(slider.getDesc());
                    cat.putExtra(CategoryActivity.CAT_KEY, category);
                    getContext().startActivity(cat);
                }

            }
        });

        imageSlider.setSliderAdapter(adapter);

        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorSelectedColor(Color.WHITE);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        imageSlider.startAutoCycle();

    }

    private void getCategoriesOnline() {
        Call<CategoryResponse> categoriesResponse = ApiClient.getClient().getAllCategories();
        categoriesResponse.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        DataHolder.categories = response.body().getCategories();
                        setCategoryRecyclerView(response.body().getCategories());

                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });

    }

    private void setCategoryRecyclerView(List<Category> categories) {
        List<Category> temp;
        if (categories.size() > 8) {
            temp = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                temp.add(categories.get(categories.size() - i - 1));
            }
        } else
            temp = categories;


        categoryRV.setHasFixedSize(true);
        categoryRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CategoryAdapter categoryAdapter = new CategoryAdapter(temp, getContext());
        categoryRV.setAdapter(categoryAdapter);


    }


    private void getProductsOnline() {
        toggleProgress(true);
        Call<AllProductResponse> allProductResponseCall = ApiClient.getClient().getAllProducts();
        allProductResponseCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                toggleProgress(false);
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                initiateRecyclerView(response.body().getProducts());


            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                toggleProgress(false);
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initiateRecyclerView(List<Product> products) {

        allProductsRV.setHasFixedSize(true);
        allProductsRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext());
        allProductsRV.setAdapter(shopAdapter);

    }

    public void toggleProgress(Boolean toogle) {
        if (toogle)
            loadingProgress.setVisibility(View.VISIBLE);
        else
            loadingProgress.setVisibility(View.GONE);
    }
}