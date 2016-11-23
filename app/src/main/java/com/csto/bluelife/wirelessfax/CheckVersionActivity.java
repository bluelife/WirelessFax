package com.csto.bluelife.wirelessfax;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.objects.Update;

/**
 * Created by slomka.jin on 2016/11/23.
 */

public class CheckVersionActivity extends AppCompatActivity {
    private ViewGroup progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_version);
        progressBar=(ViewGroup)findViewById(R.id.check_version_progress);
        checkUpdate();
    }

    private void checkUpdate(){
        /*CustomUpdater appUpdater= (CustomUpdater) new CustomUpdater(this)
                .setTitleOnUpdateAvailable("Update available")
                .setContentOnUpdateAvailable("Check out the latest version available of my app!")
                .setTitleOnUpdateNotAvailable("Update not available")
                .setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
                .setButtonUpdate("Update now?")
                .setButtonDismiss("Maybe later")
                .showAppUpdated(true);
        appUpdater.setListener(this);
        appUpdater.start();*/
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                //.setUpdateFrom(UpdateFrom.AMAZON)
                //.setUpdateFrom(UpdateFrom.GITHUB)
                //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                //...
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean hasUpdate) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent;
                        if(hasUpdate){
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(update.getUrlToDownload().toString()));
                                startActivity(intent);
                            }
                            finally {
                                finish();
                            }
                        }
                        else{
                            goMain();
                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError appUpdaterError) {
                        progressBar.setVisibility(View.GONE);
                        goMain();
                    }
                });
        appUpdaterUtils.start();

    }

    private void goMain() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
