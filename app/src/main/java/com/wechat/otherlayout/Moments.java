package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.wechat.R;

public class Moments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

        ImageButton sendMoments = (ImageButton)findViewById(R.id.send_moments);
        sendMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Moments.this,SendMoments.class);
                startActivity(send);
            }
        });

        ImageButton back = (ImageButton)findViewById(R.id.moments_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
