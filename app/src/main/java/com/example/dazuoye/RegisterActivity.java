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

import com.example.dazuoye.R;
import com.example.dazuoye.db.RegisterHelper;

public class RegisterActivity extends AppCompatActivity {
    private Button register_submit;
    private Button register_login;
    private EditText register_username;
    private EditText register_password1;
    private EditText register_password2;
    private RegisterHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_username = (EditText) findViewById(R.id.username);
        register_password1 = (EditText) findViewById(R.id.password1);
        register_password2 = (EditText) findViewById(R.id.password2);
        register_submit=(Button) findViewById(R.id.register);
        register_login=(Button) findViewById(R.id.login);
        this.register_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        this.register_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterSave();
            }
        });
    }


    private void RegisterSave() {
        String user_name = register_username.getText().toString();
        String pass1 = register_password1.getText().toString();
        String pass2 = register_password2.getText().toString();
        boolean creatUser = true;
        if (user_name.equals("") || pass1.equals("") || pass2.equals("")) {
            Toast.makeText(this, "请完整输入各项注册内容", Toast.LENGTH_SHORT).show();
        } else if (!pass1.equals(pass2)) {
            Toast.makeText(this, "两次输入密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
        } else if (pass1.length() < 6) {
            Toast.makeText(this, "密码小于六位数，请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper = new RegisterHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("user", new String[]{"username"}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                if (user_name.equals(cursor.getString(cursor.getColumnIndex("username")))) {
                    Toast.makeText(RegisterActivity.this, "该账户已存在", Toast.LENGTH_SHORT).show();
                    creatUser = false;
                }
            }
            if (creatUser) {
                ContentValues values = new ContentValues();
                values.put("username", user_name);
                values.put("password", pass1);
                db.insert("user", null, values);
                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle("注册成功");
                dialog.setMessage("您已成功注册账户，请返回登录界面");
                dialog.setPositiveButton("返回登录界面", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialog.setNegativeButton("留在注册界面", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                db.close();
                dialog.show();
            }

        }

    }
}