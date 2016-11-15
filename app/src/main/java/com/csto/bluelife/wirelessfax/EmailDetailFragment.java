package com.csto.bluelife.wirelessfax;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.transition.Fade;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.csto.bluelife.wirelessfax.combin.ImageManager;
import com.csto.bluelife.wirelessfax.model.TiffImages;
import com.csto.bluelife.wirelessfax.utils.FileUtil;
import com.csto.bluelife.wirelessfax.utils.ImageUtil;
import com.csto.bluelife.wirelessfax.widget.ChooseFragment;
import com.csto.bluelife.wirelessfax.widget.TouchImageView;
import com.github.gcacace.signaturepad.views.SignaturePad;

import org.beyka.tiffbitmapfactory.TiffReplace;
import org.beyka.tiffbitmapfactory.TiffSaver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by slomka.jin on 2016/11/4.
 */

public class EmailDetailFragment extends Fragment implements ImageManager.Listener, ActionMode.Callback, View.OnTouchListener {

    @BindView(R.id.signText)
    TextView signText;
    @BindView(R.id.signImage)
    TextView signImageText;
    @BindView(R.id.detail_reward)
    TextView reward;
    @BindView(R.id.detail_del)
    TextView del;
    @BindView(R.id.detail_more)
    TextView more;
    @BindView(R.id.drawer_layout)
    FrameLayout frameLayout;
    @BindView(R.id.detail_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.root_layout)
    ViewGroup rootView;
    @BindView(R.id.email_bottom_image)
    ImageView tiffImage;
    @BindView(R.id.email_up_image)
    TouchImageView pickedImage;
    ImageView signImage;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
    private ImageManager imageManager;
    private AppCompatActivity activity;
    private final int FIX_WIDTH=1728;
    private SignaturePad signaturePad;
    private BottomSheetDialog bottomSheetDialog;
    private ImageView pointView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_email_detail,container,false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_back);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.back));
        return view;
    }

    @OnClick(R.id.signText)
    void onSignText(){
        fadeButton(signText);
        pickedImage.setVisibility(View.GONE);
        imageManager.onSignnature();
    }
    @OnClick(R.id.signImage)
    void onSignImage(){
        fadeButton(signImageText);
        pickedImage.setVisibility(View.VISIBLE);
        imageManager.onImagePick();
    }
    @OnClick(R.id.detail_reward)
    void onReward(){
        fadeButton(reward);
    }
    @OnClick(R.id.detail_del)
    void onDel(){
        fadeButton(del);
    }
    @OnClick(R.id.detail_more)
    void onMore(){
        fadeButton(more);
    }
    void fadeButton(View view){
        buttonClick.setDuration(500);
        view.startAnimation(buttonClick);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=(AppCompatActivity) getActivity();
        imageManager=new ImageManager(activity,this);
        imageManager.setListener(this);
        progressBar.setVisibility(View.GONE);
        pickedImage.setMinZoom(0.25f);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.email_detail_menu,menu);
    }



    @Override
    public void PickFormatError() {
        Snackbar.make(rootView,getString(R.string.pick_error_format),Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void setTiff() {
        List<Bitmap> bitmaps= FileUtil.loadImage(imageManager.getImage());
        Log.w("setttiff",""+bitmaps.size());

            TiffImages.getInstance().setImages(bitmaps);
            showChooseDialog();

            //tiffImage.setImageBitmap(bitmaps.get(0));

    }
    private void showChooseDialog(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        ChooseFragment newFragment = new ChooseFragment();
        newFragment.setListener(chooseListener);
        newFragment.show(ft, "dialog");
    }
    private ChooseFragment.ClickListener chooseListener=new ChooseFragment.ClickListener() {
        @Override
        public void onDone() {
            tiffImage.setImageBitmap(TiffImages.getInstance().getSelectBitmap());
            imageManager.pickImage();
        }
    };

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void doCombin(String image) {
        activity.startSupportActionMode(this);
        Bitmap bitmap=FileUtil.loadImage(image).get(0);
        pickedImage.setImageBitmap(bitmap);
    }

    @Override
    public void onSelectSignPosition() {
        Snackbar snackbar=Snackbar.make(rootView,getString(R.string.detail_choose_signature_position),Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
        snackbar.show();
        frameLayout.setOnTouchListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.combin_menu, menu);

        return true;
    }


    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Log.w("actionmode",item.getItemId()+"");
        switch (item.getItemId()) {
            case R.id.action_combin:
                combinAll();
                mode.finish();
                break;

        }
        return true;
    }

    private void combinAll(){
        progressBar.setVisibility(View.VISIBLE);

            //combin image;

            frameLayout.setDrawingCacheEnabled(true);
            Bitmap capture = frameLayout.getDrawingCache();
            int width, height;
            width = capture.getWidth();
            height = capture.getHeight();
            Bitmap fixWidthBitmap = Bitmap.createBitmap(FIX_WIDTH, height, Bitmap.Config.RGB_565);
            fixWidthBitmap.eraseColor(Color.WHITE);
            Canvas canvas = new Canvas(fixWidthBitmap);
            Rect src = new Rect(0, 0, width, height);
            Rect dest = new Rect(src);
            dest.offset(FIX_WIDTH / 2 - width / 2, 0);
            canvas.drawBitmap(capture, src, dest, null);
            //Bitmap grayBmp = Transform.grayscale(capture);
            new SaveTiffTask().execute(fixWidthBitmap);
            frameLayout.setDrawingCacheEnabled(false);
        if(imageManager.isSignMode()){
            removeSignImage();
        }
    }
    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    public void removeTouchListener(){
        frameLayout.setOnTouchListener(null);
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                attachPoint((int) x,(int) y);
                imageManager.setSignPoint(new PointF(x,y));
                removeTouchListener();
                showSignatureDialog();
                return true;
        }
        return false;
    }
    private void attachPoint(int x,int y){
        pointView=new ImageView(getContext());
        pointView.setImageResource(R.drawable.detail_touch_point);
        int width = pointView.getDrawable().getIntrinsicWidth();
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(x-width/2,y-10,0,0);
        frameLayout.addView(pointView,layoutParams);
    }

    public void showSignatureDialog(){
        bottomSheetDialog = new BottomSheetDialog(getContext());

        View view1 = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        signaturePad= (SignaturePad) view1.findViewById(R.id.signature_pad);
        bottomSheetDialog.setContentView(view1);
        Button button=(Button)view1.findViewById(R.id.detail_sign_done);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.hide();
                drawSignature();
                imageManager.setHasSigned(true);
                doSign();
            }
        });
        ImageView delBtn=(ImageView)view1.findViewById(R.id.detail_sign_del);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signaturePad.clear();
            }
        });
        bottomSheetDialog.show();

    }

    @Override
    public void doSign() {
        activity.startSupportActionMode(EmailDetailFragment.this);
    }

    private void fadeOutAndHideImage(final ImageView img) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                removePoint();
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        img.startAnimation(fadeOut);
    }

    private void drawSignature(){
        fadeOutAndHideImage(pointView);
        signaturePad.setDrawingCacheEnabled(true);
        Bitmap bitmap=signaturePad.getDrawingCache();
        bitmap= ImageUtil.TrimBitmap(bitmap);
        Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/4,bitmap.getHeight()/4,false);
        signImage=new ImageView(getContext());
        signImage.setImageBitmap(scaledBitmap);
        signaturePad.setDrawingCacheEnabled(false);
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        PointF pointF=imageManager.getSignPoint();
        int left= (int) pointF.x;
        int top=(int)pointF.y;
        layoutParams.setMargins(left,top,0,0);
        frameLayout.addView(signImage,layoutParams);
    }

    @Override
    public void removeImage() {
        pickedImage.setImageBitmap(null);
    }

    @Override
    public void removeSign() {
        removePoint();
        removeSignImage();
    }
    private void removePoint(){
        if(pointView!=null){
            frameLayout.removeView(pointView);
            pointView=null;
        }
    }
    private void removeSignImage(){
        frameLayout.removeView(signImage);
        signImage=null;
    }

    private class SaveTiffTask extends AsyncTask<Bitmap,Void,Boolean> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Bitmap... params) {
            boolean isMultiple=TiffImages.getInstance().getImages().size()>1;
            boolean success=imageManager.saveImage(params[0],isMultiple);
            if(isMultiple) {
                success &= TiffReplace.replacePage(imageManager.getTempTiffPath(),
                        imageManager.getImage(),TiffImages.getInstance().getSelectIndex());
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean done) {
            progressBar.setVisibility(View.GONE);
            if(done){
                imageManager.init();
                setTiff();
                pickedImage.setImageBitmap(null);
            }
            else{
                Snackbar snackbar=Snackbar.make(frameLayout,"some error accured,image not saved.",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
}
