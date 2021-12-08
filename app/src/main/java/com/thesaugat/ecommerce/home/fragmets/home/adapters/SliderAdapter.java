package com.thesaugat.ecommerce.home.fragmets.home.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.thesaugat.ecommerce.R;
import com.thesaugat.ecommerce.api.responses.Slider;

import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderViewHolder> {

    List<Slider> sliders;
    LayoutInflater layoutInflater;
    Context context;
    OnSliderClickLister sliderClickLister;
    boolean isFitted =true;


    public SliderAdapter(List<Slider> sliders, Context context, Boolean isFitted) {
        this.sliders = sliders;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.isFitted = isFitted;
    }


    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_image_slider, null);
        return new SliderViewHolder(view);
    }

    public void setSliderClickLister(OnSliderClickLister sliderClickLister) {
        this.sliderClickLister = sliderClickLister;
    }


    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {


//        viewHolder.textViewDescription.setText(sliders.get(position).getDesc());
//        viewHolder.textViewDescription.setTextSize(16);
//        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Picasso.get().load(sliders.get(position).getImage()).into(viewHolder.imageViewBackground);
        if(isFitted)
        {
            viewHolder.imageViewBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliderClickLister.onItemClick(position, sliders.get(position));
            }
        });
    }

    @Override
    public int getCount() {
        return sliders.size();
    }

    public class SliderViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
//        TextView textViewDescription;

        public SliderViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
//            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

    public interface OnSliderClickLister {
        public void onItemClick(int position, Slider slider);
    }

}
