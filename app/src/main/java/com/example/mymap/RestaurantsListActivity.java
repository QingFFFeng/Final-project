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

//    restaurantList = new ArrayList<>();
        restaurantList = (List<Restaurant>) getIntent().getSerializableExtra("restaurants_list");
        if (restaurantList != null) {
            adapter = new RestaurantListAdapter(this, restaurantList);
            listView.setAdapter(adapter);

            // 修改为使用setOnItemClickListener()方法
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Restaurant selectedRestaurant = restaurantList.get(position);
                    double latitude = selectedRestaurant.getLatitude();
                    double longitude = selectedRestaurant.getLongitude();

                    // 启动导航应用程序
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + latitude + "," + longitude));
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    } else {
                        Log.e("RestaurantsListActivity", "Google Maps app not installed");
                    }
                }
            });
        } else {
            // 如果restaurantList为空，可能需要处理错误或显示空状态
            Log.e("RestaurantsListActivity", "No restaurant data received");
        }
    }
}