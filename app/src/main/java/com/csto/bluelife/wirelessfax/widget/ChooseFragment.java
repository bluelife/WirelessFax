package com.csto.bluelife.wirelessfax.widget;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.csto.bluelife.wirelessfax.R;
import com.csto.bluelife.wirelessfax.model.TiffImages;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by slomka.jin on 2016/11/15.
 */

public class ChooseFragment extends DialogFragment {

    @BindView(R.id.frag_choose_done)
    Button doneBtn;
    @BindView(R.id.flexibleIndicator)
    ExtensiblePageIndicator extensiblePageIndicator;
    @BindView(R.id.frag_choose_viewpager)
    ViewPager viewPager;
    private ClickListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frag_choose_tiff,container,false);
        ButterKnife.bind(this,v);
        TiffPagerAdapter tiffPagerAdapter=new TiffPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(tiffPagerAdapter);
        extensiblePageIndicator.initViewPager(viewPager);
        return v;
    }

    public interface ClickListener{
        void onDone();
    }
    public void setListener(ClickListener listener){
        this.listener=listener;
    }
    @OnClick(R.id.frag_choose_done)
    void onConfirm(){
        TiffImages.getInstance().setSelectIndex(viewPager.getCurrentItem());
        listener.onDone();
        dismiss();
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog= super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }
}
