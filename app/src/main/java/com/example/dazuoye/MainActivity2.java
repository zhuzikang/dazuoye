package com.example.dazuoye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dazuoye.db.PictureDatabase;

import java.io.ByteArrayOutputStream;

public class MainActivity2 extends AppCompatActivity {

    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;
    private ImageView imageView9;
    private Button shoucang;
    private Button me;
    private Button message;
    private Button refresh;
    private Bitmap bitmap;
    private PictureDatabase dbHelper;
    private String username;
    private Boolean cr;
    private MyBroadcastReceiver myReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.imageView2 = (ImageView) this.findViewById(R.id.imageView2);
        this.imageView3 = (ImageView) this.findViewById(R.id.imageView3);
        this.imageView4 = (ImageView) this.findViewById(R.id.imageView4);
        this.imageView5 = (ImageView) this.findViewById(R.id.imageView5);
        this.imageView6 = (ImageView) this.findViewById(R.id.imageView6);
        this.imageView7 = (ImageView) this.findViewById(R.id.imageView7);
        this.imageView8 = (ImageView) this.findViewById(R.id.imageView8);
        this.imageView9 = (ImageView) this.findViewById(R.id.imageView9);

        myReceiver = new MyBroadcastReceiver();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, itFilter);

        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        cr=intent.getBooleanExtra("type",false);
        downloadAndShowImage(cr);
        shoucang=(Button) findViewById(R.id.shoucang);
        this.shoucang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Shoucang.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        this.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
//                Drawable drawable=imageView2.getDrawable();
//                drawableToBitamp(drawable);
                byte[] bytes = bitmap2Bytes(bitmap);
                // 图片太大会闪退
                Intent intent = new Intent(MainActivity2.this, ShowImage.class);
                intent.putExtra("username",username);
                intent.putExtra("image", bytes);
                startActivity(intent);
            }
        });


        refresh=(Button) findViewById(R.id.refresh);
        this.refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                downloadAndShowImage(cr);
            }
        });

        me=(Button) findViewById(R.id.me);
        this.me.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, me.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private boolean checkInternetConnection() {
        // Get Connectivity Manager
        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Details about the currently active default data network
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        //Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }

    // When user click on the "Download Image".
    public void downloadAndShowImage(Boolean cr) {
        boolean networkOK = this.checkInternetConnection();
        if (!networkOK) {
            return;
        }
        String imageUrl;
        if(cr){
            imageUrl = "https://uploadbeta.com/api/pictures/random/?key=%E6%8E%A8%E5%A5%B3%E9%83%8E";
        }else{
            // 能返回图片的api
            imageUrl = "https://api.pingping6.com/tools/scenery/index.php";
        }
        // Create a task to download and display image.
        DownloadImageTask task1 = new DownloadImageTask(this.imageView2);
        // Execute task (Pass imageUrl).
        task1.execute(imageUrl);
        DownloadImageTask task2 = new DownloadImageTask(this.imageView3);
        // Execute task (Pass imageUrl).
        task2.execute(imageUrl);
        DownloadImageTask task3 = new DownloadImageTask(this.imageView4);
        // Execute task (Pass imageUrl).
        task3.execute(imageUrl);
        DownloadImageTask task4 = new DownloadImageTask(this.imageView5);
        //        // Execute task (Pass imageUrl).
        task4.execute(imageUrl);
        DownloadImageTask task5 = new DownloadImageTask(this.imageView6);
        // Execute task (Pass imageUrl).
        task5.execute(imageUrl);
        DownloadImageTask task6 = new DownloadImageTask(this.imageView7);
        // Execute task (Pass imageUrl).
        task6.execute(imageUrl);
        DownloadImageTask task7 = new DownloadImageTask(this.imageView8);
        // Execute task (Pass imageUrl).
        task7.execute(imageUrl);
        DownloadImageTask task8 = new DownloadImageTask(this.imageView9);
        // Execute task (Pass imageUrl).
        task8.execute(imageUrl);
    }
    private void drawableToBitamp(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w,h,config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
    }
    private byte[] bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}