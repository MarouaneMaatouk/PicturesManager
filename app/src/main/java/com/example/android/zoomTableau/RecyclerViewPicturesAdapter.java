package com.example.android.zoomTableau;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.javaBeans.Picture;

import java.util.List;

public class RecyclerViewPicturesAdapter extends RecyclerView.Adapter<RecyclerViewPicturesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Picture> picturesData;



    public RecyclerViewPicturesAdapter(Context mContext, List<Picture> picturesData) {
        this.mContext = mContext;
        this.picturesData = picturesData;
    }


    public void addPicture(Bitmap imgBitmap, Uri uri) {
        picturesData.add(new Picture(imgBitmap,uri));
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
        Bitmap imgBitmap = picturesData.get(i).getImgBitmap();
        if(imgBitmap != null) {
            myViewHolder.picture.setImageBitmap(imgBitmap);
        }


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
            picture = (ImageView) v.findViewById(R.id.picture_id);
        }

    }
}
