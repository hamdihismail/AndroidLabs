package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btnNext = (Button)findViewById(R.id.btnNext);
    EditText edtText = (EditText)findViewById(R.id.edtText);
    SharedPreferences prefs = getSharedPreferences("shared", Context.MODE_PRIVATE);
    SharedPreferences.Editor edit = prefs.edit();
    @Override
    protected void onPause() {
        super.onPause();
        edit.putString("edtText", String.valueOf(edtText.getText()));
        edit.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent nextPage = new Intent(this, NameActivity.class);

        if(prefs.contains("edtText")){
            edtText.setText(prefs.getInt("edtText",0));
        }




        btnNext.setOnClickListener((click) -> {
            nextPage.putExtra("typed", edtText.getText());
            startActivityForResult(nextPage,1);
        });
    }
}