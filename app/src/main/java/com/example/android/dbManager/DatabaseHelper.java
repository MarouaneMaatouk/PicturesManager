package com.example.android.dbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.javaBeans.Album;
import com.example.android.javaBeans.Picture;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String ALBUM_TABLE = "album_table";
    private static final String AL_COL0 = "ID";
    private static final String AL_COL1 = "name";

    private static final String PICTURE_TABLE = "picture_table";
    private static final String PIC_COL0 = "ID_PIC";
    private static final String PIC_COL1 = "description";
    private static final String PIC_COL2 = "album_id";
    private static final String PIC_COL3 = "imgUri";
    private static final String PIC_COL4 = "mediaUri";



    public DatabaseHelper(Context context) {
        super(context, "DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createAlbumTable = "CREATE TABLE " + ALBUM_TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AL_COL1 + " TEXT);";
        String createPictureTable = "CREATE TABLE " + PICTURE_TABLE +" (" +
                "ID_PIC INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PIC_COL1 + " TEXT, " +
                PIC_COL2 + " INTEGER, " +
                PIC_COL3 + " TEXT, " +
                PIC_COL4 + " TEXT, " +
                "FOREIGN KEY ("+ PIC_COL2  +") REFERENCES "+ ALBUM_TABLE +"(ID) ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createAlbumTable);
        sqLiteDatabase.execSQL(createPictureTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ALBUM_TABLE);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ PICTURE_TABLE);
        onCreate(sqLiteDatabase);
    }


    /* *********************************************************
    *               Operations on ALBUM_TABLE
    ***********************************************************/

    /**
     * Add a new album by name
     * @param item album name's
     * */
    public void addNewAlbum(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AL_COL1, item);
        Log.i(TAG, "addData: Adding " + item + " to " + ALBUM_TABLE);
        db.insert(ALBUM_TABLE, null, contentValues);

    }

    /**
     * Remove an album by id
     * @param id album's id
     * */
    public void rmAlbum(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + ALBUM_TABLE + " WHERE "
                + AL_COL0 + " = " + id + "" ;
        Log.d(TAG, "delete query: " + query);
        Log.d(TAG, "Deleting an album from database.");
        db.execSQL(query);

    }


    /**
     * Get all the albums in the db
     * @return data
     * */
    public List<Album> getAlbumData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + ALBUM_TABLE;
        Cursor data = db.rawQuery(query, null);

        List<Album> dataLs = new ArrayList<Album>();
        while(data.moveToNext()) {
            dataLs.add(
                    new Album(
                            data.getInt(0),
                            data.getString(1)
                    )
            );
        }
        data.close();

        return dataLs;

    }

    /**
     * Get all the last album's id in the db
     * @return id
     * */
    public int getLastAlbumId() {
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT MAX(ID) FROM " + ALBUM_TABLE;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()) {
            id = data.getInt(0);
        }

        data.close();

        return id;
    }


    /* *********************************************************
     *               Operations on PICTURE_TABLE
    ***********************************************************/

    /**
     * Add a new picture object in the db
     * @param  new_picture Picture Object
     * */
    public void addNewPicture(Picture new_picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PIC_COL1, new_picture.getDescription());
        contentValues.put(PIC_COL2, new_picture.getAlbum_id());
        contentValues.put(PIC_COL3, new_picture.getImgUriStr());
        contentValues.put(PIC_COL4, new_picture.getMediaUriStr());
        Log.i(TAG, "addData: Adding a new picture to " + PICTURE_TABLE);

        db.insert(PICTURE_TABLE, null, contentValues);
    }


    /**
     * Remove an album by id
     * @param id album's id
     * */
    public void rmPicture(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + PICTURE_TABLE + " WHERE "
                + PIC_COL0 + " = " + id ;
        Log.d(TAG, "deleting a picture: query: " + query);
        db.execSQL(query);
    }

    /**
     * Get the pictures inside an album
     * @param album_id album's id
     * @return dataLs
     * */
    public List<Picture> getPictureData(int album_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + PICTURE_TABLE + " WHERE " + PIC_COL2 + " = "+ album_id ;
        Cursor data = db.rawQuery(query, null);
        List<Picture> dataLs = new ArrayList<Picture>();

        while(data.moveToNext()) {
            dataLs.add(
                    new Picture(
                            data.getInt(0),
                            data.getString(1),
                            data.getInt(2),
                            data.getString(3),
                            data.getString(4)
                    )
            );
        }
        data.close();
        Log.d(TAG, "Fetching the pictures inside the album: " + query);

        return dataLs;
    }

    /**
     * Get all the last pictures's id in the db
     * @return id
     * */
    public int getLastPictureId() {
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT MAX("+ PIC_COL0 +") FROM " + PICTURE_TABLE;

        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()) {
            id = data.getInt(0);
        }
        data.close();

        Log.d(TAG, "Fetching the last id in the picture table: " + query);

        return id;
    }

    /**
     * Updates somme picture's comment
     * @param id picture's id
     * @param comment the new comment of the picture
     */
    public void updateComment(int id, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + PICTURE_TABLE + " SET " + PIC_COL1 + " = '" + comment + "'" +
                       " WHERE " + PIC_COL0 + " = " + id;

        Log.d(TAG, "UPDATE inserting a comment: " + query);
        db.execSQL(query);
    }


}
