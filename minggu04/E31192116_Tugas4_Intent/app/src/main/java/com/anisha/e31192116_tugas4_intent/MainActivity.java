package com.anisha.e31192116_tugas4_intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //inisiasi object
    ImageView img1;
    Button btn1;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view
        img1 = findViewById(R.id.img1);
        btn1 = findViewById(R.id.btn1);

        //handle button click
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //check runtime permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //permission not granded, request it
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    //show popup for runtime permission
                    requestPermissions(permissions,PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    ImageFromGallery();
                }
            }
            else{
                //system or is less then marshmallow
                ImageFromGallery();
            }
            }
        });
    }

    private void ImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults){
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grandResults.length >0 && grandResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    ImageFromGallery();
                }
                //else{
                    //permission was denied
                    //Toast.makeText(context this, text "Permission denied...!", Toast.LENGTH_SHORT).show();
                //}
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            //set image to image view
            img1.setImageURI(data.getData());
        }
    }
}