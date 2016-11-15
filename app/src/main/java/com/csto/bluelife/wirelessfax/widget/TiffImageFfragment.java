package com.csto.bluelife.wirelessfax.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.csto.bluelife.wirelessfax.R;
import com.csto.bluelife.wirelessfax.model.TiffImages;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by slomka.jin on 2016/11/15.
 */

public class TiffImageFfragment extends Fragment {
    @BindView(R.id.frag_tiff_imageview)
    ImageView tiffImage;
    private int index;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frag_tiff_image,container,false);
        ButterKnife.bind(this,v);
        tiffImage.setImageBitmap(TiffImages.getInstance().getImages().get(index));
        return v;
    }

    public static TiffImageFfragment getInstance(){
        return new TiffImageFfragment();
    }
    public void setIndex(int pos){
        index=pos;
    }

}
