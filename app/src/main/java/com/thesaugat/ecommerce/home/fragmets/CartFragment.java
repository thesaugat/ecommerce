package com.thesaugat.ecommerce.home.fragmets;

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

import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.ApiClient;
import com.thesaugat.ecommerce.api.responses.AllProductResponse;
import com.thesaugat.ecommerce.api.responses.Product;
import com.thesaugat.ecommerce.home.fragmets.home.adapters.ShopAdapter;
import com.thesaugat.ecommerce.singleProductScreen.SingleProductActivity;
import com.thesaugat.ecommerce.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    RecyclerView allProductsRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allProductsRV = view.findViewById(R.id.allProductsRV);
        getCartItems();
    }

    private void getCartItems() {
        String key = SharedPrefUtils.getString(getActivity(), getString(R.string.api_key));
        Call<AllProductResponse> myCartCall = ApiClient.getClient().getMyCartItems(key);
        myCartCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                if(response.isSuccessful())
                {
                    if(!response.body().getError()){

                        loadCartList(response.body().getProducts());

                    }                }
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {

            }
        });
    }

    private void loadCartList(List<Product> products) {
        allProductsRV.setHasFixedSize(true);
        allProductsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext(),true);
        allProductsRV.setAdapter(shopAdapter);
    }
}