package com.thesaugat.ecommerce.api;

import com.thesaugat.ecommerce.api.responses.AllProductResponse;
import com.thesaugat.ecommerce.api.responses.CategoryResponse;
import com.thesaugat.ecommerce.api.responses.LoginResponse;
import com.thesaugat.ecommerce.api.responses.RegisterResponse;
import com.thesaugat.ecommerce.api.responses.SingleProductResponse;
import com.thesaugat.ecommerce.api.responses.SliderResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/v1/register")
    Call<RegisterResponse> register(@Field("name") String names, @Field("email") String email, @Field("password") String password);


    @GET("/api/v1/get-all-products")
    Call<AllProductResponse> getAllProducts();


    @GET("/api/v1/get-categories")
    Call<CategoryResponse> getAllCategories();

    @GET("/api/v1/slider")
    Call<SliderResponse> getSliders();

    @GET("/api/v1/get-products-by-category")
    Call<AllProductResponse> getProductsByCategory(@Query("c_id") int c_id);

    @GET("/api/v1/get-all-products")
    Call<SingleProductResponse> getProductById(@Query("id") int c_id);
}
