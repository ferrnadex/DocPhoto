package com.mobileteam.intersg.docphoto;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    CardView cV_1;
    CardView cV_4;

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

        cV_4 = findViewById(R.id.photo_card);

        cV_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, 0);
            }
        });
        //etRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT) // android suggestion
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        // try catch: for prevent the fatal error when the user go back
        try {

            /*Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            Intent intent = new Intent(this, Preview.class);

            intent.putExtra("img", bitmap);

            startActivity(intent);


            //img.setImageBitmap(bitmap);*/

            // reference: https://stackoverflow.com/questions/28609527/null-intent-on-onactivityresult-when-using-mediastore-extra-output-for-action-im
            if( resultCode == RESULT_OK )
            {
                CharSequence gallerySelector[] = new CharSequence[]{"Doc Sale", "Doc Rent", "Doc Receipt"};

                // reference:https://stackoverflow.com/questions/8605301/alertdialog-with-selector
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //  If set to false it allows to cancel the dialog box by clicking on area outside the dialog else it allows.
                // reference: https://abhiandroid.com/ui/alertdialog
                builder.setCancelable(false);

                builder.setTitle("Select Option");
                builder.setItems(gallerySelector, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("value is", "" + which);

                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                builder.show();
            }
            else
            {
                Msg();
            }

        }
        catch (Exception e)
        {
            // no action
        }


    }

    private void Msg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("The image will not be save.");
        builder.show();
    }

}
