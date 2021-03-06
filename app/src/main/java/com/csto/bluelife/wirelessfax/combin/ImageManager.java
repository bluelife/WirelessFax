package com.csto.bluelife.wirelessfax.combin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nononsenseapps.filepicker.FilePickerActivity;

import org.beyka.tiffbitmapfactory.TiffSaver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by slomka.jin on 2016/11/11.
 */

public class ImageManager implements ActivityListener{
    private AppCompatActivity context;
    private Fragment fragment;
    private static final int FILE_CODE = 1;
    private Listener listener;
    private int step;
    private List<String> supportFormats;
    private String tiffPath;
    private String tempTiffPath;
    private String resultTiffPath;
    private String pickImagePath;
    public static final String MODE_IMAGE="image";
    public static final String MODE_SIGN="sign";
    private String currentMode;
    private PointF signPoint;
    private boolean hasSigned;
    public ImageManager(AppCompatActivity context,Fragment fragment) {
        this.context=context;
        this.fragment=fragment;
        supportFormats=new ArrayList<>();
        supportFormats.add("png");
        supportFormats.add("jpg");
        supportFormats.add("bmp");
    }
    public interface Listener{
        void PickFormatError();
        void setTiff();
        void showTiff();
        void doCombin(String image);
        void doSign();
        void onSelectSignPosition();
        void removeImage();
        void removeSign();
        void showSignatureDialog();
    }

    public void setListener(Listener listener){
        this.listener=listener;
    }
    public String getImage(){
        return tiffPath;
    }
    public void onSignnature(){
        switchToSignature();
        if(tiffPath==null){
            pickTiff();
        }
        else if(signPoint==null){
            listener.onSelectSignPosition();
        }
        else if(!hasSigned){
            listener.showSignatureDialog();
        }
        else{
            listener.doSign();
        }
    }
    public void setHasSigned(boolean flag){
        hasSigned=flag;
    }
    private void switchToSignature(){
        currentMode=MODE_SIGN;
        if(step==2){
            listener.removeImage();
        }
        step=0;
        pickImagePath=null;
    }
    private void switchToCombin(){
        currentMode=MODE_IMAGE;
        listener.removeSign();
        signPoint=null;
        hasSigned=false;
    }
    public void onImagePick(){
        switchToCombin();
        if(tiffPath==null){
            pickTiff();
        }
        else if(pickImagePath==null){
            pickImage();
        }
        else{
            listener.doSign();
        }
    }
    public void pickTiff(){
        step=0;
        Intent i = new Intent(context, FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
        fragment.startActivityForResult(i, FILE_CODE);

    }
    public void pickImage(){
        step=1;
        Intent i = new Intent(context, FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
        fragment.startActivityForResult(i, FILE_CODE);

    }
    public boolean saveImage(Bitmap bitmap, boolean isMultiple, TiffSaver.Orientation orientation) {
        String tempPath=tiffPath.substring(0,tiffPath.lastIndexOf("."))+"_temp.tif";
        String savedPath=isMultiple?tempPath:tiffPath;
        tempTiffPath=savedPath;
        resultTiffPath=tiffPath.substring(0,tiffPath.lastIndexOf("."))+"_result.tif";
        // String fname = "Upload.png";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        File saved_image_file = new File(savedPath);
        TiffSaver.SaveOptions options = new TiffSaver.SaveOptions();
//By default compression mode is none

        options.compressionMode = TiffSaver.CompressionMode.COMPRESSION_CCITTFAX3;
        options.orientation=orientation;
//Save image as tif. If image saved succesfull true will be returned
        return TiffSaver.saveBitmap(saved_image_file, bitmap, options, 130);

    }
    public String getTempTiffPath(){
        return tempTiffPath;
    }
    public String getResultTiffPath(){return resultTiffPath;}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if(currentMode.equals(MODE_IMAGE)) {
                if (step == 0) {
                    if (checkValidFormat(uri.getPath())) {
                        tiffPath = uri.getPath();
                        listener.setTiff();
                        //pickImage();

                    } else {
                        listener.PickFormatError();
                    }
                } else if (step == 1) {
                    if (checkImageFormat(uri.getPath())) {
                        step=2;
                        pickImagePath = uri.getPath();
                        listener.doCombin(pickImagePath);
                    } else {
                        listener.PickFormatError();
                    }
                }
            }
            else{
                if (checkValidFormat(uri.getPath())) {
                    tiffPath = uri.getPath();
                    listener.showTiff();
                    listener.onSelectSignPosition();

                } else {
                    listener.PickFormatError();
                }

            }
        }
    }
    public void init(){
        step=0;
        signPoint=null;
        pickImagePath=null;
    }
    private boolean checkValidFormat(String path) {
        String ext = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        return ext.equals("tif");
    }
    private boolean checkImageFormat(String path){
        String ext=path.substring(path.lastIndexOf(".")+1).toLowerCase();
        return supportFormats.contains(ext);
    }
    public void setSignPoint(PointF point){
        signPoint=point;
    }
    public String getCurrentMode(){
        return currentMode;
    }
    public PointF getSignPoint(){
        return signPoint;
    }
    public boolean isSignMode(){
        return currentMode.equals(MODE_SIGN);
    }
}
