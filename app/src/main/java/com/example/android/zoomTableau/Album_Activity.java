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

import com.example.android.javaBeans.Album;
import com.example.android.javaBeans.Picture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Album_Activity extends AppCompatActivity {

    TextView albumName;
    List<Picture> pictures;

    ImageView imageView;

    Uri imgUri;
    Bitmap imgBitmap;

    RecyclerView myrv;
    RecyclerViewPicturesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent = getIntent();

        Album album = (Album) intent.getExtras().getSerializable("albumData");

        albumName = (TextView) findViewById(R.id.album_name_bar);
        albumName.setText(album.getName());

        pictures = new ArrayList<>();

        myrv = (RecyclerView) findViewById(R.id.recyclerview_pictures_id);
        mAdapter = new RecyclerViewPicturesAdapter(this, pictures);

        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(mAdapter);

        Album_Activity.this.overridePendingTransition(0,0);

    }
    public void removeAlbum(View v) {}


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
                mAdapter.addPicture(imgBitmap, imgUri);
            }catch(IOException e) {e.printStackTrace();}
        }
    }
}
