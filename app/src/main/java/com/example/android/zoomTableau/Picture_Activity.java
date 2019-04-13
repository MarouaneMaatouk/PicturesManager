package com.example.android.zoomTableau;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.dbManager.DatabaseHelper;
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

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        mDatabaseHelper = new DatabaseHelper(this);

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

        Picture_Activity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Album_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        return;
    }

    public void addComment(View v) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.description_dialog, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        editText.setText(img.getDescription());

        Button save = (Button) dialogView.findViewById(R.id.saveButton);
        Button cancel = (Button) dialogView.findViewById(R.id.cancelButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newDescription = editText.getText().toString();
                img.setDescription(newDescription);
                mDatabaseHelper.updateComment(img.getId(),newDescription);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }


    public void removePicture(View v) {
        mDatabaseHelper.rmPicture(img.getId());
        Intent intent = new Intent(this, Album_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, "L'image a été suprimée", Toast.LENGTH_SHORT).show();
        this.startActivity(intent);
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
