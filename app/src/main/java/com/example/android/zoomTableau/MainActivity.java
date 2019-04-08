
package com.example.android.zoomTableau;



import com.example.android.dao.DatabaseHelper;
import com.example.android.javaBeans.Album;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;



import org.opencv.android.OpenCVLoader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    RecyclerView myrv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myrv = null;
        myrv = findViewById(R.id.recyclerview_albums_id);
        RecyclerViewAlbumsAdapter mAdapter = new RecyclerViewAlbumsAdapter(this);
        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(mAdapter);

        OpenCVLoader.initDebug();
        MainActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }


/*
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
        /*
        Utils.matToBitmap(rgba, enhancedBitmap);

        imageView.setImageBitmap(enhancedBitmap);

    }
    */
}
