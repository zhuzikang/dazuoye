package com.example.dazuoye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class me extends AppCompatActivity {

    private String username;
    private Button changepwd;
    private Button button;
    private TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        user=(TextView) findViewById(R.id.user);
        user.setText(username);
        changepwd=(Button) findViewById(R.id.changepwd);
        this.changepwd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(me.this, changepwd.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        button=(Button) findViewById(R.id.button3);
        this.button.setOnClickListener(new OnClickListenercall());
    }
    private class OnClickListenercall implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //打开系统的电话，填入110
            String telstr="110";
            Uri uri= Uri.parse("tel:"+telstr);
            Intent intent1=new Intent();
            intent1.setAction(Intent.ACTION_DIAL);
            intent1.setData(uri);
            startActivity(intent1);

            try {
                Thread.sleep(5000);
            } catch (Exception e) {
            }
            // 模式切换，传入type参数并打开首页
            Intent intent = new Intent(me.this, MainActivity2.class);
            intent.putExtra("username",username);
            intent.putExtra("type",true);
            startActivity(intent);
        }

    }

}