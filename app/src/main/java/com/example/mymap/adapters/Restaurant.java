package com.example.mymap.adapters;
import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.mymap.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.example.mymap.adapters.CommentGenerator;

import java.io.Serializable;


public class Restaurant implements Serializable {
    private String name;
    private transient PhotoMetadata photoMetadata; // transient 表明不进行序列化
    private transient Place place;
    private String photoReference; // 用于Intent传递和图片加载
    private float rating;
    private double latitude;  // Add latitude property
    private double longitude; // Add longitude property

    public Restaurant(String name, PhotoMetadata photoMetadata, String photoReference,double latitude, double longitude) {
        this.name = name;
        this.photoMetadata = photoMetadata;
        this.latitude = latitude;
        this.longitude = longitude;
//        this.place = place;
        if (photoMetadata != null) {
//            this.photoReference = photoMetadata.getAttributions(); // 或者使用其他唯一标识
//            this.photoReference = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoMetadata.getAttributions() + "&key=AIzaSyBKIUTYgfoQLK4kobf121iMfPQ90eRNr64";
//            Log.i(TAG, String.valueOf(this.photoMetadata));
           this.photoReference = "https://places.googleapis.com/v1/places/ChIJ2fzCmcW7j4AR2JzfXBBoh6E/photos/AUacShh3_Dd8yvV2JZMtNjjbbSbFhSv-0VmUN-uasQ2Oj00XB63irPTks0-A_1rMNfdTunoOVZfVOExRRBNrupUf8TY4Kw5iQNQgf2rwcaM8hXNQg7KDyvMR5B-HzoCE1mwy2ba9yxvmtiJrdV-xBgO8c5iJL65BCd0slyI1/media?maxHeightPx=400&maxWidthPx=400&key=AIzaSyBKIUTYgfoQLK4kobf121iMfPQ90eRNr64";

//            this.photoReference = photoReference;
        }

        // 生成1到5之间的随机评分，保留一位小数
        this.rating = 3/2 + new Random().nextFloat() * 4;
        this.rating = Math.round(this.rating * 10) / 10.0f;

    }

    // Getter 方法
    public String getName() {
        return name;
    }

    public PhotoMetadata getPhotoMetadata() {
        return photoMetadata;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public float getRating() { return rating;}

    public String getFormattedRating() {
        return rating + "\n" + getStarRating();
    }

    // Add these methods in your Restaurant class
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    // 可能还需要 setter 方法或其他逻辑

    public List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        // 添加一些测试数据
        CommentGenerator commentGenerator = new CommentGenerator();
        comments.add(new Comment("https://example.com/avatar1.jpg", commentGenerator.generateComment()));
        comments.add(new Comment("https://example.com/avatar2.jpg", commentGenerator.generateComment()));
        comments.add(new Comment("https://example.com/avatar3.jpg", commentGenerator.generateComment()));

        return comments;
    }

        // 新增的方法来获取星级表示
    public String getStarRating() {
        int starCount = (int) rating; // 将 rating 转换为整数
        starCount = Math.max(1, starCount); // 确保至少有一个星号
        starCount = Math.min(starCount, 5); // 确保最多五个星号

        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < starCount; i++) {
            stars.append("☆");
        }

        return stars.toString();
    }
}