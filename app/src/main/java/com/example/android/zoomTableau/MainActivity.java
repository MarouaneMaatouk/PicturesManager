package com.example.android.zoomTableau;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.android.dao.DatabaseHelper;
import com.example.android.javaBeans.Album;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    List<Album> albums;

    ImageView imageView;

    Uri imgUri;
    Bitmap imgBitmap,enhancedBitmap;

    RecyclerView myrv;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albums = new ArrayList<>();
        albums.add(new Album(R.drawable.add, "add"));

        myrv = (RecyclerView) findViewById(R.id.recyclerview_albums_id);
        RecyclerViewAlbumsAdapter mAdapter = new RecyclerViewAlbumsAdapter(this, albums);

        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(mAdapter);

        mDatabaseHelper = new DatabaseHelper(this);

        //imageView = (ImageView) findViewById(R.id.imageView);
        OpenCVLoader.initDebug();
        MainActivity.this.overridePendingTransition(0,0);
    }



    public void openGallery(View v) {
        Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(myIntent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK &&data != null) {
            imgUri = data.getData();
            try{
                imgBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
            }catch(IOException e) {e.printStackTrace();}
            imageView.setImageBitmap(imgBitmap);
        }

    }

    public void enhanceQuality(View v) {
        Mat rgba = new Mat();

        int width = imgBitmap.getWidth();
        int height = imgBitmap.getHeight();
        enhancedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);


        Utils.bitmapToMat(imgBitmap, rgba);

        List <Mat> channels = new ArrayList<Mat>();
        Core.split(rgba,channels);

        Imgproc.equalizeHist(channels.get(0), channels.get(0));
        Imgproc.equalizeHist(channels.get(1), channels.get(1));
        Imgproc.equalizeHist(channels.get(2), channels.get(2));

        Core.merge(channels, rgba);

        /* Image sharpness: cons -> slow
        Imgproc.GaussianBlur(rgba, grayMat, new Size(0, 0), 10);
        Core.addWeighted(rgba, 1.5, grayMat, -0.5, 0, grayMat);
        */

        //Imgproc.adaptiveThreshold(rgba, grayMat,255.0, Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY ,15,40);

        Utils.matToBitmap(rgba, enhancedBitmap);

        imageView.setImageBitmap(enhancedBitmap);

    }

}
