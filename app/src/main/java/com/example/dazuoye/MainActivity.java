package com.example.dazuoye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import com.example.dazuoye.db.RegisterHelper;

public class MainActivity extends AppCompatActivity {

    private Button bt_login;
    private Button bt_register;
    private EditText et_username;
    private EditText et_password;
    private CheckBox remember;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_login=(Button) findViewById(R.id.login);
        bt_register=(Button) findViewById(R.id.register);
        et_username=(EditText) findViewById(R.id.username);
        et_password=(EditText) findViewById(R.id.password);
        remember=(CheckBox) findViewById(R.id.remember);
        this.bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        this.bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        isRemember();

    }
    private void Login() {

        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        //判断是否输入内容
        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "请输入账号或密码", Toast.LENGTH_SHORT).show();
        } else {//输入了账号&密码
            db = new RegisterHelper(this).getReadableDatabase();
            Cursor cursor = db.query("user", new String[]{"username", "password"}, null, null, null, null, null);
            boolean login = false;//账号密码是否匹配
            //从数据库中匹配账号密码
            while (cursor.moveToNext()) {
                if (username.equals(cursor.getString(cursor.getColumnIndex("username")))
                        && password.equals(cursor.getString(cursor.getColumnIndex("password")))) {
                    login = true;
                    break;
                }
            }
            //如果匹配成功，判断是否勾选记住账号，并进行账号信息存储
            if (login) {
                SharedPreferences.Editor editor = getSharedPreferences("remember", MODE_PRIVATE).edit();
                if (remember.isChecked()) {
                    editor.putBoolean("remember", true);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();
                } else {
                    editor.clear();
                }
                editor.commit();
                db.close();

                //跳转到首页并传入username
                Intent intent = new Intent(this, MainActivity2.class);
                intent.putExtra("username",et_username.getText().toString());
                startActivity(intent);
            } else {
                //账号密码在数据库中匹配不成功
                Toast.makeText(this, "账号与密码不匹配", Toast.LENGTH_SHORT).show();
            }

        }

    }


    public void isRemember() {
        SharedPreferences prefer = getSharedPreferences("remember", MODE_PRIVATE);
        boolean isRemember = prefer.getBoolean("remember", false);
        if (isRemember) {
            et_username.setText(prefer.getString("username", ""));
            et_password.setText(prefer.getString("password", ""));
            remember.setChecked(true);
        }
    }

}