package com.thesaugat.ecommerce.home.fragmets.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.ApiClient;
import com.thesaugat.ecommerce.api.responses.AllProductResponse;
import com.thesaugat.ecommerce.api.responses.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    RecyclerView allProductsRV;
    ProgressBar loadingProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allProductsRV = view.findViewById(R.id.allProductsRV);
        loadingProgress = view.findViewById(R.id.loadingProgress);
        getProductsOnline();
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
        allProductsRV.setLayoutManager(new GridLayoutManager(getActivity(),2));
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