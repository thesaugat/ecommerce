package com.thesaugat.ecommerce.home.fragmets.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.responses.Category;
import com.thesaugat.ecommerce.categoryPage.CategoryActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<Category> categories;
    LayoutInflater layoutInflater;
    Context context;

    public CategoryAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CategoryViewHolder(layoutInflater.inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.catName.setText(categories.get(position).getName());
        Picasso.get().load(categories.get(position).getCategoryImage()).into(holder.catIv);
        holder.categoryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra(CategoryActivity.CAT_KEY,categories.get(holder.getAdapterPosition()) );
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView catIv;
        TextView catName;
        LinearLayout categoryLL;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catIv = itemView.findViewById(R.id.catIV);
            catName = itemView.findViewById(R.id.catNameTV);
            categoryLL = itemView.findViewById(R.id.categoryLL);

        }
    }
}
