package com.example.mymap;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.mymap.adapters.CommentAdapter;
import com.example.mymap.adapters.Restaurant;
import com.example.mymap.adapters.Comment;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mymap.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    double latitude = 0.0;
    double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        EditText searchText = findViewById(R.id.searchText);


            // 初始化Places
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyBKIUTYgfoQLK4kobf121iMfPQ90eRNr64");
        }

        // 创建PlacesClient实例
        PlacesClient placesClient = Places.createClient(this);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    performSearch(searchText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }


    // 搜索附近餐馆的方法
        private void performSearch(String query) {
            // 创建一个搜索请求
            FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                // 设置搜索类型为餐馆
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                // 设置用户输入的查询文本
                .setQuery(query)
                .build();
            PlacesClient placesClient = Places.createClient(this);
            placesClient.findAutocompletePredictions(predictionsRequest).addOnSuccessListener((response) -> {
                List<String> placeIds = new ArrayList<>();
                for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                    Log.i(TAG, prediction.getPrimaryText(null).toString());
                    placeIds.add(prediction.getPlaceId());
                }

                // 用获取到的地点 ID 查找更多地点信息
                fetchPlacesDetails(placeIds);
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                }
            });

        }

        private void fetchPlacesDetails(List<String> placeIds) {
            List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.PHOTO_METADATAS);
            PlacesClient placesClient = Places.createClient(this);
            List<Restaurant> restaurants = new ArrayList<>();

            AtomicInteger processedCount = new AtomicInteger();
            for (String placeId : placeIds) {
                FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    Place place = response.getPlace();
                    Log.i(TAG, "Place found: " + place);


                    // 从地点获取照片元数据（如果有）
                    String photoReference = null;
                    PhotoMetadata photoMetadata = null;
                    if (place.getPhotoMetadatas() != null && !place.getPhotoMetadatas().isEmpty()) {
                        photoMetadata = place.getPhotoMetadatas().get(0);
                        // 创建 Restaurant 时，构建图片 URL
                        String key = "photoReference=";
                        String data = String.valueOf(photoMetadata);
                        int startIndex = data.indexOf(key);

                        if (startIndex != -1) {
                            startIndex += key.length();
                            int endIndex = data.indexOf(',', startIndex);
                            if (endIndex == -1) {  // 如果没有找到逗号，尝试找到右大括号
                                endIndex = data.indexOf('}', startIndex);
                            }
                            endIndex = endIndex != -1 ? endIndex : data.length();

                            photoReference = data.substring(startIndex, endIndex);
                            photoReference = "https://places.googleapis.com/v1/places/"+place.getId()+"/photos/" + photoReference + "/media?maxHeightPx=400&maxWidthPx=400&key=AIzaSyBKIUTYgfoQLK4kobf121iMfPQ90eRNr64";
                            Log.i(TAG, String.valueOf(photoReference));
                        }

                        if (place.getLatLng() != null) {
                            latitude = place.getLatLng().latitude;
                            longitude = place.getLatLng().longitude;
                        }
                        restaurants.add(new Restaurant(
                                place.getName() + '\n' + place.getAddress(),
                                photoMetadata,
                                photoReference,
                                latitude,
                                longitude
                        ));
                    }

                    // 添加到餐馆列表
//                    restaurants.add(new Restaurant(place.getName(), photoMetadata, place));

                    // 检查是否已处理完所有地点
                    if (processedCount.incrementAndGet() == placeIds.size()) {
                        launchRestaurantsListActivity(restaurants);
                    }

                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                    }
                    // 在处理失败的情况下也要增加计数
                    if (processedCount.incrementAndGet() == placeIds.size()) {
                        launchRestaurantsListActivity(restaurants);
                    }
                });
            }
        }

        private void launchRestaurantsListActivity(List<Restaurant> restaurants) {
            Intent intent = new Intent(this, RestaurantsListActivity.class);
            intent.putExtra("restaurants_list", (Serializable) restaurants);
            startActivity(intent);
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng from_pos = new LatLng(43.08457439957523, -89.37471530087691);
        mMap.addMarker(new MarkerOptions().position(from_pos).title("Wisconsin Madison"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(from_pos));
    }

}