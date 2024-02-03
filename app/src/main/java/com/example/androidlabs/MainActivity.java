package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        Button btnPressMe = (Button)findViewById(R.id.btnPressMe);
        TextView textView = (TextView)findViewById(R.id.textview);
        EditText edtText = (EditText)findViewById(R.id.edtText);
        String toast_message = (String)getResources().getString(R.string.toast_message);
        String snackBar_message = (String)getResources().getString(R.string.snackBar_message);
        String on = (String)getResources().getString(R.string.on);
        String off = (String)getResources().getString(R.string.off);
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);

        btnPressMe.setOnClickListener( (click) -> {
            textView.setText(edtText.getText());
            Toast.makeText(MainActivity.this,toast_message,Toast.LENGTH_LONG).show();
        });

        checkBox.setOnCheckedChangeListener((cb, isChecked) -> {
            String onOff;
            if(isChecked){
                onOff = " " + on;
            } else {
                onOff = " " + off;
            }
            Snackbar.make(checkBox, snackBar_message + onOff, Snackbar.LENGTH_LONG)
                .setAction( "Undo", click -> cb.setChecked(!isChecked))
                .show();
        });
    }
}