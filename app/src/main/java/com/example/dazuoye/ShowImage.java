package com.example.dazuoye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowImage extends AppCompatActivity {
    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        this.imageview = (ImageView) this.findViewById(R.id.imageV);
        Intent intent=getIntent();
        byte[] appIcons = intent.getByteArrayExtra("image");
        Bitmap bitmap= BitmapFactory.decodeByteArray(appIcons,0,appIcons.length);
        this.imageview.setImageBitmap(bitmap);
    }
}