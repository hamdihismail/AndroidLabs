package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap bitmap;
    //Create Path to save Image
    private File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/AndroidDvlpr"); //Creates app specific folder


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!path.exists()) {
            path.mkdirs();
        }

//        HttpURLConnection connection = null;
//        BufferedReader reader = null;

        new CatImages().execute("https://cataas.com/cat?json=true");

//        try {
//            URL url = new URL("https://cataas.com/cat?json=true");
//            connection = (HttpURLConnection) url.openConnection();
//            connection.connect();
//
//            InputStream stream = connection.getInputStream();
//
//
//            reader = new BufferedReader(new InputStreamReader(stream));
//            StringBuffer buffer = new StringBuffer();
//
//            String line = "";
//            while((line = reader.readLine()) != null){
//                buffer.append(line);
//            }
//            System.out.println(buffer.toString());
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(connection != null){
//                connection.disconnect();
//            }
//            try {
//                if(reader != null){
//                    reader.close();
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }

        InputStream is = getResources().openRawResource(+R.drawable.test);
        bitmap = BitmapFactory.decodeStream(is);
        imageView = findViewById(R.id.imgView);

//        imageView.setImageBitmap(bitmap);
    }

    public class CatImages extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();


                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                String id = parentObject.get("_id").toString();
                Path oldFile = Paths.get(id+".png");

                if (Files.exists(oldFile)){
                    System.out.println("The file exists!!");
                }

                return id;

//                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                try {
                    if(reader != null){
                        reader.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            System.out.println(result);
        }
    }
}