package com.example.android.zoomTableau;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.javaBeans.Album;
import com.example.android.javaBeans.Picture;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Picture_Activity extends AppCompatActivity {
    Picture img;
    Album album;
    ImageView imgView;
    TextView albumName;
    Bitmap imgBitmap, enhancedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Intent intent = getIntent();

        img = (Picture) intent.getExtras().getSerializable("pictureData");
        album = Album_Activity.getAlbum();

        albumName = findViewById(R.id.album_name_bar);
        albumName.setText(album.getName());

        String imgUriStr = img.getImgUriStr();
        Uri imgUri = Uri.parse(imgUriStr);
        imgView =  findViewById(R.id.pictureView);

        try{
            imgBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
            imgView.setImageBitmap(imgBitmap);
        }catch(IOException e) {e.printStackTrace();}

        Picture_Activity.this.overridePendingTransition(0,0);
    }


    //Equalization of the histogram
    public void enhanceQuality(View v) {

        Mat rgba = new Mat();

        int width = imgBitmap.getWidth();
        int height = imgBitmap.getHeight();
        enhancedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        Utils.bitmapToMat(imgBitmap, rgba);

        List<Mat> channels = new ArrayList<Mat>();
        Core.split(rgba,channels);

        Imgproc.equalizeHist(channels.get(0), channels.get(0));
        Imgproc.equalizeHist(channels.get(1), channels.get(1));
        Imgproc.equalizeHist(channels.get(2), channels.get(2));

        Core.merge(channels, rgba);


        Utils.matToBitmap(rgba, enhancedBitmap);

        imgView.setImageBitmap(enhancedBitmap);

    }
}
