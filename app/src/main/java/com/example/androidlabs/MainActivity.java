package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputStream is = getResources().openRawResource(+R.drawable.test);
        bitmap = BitmapFactory.decodeStream(is);
        imageView = findViewById(R.id.imgView);
        imageView.setImageBitmap(bitmap);
    }

    public class CatImages extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}