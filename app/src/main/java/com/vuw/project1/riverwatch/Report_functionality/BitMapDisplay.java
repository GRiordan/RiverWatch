package com.vuw.project1.riverwatch.Report_functionality;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

/**
 * Created by Alex on 18/07/2016.
 */
public class BitmapDisplay {
    /**
     * Rotates the image based on the angle so it can be displayed in the right
     * orientation in the fragment
     * */
    public static Bitmap rotateBitmap(final Bitmap source, final float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * Efficiently decodes scaled bitmap file based on input height
     * Modified from google android guide: http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
     * @param path
     * @param reqHeight
     * @return decoded bitmap
     */
    public static Bitmap decodeSampledBitmapFromPath(String path, int reqHeight) {

        Log.d("Will", "decoding bitmap");

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

//        Log.d("Will", "before: " + options.inSampleSize);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqHeight);
//        Log.d("Will", "after: " + options.inSampleSize);

        // don't load alpha (transparency) channel
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
