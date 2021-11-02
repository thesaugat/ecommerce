package com.thesaugat.ecommerce.home.fragmets.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.responses.Product;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    List<Product> productDataList;
    LayoutInflater layoutInflater;


    ShopAdapter(List<Product> productDataList, Context context) {
        this.productDataList = productDataList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        holder.nameTV.setText(productDataList.get(position).getName());
        if(productDataList.get(position).getDiscountPrice() == null || productDataList.get(position).getDiscountPrice()== 0){
            holder.priceTv.setVisibility(View.GONE);
            holder.discountPrice.setText(productDataList.get(position).getPrice()+"");
        }
        else
            holder.discountPrice.setText(productDataList.get(position).getDiscountPrice()+"");
        holder.priceTv.setText(productDataList.get(position).getPrice()+"");

        Picasso.get().load(productDataList.get(position).getImages().get(0)).into(holder.productIV);

    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV;
        TextView nameTV, priceTv, discountPrice, discountPercent;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            productIV = itemView.findViewById(R.id.productIV);
            nameTV = itemView.findViewById(R.id.productNameTV);
            priceTv = itemView.findViewById(R.id.oldPriceTV);
            discountPrice = itemView.findViewById(R.id.discountPriceTV);
        }
    }
}
