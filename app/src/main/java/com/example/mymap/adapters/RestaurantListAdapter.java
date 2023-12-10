package com.example.mymap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymap.R;
import com.example.mymap.adapters.Restaurant;

import java.util.List;
import java.util.Locale;

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {

    public RestaurantListAdapter(Context context, List<Restaurant> restaurants) {
        super(context, 0, restaurants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Restaurant restaurant = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewRestaurantName);
        TextView textViewRating = convertView.findViewById(R.id.textViewRestaurantRating); // 新增
        ImageView imageViewRestaurant = convertView.findViewById(R.id.imageViewRestaurant);

        textViewName.setText(restaurant.getName());
        textViewRating.setText(String.format(Locale.getDefault(), "%.1f", restaurant.getRating()) + '\n' + restaurant.getStarRating()); // 显示评分
        if (restaurant.getPhotoReference() != null) {
            Glide.with(getContext()).load(restaurant.getPhotoReference()).into(imageViewRestaurant);
        }

        RecyclerView recyclerViewComments = convertView.findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        CommentAdapter commentAdapter = new CommentAdapter(restaurant.getComments());
        recyclerViewComments.setAdapter(commentAdapter);

        return convertView;
    }
}