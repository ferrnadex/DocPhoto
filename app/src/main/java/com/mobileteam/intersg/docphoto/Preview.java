package com.mobileteam.intersg.docphoto;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Preview extends AppCompatActivity {
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        img = findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();

        //kkk
        if(bundle != null)
        {
            int res_id = bundle.getInt("resId");
            img.setImageResource(res_id);
        }
    }


}
