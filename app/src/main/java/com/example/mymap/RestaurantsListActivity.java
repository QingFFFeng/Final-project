package com.example.mymap;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymap.adapters.Restaurant;
import com.example.mymap.adapters.RestaurantListAdapter;

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

        // 检查 Google Maps 是否已安装
        PackageManager packageManager = getPackageManager();
        boolean isInstalled = false;
        try {
            isInstalled = packageManager.getPackageInfo("com.google.android.apps.maps", 0) != null;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!isInstalled) {
            // 提示用户安装 Google Maps
            Toast.makeText(this, "Google Maps 未安装", Toast.LENGTH_SHORT).show();
        }

        restaurantList = (List<Restaurant>) getIntent().getSerializableExtra("restaurants_list");
        if (restaurantList != null) {
            adapter = new RestaurantListAdapter(this, restaurantList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // 获取选中的餐厅
                    Log.d("RestaurantsListActivity", "onItemClick triggered");
                    Restaurant selectedRestaurant = restaurantList.get(position);

                    // 获取餐厅地址
                    String selectedRestaurantAddress = selectedRestaurant.getName(); // 请确保你的 Restaurant 类有一个获取地址的方法
                    Log.d("RestaurantsListActivity", "Selected Restaurant Name: " + selectedRestaurantAddress);
                    // 设置结果和结束活动
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("selected_restaurant_address", selectedRestaurantAddress);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }

            });
        }
    }
}