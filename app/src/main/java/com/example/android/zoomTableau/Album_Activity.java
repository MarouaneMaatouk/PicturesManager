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
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.dbManager.DatabaseHelper;
import com.example.android.javaBeans.Album;

import java.io.IOException;

public class Album_Activity extends AppCompatActivity {

    static Album album;

    TextView albumName;

    Uri imgUri;

    RecyclerView myrv;
    RecyclerViewPicturesAdapter mAdapter;

    DatabaseHelper mDatabaseHelper;

    public static Album getAlbum() {
        return album;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        mDatabaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if(album == null)
            album = (Album) intent.getExtras().getSerializable("albumData");

        albumName = findViewById(R.id.album_name_bar);
        albumName.setText(album.getName());


        myrv = findViewById(R.id.recyclerview_pictures_id);
        mAdapter = new RecyclerViewPicturesAdapter(this, album.getId());

        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(mAdapter);

        Album_Activity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        return;
    }


    public void removeAlbum(View v) {
        Log.d("ID", ""+ album.getId());
        mDatabaseHelper.rmAlbum(album.getId());
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, album.getName() + " a été suprimée", Toast.LENGTH_SHORT).show();
        this.startActivity(intent);
    }


    public void openGallery(View v) {
        //Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(myIntent,101);

        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String pickTitle = "Select or take a new Picture";
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra
                (
                        Intent.EXTRA_INITIAL_INTENTS,
                        new Intent[] { takePhotoIntent }
                );

        startActivityForResult(chooserIntent, 101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK &&data != null) {
            imgUri = data.getData();
            mAdapter.addPicture(imgUri, album.getId());
            mAdapter.notifyDataSetChanged();
        }
    }
}
