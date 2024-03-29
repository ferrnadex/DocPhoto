package com.mobileteam.intersg.docphoto;
// reference: https://danielme.com/tip-android-25-galeria-de-imagenes-con-el-widget-gallery/
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private Integer[] img;
    private int background;
    //guardamos las imágenes reescaladas para mejorar el rendimiento ya que estas operaciones son costosas
    //se usa SparseArray siguiendo la recomendación de Android Lint
    private SparseArray<Bitmap> ScalableImg = new SparseArray<>(7);

    GalleryAdapter(Context context, Integer[] imgs)
    {
        super();
        this.img = imgs;
        this.context = context;

        //establecemos un marco para las imágenes (estilo por defecto proporcionado)
        //por android y definido en /values/attr.xml
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.Gallery1);
        background = typedArray.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
        typedArray.recycle();
    }

    @Override
    public int getCount()
    {
        return img.length;
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imagen = new ImageView(context);

        //reescalamos la imagen para evitar "java.lang.OutOfMemory" en el caso de imágenes de gran resolución
        //como es este ejemplo
        if (ScalableImg.get(position) == null)
        {
            Bitmap bitmap = decodeSampledBitmapFromResource(context.getResources(), img[position]);
            ScalableImg.put(position, bitmap);
        }
        imagen.setImageBitmap(ScalableImg.get(position));
        //se aplica el estilo
        imagen.setBackgroundResource(background);

        return imagen;
    }
    // reference: https://developer.android.com/topic/performance/graphics/load-bitmap
    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > 0 || width > 120) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= 0
                    && (halfWidth / inSampleSize) >= 120) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
