package com.mobileteam.intersg.docphoto;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    CardView cV_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cV_1 = findViewById(R.id.sale_doc_card);

        cV_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SaleDocGallery.class);
                startActivity(intent);
            }
        });

        //etRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

    }

    public void photo(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, 0);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT) // android suggestion
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // try catch: for prevent the fatal error when the user go back
        try {

            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            Intent intent = new Intent(this, Preview.class);

            intent.putExtra("img", bitmap);

            startActivity(intent);

            //img.setImageBitmap(bitmap);
        }
        catch (Exception e)
        {
            // no action
        }


    }

}
