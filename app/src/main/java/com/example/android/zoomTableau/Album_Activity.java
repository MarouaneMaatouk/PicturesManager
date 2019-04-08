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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.dao.DatabaseHelper;
import com.example.android.javaBeans.Album;
import com.example.android.javaBeans.Picture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Album_Activity extends AppCompatActivity {

    static Album album;

    TextView albumName;

    Uri imgUri;
    Bitmap imgBitmap;

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
    public void removeAlbum(View v) {
        mDatabaseHelper.rmAlbum(album.getId());
        Intent intent = new Intent(this, MainActivity.class);
        Toast.makeText(this, album.getName() + " a été suprimée", Toast.LENGTH_SHORT).show();
        this.startActivity(intent);
    }


    public void openGallery(View v) {
        Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(myIntent,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK &&data != null) {
            imgUri = data.getData();
            try{
                imgBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                mAdapter.addPicture(imgBitmap, imgUri, album.getId());
            }catch(IOException e) {e.printStackTrace();}
        }
    }
}
