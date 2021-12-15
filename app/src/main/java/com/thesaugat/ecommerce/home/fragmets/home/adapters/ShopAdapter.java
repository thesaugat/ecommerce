package com.thesaugat.ecommerce.home.fragmets.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.responses.Product;
import com.thesaugat.ecommerce.singleProductScreen.SingleProductActivity;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    List<Product> productDataList;
    LayoutInflater layoutInflater;
    Context context;
    Boolean isCart;
    Boolean removeEnabled = true;
    OnRemoveClicked removeClicked;


    public ShopAdapter(List<Product> productDataList, Context context, Boolean isCart) {
        this.productDataList = productDataList;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.isCart = isCart;
    }

    public void setRemoveClicked(OnRemoveClicked removeClicked) {
        this.removeClicked = removeClicked;
    }

    public void setRemoveEnabled(Boolean removeEnabled) {
        this.removeEnabled = removeEnabled;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isCart)
            return new ShopViewHolder(layoutInflater.inflate(R.layout.item_cart, parent, false));

        else
            return new ShopViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        holder.nameTV.setText(productDataList.get(position).getName());
        if (productDataList.get(position).getDiscountPrice() == null || productDataList.get(position).getDiscountPrice() == 0) {
            holder.priceTv.setVisibility(View.GONE);
            holder.discountPrice.setText(productDataList.get(position).getPrice() + "");
        } else
            holder.discountPrice.setText(productDataList.get(position).getDiscountPrice() + "");
        holder.priceTv.setText(productDataList.get(position).getPrice() + "");

        Picasso.get().load(productDataList.get(position).getImages().get(0)).into(holder.productIV);
        holder.singleProductLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleProductActivity.class);
                intent.putExtra(SingleProductActivity.DATA_KEY, productDataList.get(holder.getAdapterPosition()));
                context.startActivity(intent);

            }
        });
        if (isCart)
            if(removeEnabled)
            holder.deleteCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    removeClicked.onRemove(holder.getAdapterPosition());
                }
            });
            else
                holder.deleteCart.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV;
        LinearLayout singleProductLL;
        TextView nameTV, priceTv, discountPrice, discountPercent;
        ImageView deleteCart;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            productIV = itemView.findViewById(R.id.productIV);
            nameTV = itemView.findViewById(R.id.productNameTV);
            singleProductLL = itemView.findViewById(R.id.singleProductLL);
            priceTv = itemView.findViewById(R.id.oldPriceTV);
            discountPrice = itemView.findViewById(R.id.discountPriceTV);
            if (isCart)
                deleteCart = itemView.findViewById(R.id.deleteCart);
        }
    }

  public   interface OnRemoveClicked {
        public void onRemove(int position);
    }
}
