package com.csto.bluelife.wirelessfax.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.beyka.tiffbitmapfactory.TiffBitmapFactory;

import java.io.File;

/**
 * Created by slomka.jin on 2016/10/31.
 */

public class FileUtil {
    public static Bitmap loadTiff(String path) {
        TiffBitmapFactory.Options options = new TiffBitmapFactory.Options();
        options.inJustDecodeBounds = true;
        File file = new File(path);
        TiffBitmapFactory.decodeFile(file.getAbsoluteFile(), options);

        int dirCount = options.outDirectoryCount;
//Read and process all images in file

        options.inDirectoryNumber = 0;
        TiffBitmapFactory.decodeFile(file, options);
        int curDir = options.outCurDirectoryNumber;
        int width = options.outWidth;
        int height = options.outHeight;
        Log.w("image", path + " " + width + " " + height);
        //Change sample size if width or height bigger than required width or height
            /*int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }*/
        options.inJustDecodeBounds = false;
        options.inSampleSize = 1;

        // Specify the amount of memory available for the final bitmap and temporary storage.
        options.inAvailableMemory = 30000000; // bytes

        Bitmap bmp = TiffBitmapFactory.decodeFile(file, options);
        return bmp;
    }
    public static Bitmap loadImage(String path){
        if(path.substring(path.lastIndexOf(".")+1).equalsIgnoreCase("tif")){
            return FileUtil.loadTiff(path);
        }
        else{
            return BitmapFactory.decodeFile(path);
        }
    }
}
