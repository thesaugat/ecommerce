package com.thesaugat.ecommerce.home.fragmets;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.ApiClient;
import com.thesaugat.ecommerce.api.responses.AllProductResponse;
import com.thesaugat.ecommerce.api.responses.Product;
import com.thesaugat.ecommerce.api.responses.RegisterResponse;
import com.thesaugat.ecommerce.api.responses.SingleProductResponse;
import com.thesaugat.ecommerce.checkout.CheckOutActivity;
import com.thesaugat.ecommerce.home.fragmets.home.adapters.ShopAdapter;
import com.thesaugat.ecommerce.singleProductScreen.SingleProductActivity;
import com.thesaugat.ecommerce.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    RecyclerView allProductsRV;
    List<Product> products;
    AllProductResponse allProductResponse;
    TextView totalPriceTv, checkOutTV;

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
        totalPriceTv = view.findViewById(R.id.totalPriceTv);
        checkOutTV = view.findViewById(R.id.checkOutTV);
        getCartItems();
        checkOutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                intent.putExtra(CheckOutActivity.CHECK_OUT_PRODUCTS, allProductResponse);
                getActivity().startActivity(intent);
            }
        });
    }

    private void getCartItems() {
        String key = SharedPrefUtils.getString(getActivity(), getString(R.string.api_key));
        Call<AllProductResponse> myCartCall = ApiClient.getClient().getMyCartItems(key);
        myCartCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        products = response.body().getProducts();
                        allProductResponse = response.body();

                        loadCartList();

                    }
                }
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {

            }
        });
    }

    private void loadCartList() {
        allProductsRV.setHasFixedSize(true);
        allProductsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext(), true);
        shopAdapter.setRemoveClicked(new ShopAdapter.OnRemoveClicked() {
            @Override
            public void onRemove(int position) {
                String key = SharedPrefUtils.getString(getActivity(), getString(R.string.api_key));
                Call<RegisterResponse> removeCart = ApiClient.getClient().removeFromCart(key, products.get(position).getCart_id());
                removeCart.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            products.remove(products.get(position));
                            shopAdapter.notifyItemRemoved(position);
                            setTotalPrice();

                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });

            }
        });
        setTotalPrice();
        allProductsRV.setAdapter(shopAdapter);
    }

    private void setTotalPrice() {
        double totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getDiscountPrice() != 0 || products.get(i).getDiscountPrice() != null) {
                totalPrice = totalPrice + products.get(i).getDiscountPrice();

            } else

                totalPrice = totalPrice + products.get(i).getPrice();
        }
        totalPriceTv.setText("( Rs. " + totalPrice+" )");
    }
}