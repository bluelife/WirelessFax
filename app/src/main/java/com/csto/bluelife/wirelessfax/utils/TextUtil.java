package com.csto.bluelife.wirelessfax.utils;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by slomka.jin on 2016/11/4.
 */

public class TextUtil {
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }
}
