package com.example.mynotes.model;

import com.android.volley.toolbox.StringRequest;

public class ImageUrl {
    String imageUrl;

    public ImageUrl(String url)
    {
        this.imageUrl  = url;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}