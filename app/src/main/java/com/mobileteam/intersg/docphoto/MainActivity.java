package com.mobileteam.intersg.docphoto;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    CardView cV_1;
    CardView cV_2;
    CardView cV_3;
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

        cV_2 = findViewById(R.id.rent_doc_card);

        cV_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(getApplicationContext(), RentDocGallery.class);
               // startActivity(intent);
            }
        });

        cV_3 = findViewById(R.id.receipt_doc_card);

        cV_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), ReceiptDocGallery.class);
                //startActivity(intent);
            }
        });

        cV_4 = findViewById(R.id.photo_card);

        cV_4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, 10);
            }
        });
        //etRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Intent d = data;
        // try catch: for prevent the fatal error when the user go back
        try {

            /*Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            Intent intent = new Intent(this, Preview.class);

            intent.putExtra("img", bitmap);

            startActivity(intent);


            //img.setImageBitmap(bitmap);*/

            // reference: https://stackoverflow.com/questions/28609527/null-intent-on-onactivityresult-when-using-mediastore-extra-output-for-action-im
            if(requestCode == 10 && resultCode == RESULT_OK)
            {
                CharSequence gallerySelector[] = new CharSequence[]{"Doc Sale", "Doc Rent", "Doc Receipt", "Cancel"};

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
                                //convertir imagen a bitmap
                                Bundle extras = d.getExtras();
                                Bitmap imageBitmap = (Bitmap) extras.get("data");

                                //guardar imagen
                                SaveImage(imageBitmap);


                                break;
                            case 1:
                                break;
                            case 2:
                                break;

                            default: Msg();

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
        builder.setMessage("The image capture was canceled.");
        builder.show();
    }

    public void SaveImage(Bitmap ImageToSave) {

        String nameOfFolder = "/Nuevacarpeta";
        String file_path = getFilesDir().getAbsolutePath() + nameOfFolder;
        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);

        //Bitmap bitmap = BitmapFactory.decodeFile(file_path);

        ImageView imageView = findViewById(R.id.imgC);
        imageView.setImageBitmap(BitmapFactory.decodeFile(file_path));

        if (!dir.exists()) {
            dir.mkdirs();
        }
        String nameOfFile = "imagen";
        File file = new File(dir, nameOfFile + CurrentDateAndTime + ".jpg");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }
        catch(FileNotFoundException e) {
            UnableToSave();
        }
        catch(IOException e) {
            UnableToSave();
        }
    }
    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(getApplicationContext(),
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }
    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
    private void UnableToSave() {
        Toast.makeText(getApplicationContext(), "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }
    private void AbleToSave() {
        Toast.makeText(getApplicationContext(), "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }

}
