package com.csto.bluelife.wirelessfax;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.csto.bluelife.wirelessfax.utils.TextUtil;

/**
 * Created by HiWin10 on 2016/11/3.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textView = (TextView) findViewById(R.id.login_forget);
        textView.setText(TextUtil.fromHtml(getString(R.string.login_forget_label)));

    }


}
