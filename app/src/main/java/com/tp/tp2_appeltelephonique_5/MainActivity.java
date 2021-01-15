package com.tp.tp2_appeltelephonique_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {
    private Button bouton;
    private EditText phonenumber;
    final static int reqCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bouton = findViewById(R.id.button);
        bouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                phonenumber = (EditText) findViewById(R.id.phoneNumber);
                String numero = phonenumber.getText().toString();
                Intent callIntent = new Intent();
                callIntent.setData(Uri.parse("tel:" + numero));
                callIntent.setAction(Intent.ACTION_CALL);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    /*
                    Appel à la méthode AskForPermission() pour que l'utilisateur donne la
                    permission manuellement
                    */
                    AskForPermission();
                } else {
                    startActivity(callIntent);
                }

            }

            //The method to ask for permission in case of denial of permission or at first call attempt
            public void AskForPermission() {
                //This bloc of condition is excecuted in case of denial of permission by the user for the first time
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE)) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("test").setMessage("permission pour pouvoir passer l'appel que" +
                            "vous voulez" +
                            "").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, reqCode);
                        }

                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_LONG).show();
                        }
                    }).create().show();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, reqCode);
                }

            }

        });

    }
}