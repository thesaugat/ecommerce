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
import android.widget.ProgressBar;

import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.responses.Category;
import com.thesaugat.ecommerce.home.fragmets.home.adapters.CategoryAdapter;
import com.thesaugat.ecommerce.utils.DataHolder;

import java.util.List;


public class CategoryFragment extends Fragment {
    RecyclerView fullCatRv;
    ProgressBar catProgress;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullCatRv = view.findViewById(R.id.fullCatRV);
        catProgress = view.findViewById(R.id.catProgress);
        if(DataHolder.categories != null){
            toggleLoading(false);
            loadRecyclerView(DataHolder.categories);
        }else
            toggleLoading(true);
    }

    private void toggleLoading(boolean b) {
        if(b)
            catProgress.setVisibility(View.VISIBLE);
        else
            catProgress.setVisibility(View.GONE);


    }

    private void loadRecyclerView(List<Category> categories) {
        fullCatRv.setHasFixedSize(true);
        fullCatRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, getContext());
        fullCatRv.setAdapter(categoryAdapter);
    }
}