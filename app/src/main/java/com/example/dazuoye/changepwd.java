package com.example.dazuoye;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dazuoye.db.RegisterHelper;
import android.database.sqlite.SQLiteDatabase;

public class changepwd extends AppCompatActivity {

    private Button submit;
    private EditText password1;
    private EditText password2;
    private RegisterHelper dbHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
        submit=(Button) findViewById(R.id.submit);
        this.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeSave();
            }
        });
    }

    private void ChangeSave() {
        String pass1 = password1.getText().toString();
        String pass2 = password2.getText().toString();
        boolean creatUser = true;
        if (pass1.equals("") || pass2.equals("")) {
            Toast.makeText(this, "请先填写各项", Toast.LENGTH_SHORT).show();
        } else if (!pass1.equals(pass2)) {
            Toast.makeText(this, "两次输入密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
        } else if (pass1.length() < 6) {
            Toast.makeText(this, "密码小于六位数，请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper = new RegisterHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("user", new String[]{"username"}, null, null, null, null, null);
            if (creatUser) {
                ContentValues values = new ContentValues();
                values.put("username", username);
                values.put("password", pass1);
                db.execSQL("Update user set password=? where username= ? ",
                        new Object[ ]{ pass1,username } );
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_LONG).show();
                db.close();
            }

        }

    }
}