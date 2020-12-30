package com.example.dazuoye.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.BaseColumns;

import com.example.dazuoye.R;

import java.io.ByteArrayOutputStream;

public class PictureDatabase extends SQLiteOpenHelper {

    //数据库的字段
    public static class PictureColumns implements BaseColumns {
        public static final String PICTURE = "picture";
    }

    private Context mContext;

    //数据库名
    private static final String DATABASE_NAME = "picture.db";
    //数据库版本号
    private static final int DATABASE_Version = 1;
    //表名
    private static final String TABLE_NAME = "picture";

    //创建数据库
    public PictureDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.mContext = context;
    }

    //创建表并初始化表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table " + TABLE_NAME + "(" + BaseColumns._ID
                + " integer primary key autoincrement," + PictureColumns.PICTURE
                + " blob not null);";
        db.execSQL(sql);

        //初始化
        initDataBase(db, mContext);
    }

    //将转换后的图片存入到数据库中
    private void initDataBase(SQLiteDatabase db, Context context) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.home);
        ContentValues cv = new ContentValues();
        cv.put(PictureColumns.PICTURE, getPicture(drawable));
        db.insert(TABLE_NAME, null, cv);
    }

    //将drawable转换成可以用来存储的byte[]类型
    private byte[] getPicture(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }

    //更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}