
package com.example.android.zoomTableau;



import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;



import org.opencv.android.OpenCVLoader;


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

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Quitter l'application ?");
        // Setting Dialog Message
        alertDialog.setMessage("Confirmer votre choix");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.background_icon);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Quitter",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        //System.exit(0);
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Annuler",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }



}
