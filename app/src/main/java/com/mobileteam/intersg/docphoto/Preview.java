package com.mobileteam.intersg.docphoto;

import android.content.res.Configuration;
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


        if(bundle != null)
        {
            Bitmap imgExtra = (Bitmap) bundle.get("img");

            if( imgExtra != null)
            {
                img.setImageBitmap(imgExtra);

            }

        }
    }


}
