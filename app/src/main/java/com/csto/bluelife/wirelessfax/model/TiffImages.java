package com.csto.bluelife.wirelessfax.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by slomka.jin on 2016/11/15.
 */

public class TiffImages {
    private static TiffImages tiffImages;
    private List<Bitmap> images;
    private int selectIndex;
    public static TiffImages getInstance(){
        if(null==tiffImages)
            tiffImages=new TiffImages();
        return tiffImages;
    }
    public void setSelectIndex(int index){
        selectIndex=index;
    }
    public int getSelectIndex(){
        return selectIndex;
    }
    public Bitmap getSelectBitmap(){
        return images.get(selectIndex);
    }

    public void setImages(List<Bitmap> bitmaps){
        images=bitmaps;
    }
    public List<Bitmap> getImages(){
        return images;
    }
}
