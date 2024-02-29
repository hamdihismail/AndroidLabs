package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class CatImages extends AsyncTask<String, Integer, String>{
//        private Bitmap currentImg = new Bitmap();
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}