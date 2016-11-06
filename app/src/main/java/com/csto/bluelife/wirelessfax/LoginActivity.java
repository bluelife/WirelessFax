package com.csto.bluelife.wirelessfax;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.csto.bluelife.wirelessfax.utils.TextUtil;
import com.csto.bluelife.wirelessfax.utils.TransitionHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HiWin10 on 2016/11/3.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        TextView textView = (TextView) findViewById(R.id.login_forget);
        textView.setText(TextUtil.fromHtml(getString(R.string.login_forget_label)));

    }

    @OnClick(R.id.login_submit)
    void loginIn(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

        finish();
    }


}
