package com.gogxi.githubfavoriteuser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";

    public static int REQUEST_USER = 109;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



    }
}
