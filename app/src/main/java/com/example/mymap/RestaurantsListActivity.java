package com.example.mymap;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymap.adapters.Restaurant;
import com.example.mymap.adapters.RestaurantListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsListActivity extends AppCompatActivity {
    private ListView listView;
    private RestaurantListAdapter adapter;
    private List<Restaurant> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);

        listView = findViewById(R.id.listViewRestaurants);
//        restaurantList = new ArrayList<>();
         restaurantList = (List<Restaurant>) getIntent().getSerializableExtra("restaurants_list");
            if (restaurantList != null) {
                adapter = new RestaurantListAdapter(this, restaurantList);
                listView.setAdapter(adapter);
            } else {
                // 如果restaurantList为空，可能需要处理错误或显示空状态
                Log.e("RestaurantsListActivity", "No restaurant data received");
            }
            }

}