package com.example.android.zoomTableau;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.dbManager.DatabaseHelper;
import com.example.android.javaBeans.Album;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAlbumsAdapter extends RecyclerView.Adapter<RecyclerViewAlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumsData;

    private String popup_Text;
    private DatabaseHelper mDatabaseHelper;

    public RecyclerViewAlbumsAdapter(Context mContext) {
        this.mContext = mContext;

        this.albumsData = new ArrayList<>();

        this.mDatabaseHelper = new DatabaseHelper(mContext);

        albumsData.add(new Album(R.drawable.add, "add"));

        //Load the albums from the db
        mDatabaseHelper = new DatabaseHelper(mContext);
        albumsData.addAll(mDatabaseHelper.getAlbumData());

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_album_block, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.album_name.setText(albumsData.get(i).getName());
        myViewHolder.album_icon.setImageResource(R.drawable.folder);

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                Intent intent = new Intent(mContext, Album_Activity.class);

                if(i == 0) {
                    // Creating the popup input for taking new albums names
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Album name: ");

                    final EditText input = new EditText(mContext);

                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            popup_Text = input.getText().toString();
                            if(popup_Text.equals("") || popup_Text == null) return;

                            Album album = (new Album(popup_Text));
                            Log.d("Adding Album",popup_Text +" id: "+ Integer.toString(mDatabaseHelper.getLastAlbumId() + 1));
                            //Adding to the db
                            mDatabaseHelper.addNewAlbum(popup_Text);
                            //getAlbumId returns 0 if the table is cleaned, but last id is autoincrement > 0
                            //Solution: Set album id after insert in db
                            album.setId(mDatabaseHelper.getLastAlbumId());
                            albumsData.add(album);

                            Toast.makeText(mContext,popup_Text + " est crée !",Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
                else {
                    intent.putExtra("albumData", albumsData.get(i));
                    Album_Activity.album = albumsData.get(i);
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return albumsData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView album_name;
        ImageView album_icon;
        CardView cardView;
        public MyViewHolder(View v) {
            super(v);

            album_name = v.findViewById(R.id.folder_id);
            album_icon = v.findViewById(R.id.folder_img_id);
            cardView = v.findViewById(R.id.cardview_id);


        }

    }
}
