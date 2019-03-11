package com.mobileteam.intersg.docphoto;

// reference: https://danielme.com/tip-android-25-galeria-de-imagenes-con-el-widget-gallery/

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.Objects;

public class SaleDocGallery extends AppCompatActivity {
    ImageView selectedImg;
    Gallery gallery;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_doc_gallery);

        selectedImg = findViewById(R.id.seleccionada);

        final Integer[] img = {R.mipmap.receipt, R.mipmap.receipt, R.mipmap.rent, R.mipmap.car};

        gallery = findViewById(R.id.gallery);

        gallery.setAdapter(new GalleryAdapter(this, img));
        //al seleccionar una imagen, la mostramos en el centro de la pantalla a mayor tamaño

        //con este listener, sólo se mostrarían las imágenes sobre las que se pulsa
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                selectedImg.setImageBitmap(decodeSampledBitmapFromResource(getResources(), img[position], 300, 0));
            }

        });

        gallery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //final String selectedItem = (String) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Do you want delete this image?").setTitle("Delete Item");
                // Add the buttons
                builder.setPositiveButton(R.string.y, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        // User clicked DELETE button

                    }
                });
                builder.setNegativeButton(R.string.n, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();

                dialog.show();

                return true;
            }

        });

        selectedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedImg.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) selectedImg.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                Intent intent = new Intent(getApplicationContext(), Preview.class);

                intent.putExtra("img", bitmap);

                startActivity(intent);
            }
        });
    }

    // reference: https://developer.android.com/topic/performance/graphics/load-bitmap
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
        BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}