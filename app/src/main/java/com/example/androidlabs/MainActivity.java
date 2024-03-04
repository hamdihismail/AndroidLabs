package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
//    private File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/AndroidDvlpr"); //Creates app specific folder
    private File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+"/CatImages");



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

//        InputStream is = getResources().openRawResource(+R.drawable.test);
//        File test = new File("/sdcard/Pictures/CatImages/1Y3dpssxcbHPEkfO.png");
//        if(test.exists()){
//            System.out.println("Test File Exists!!!");
//        }
//        bitmap = BitmapFactory.decodeFile(test.toString());
//        imageView = findViewById(R.id.imgView);
//
//        imageView.setImageBitmap(bitmap);
    }

    public class CatImages extends AsyncTask<String, Integer, String>{
        String newImgFile;

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            FileOutputStream out;
            while(true) {
                try {
                    URL url = new URL(params[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();


                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String finalJson = buffer.toString();

                    JSONObject parentObject = new JSONObject(finalJson);
                    String id = parentObject.get("_id").toString();
//                    Path oldFile = Paths.get(id + ".png");
                    File oldFile = new File("/sdcard/Pictures/CatImages/"+id+".png");

                    if (oldFile.exists()) {
                        System.out.println("The file exists!!");
                        newImgFile = oldFile.toString();
                    } else {
                        System.out.println("The file doesn't exist!!");
                        URL imgURL = new URL("https://cataas.com/cat?id=" + id);
                        bitmap = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());
                        File imageFile = new File(path, String.valueOf(id) + ".png"); // Imagename.png
                        newImgFile = imageFile.toString();
                        out = new FileOutputStream(imageFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                        out.flush();
                        out.close();

                        System.out.println("FIle Saved!!");
                        System.out.println("Path: " + path.toString());
                        MediaScannerConnection.scanFile(MainActivity.this, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                            public void onScanCompleted(String path, Uri uri) {
                                // Log.i("ExternalStorage", "Scanned " + path + ":");
                                //    Log.i("ExternalStorage", "-> uri=" + uri);
                            }
                        });
                        for (int i = 0; i < 100; i++) {
                            try {
                                publishProgress(i);
                                Thread.sleep(30);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    return id;

//                return buffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            bitmap = BitmapFactory.decodeFile(newImgFile);
            imageView.setImageBitmap(bitmap);
        }

        @Override
        protected void onPostExecute(String result){
            imageView = findViewById(R.id.imgView);
            super.onPostExecute(result);
            System.out.println(result);
        }
    }
}