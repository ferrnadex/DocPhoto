package com.mobileteam.intersg.docphoto;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Preview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        // reference: https://eazyprogramming.blogspot.com/2013/01/passing-image-between-activities.html
        Bundle extras = getIntent().getExtras();
        assert extras != null; //android suggestion
        byte[] b = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        ImageView image = findViewById(R.id.imageView);
        image.setImageBitmap(bmp);
    }


}
