package com.example.android.zoomTableau;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.dbManager.DatabaseHelper;
import com.example.android.javaBeans.Picture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPicturesAdapter extends RecyclerView.Adapter<RecyclerViewPicturesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Picture> picturesData;
    private DatabaseHelper mDatabaseHelper;


    public RecyclerViewPicturesAdapter(Context mContext , int album_id) {
        this.mContext = mContext;
        this.picturesData = new ArrayList<>();

        //Load the albums from the db
        this.mDatabaseHelper = new DatabaseHelper(mContext);
        this.picturesData.addAll(mDatabaseHelper.getPictureData(album_id));

    }


    public void addPicture(Uri uri, int album_id) {

        Picture newPicture = new Picture(uri,album_id );
        mDatabaseHelper.addNewPicture(newPicture);
        newPicture.setId(mDatabaseHelper.getLastPictureId());
        picturesData.add(newPicture);

        Log.i("adding picture", "picture path" + uri.toString());

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.layout_picture_block, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Uri imgUri = picturesData.get(i).getImgUri();

        if(imgUri == null)
            imgUri = Uri.parse(picturesData.get(i).getImgUriStr());

        Glide.with(mContext).load(imgUri).into(myViewHolder.picture);

        myViewHolder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                Intent intent = new Intent(mContext, Picture_Activity.class);
                intent.putExtra("pictureData", picturesData.get(i));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() { return picturesData.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;

        public MyViewHolder(View v) {
            super(v);
            picture = v.findViewById(R.id.picture_id);
        }

    }
}
