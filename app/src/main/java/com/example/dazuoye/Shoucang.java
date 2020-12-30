package com.example.dazuoye;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.dazuoye.db.PictureDatabase;

public class Shoucang extends Activity {

    private String username;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView iv = new ImageView(this);
        if(getDrawable().size() != 0) {
            iv.setImageDrawable(getDrawable().get(0));
        }
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        setContentView(iv);
    }


    private ArrayList<Drawable> getDrawable() {
        PictureDatabase pd = new PictureDatabase(this);
        SQLiteDatabase sd = pd.getWritableDatabase();

        ArrayList<Drawable> drawables = new ArrayList<Drawable>();

        //查询数据库
        Cursor c = sd.query("picture", null, null, null, null, null, null);

        //遍历数据
        if(c != null && c.getCount() != 0) {
            while(c.moveToNext()) {
                //获取数据
                byte[] b = c.getBlob(c.getColumnIndexOrThrow(PictureDatabase.PictureColumns.PICTURE));
                //将获取的数据转换成drawable
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                Drawable drawable = bitmapDrawable;
                drawables.add(drawable);
            }
        }
        return drawables;
    }
}