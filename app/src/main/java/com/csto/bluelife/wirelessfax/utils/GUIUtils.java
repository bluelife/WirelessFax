package com.csto.bluelife.wirelessfax.utils;

/**
 * Created by HiWin10 on 2016/11/6.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.csto.bluelife.wirelessfax.OnRevealAnimationListener;
import com.csto.bluelife.wirelessfax.R;


public class GUIUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealHide(final Context ctx, final View view, @ColorRes final int color,
                                         final int finalRadius, final OnRevealAnimationListener listener) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        int initialRadius = view.getWidth();

        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, finalRadius);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setBackgroundColor(ctx.getResources().getColor(color));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                listener.onRevealHide();
                view.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(300);
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealShow(final Context ctx, final View view, final int startRadius,
                                         @ColorRes final int color, int x, int y, final OnRevealAnimationListener listener) {
        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());

        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, finalRadius);
        anim.setDuration(300);
        anim.setStartDelay(100);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setBackgroundColor(ctx.getResources().getColor(color));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
                listener.onRevealShow();
            }
        });
        anim.start();
    }





    private static Animation.AnimationListener getGoneAnimationListener(final OnReturnAnimationFinished listener, final View... views) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for(View v : views) {
                    v.setVisibility(View.INVISIBLE);
                }
                if(listener != null) {
                    listener.onAnimationFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private static Animation.AnimationListener getShowAnimationListener(final OnReturnAnimationFinished listener, final View... views) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for(View v : views) {
                    v.setVisibility(View.VISIBLE);
                }
                if(listener != null) {
                    listener.onAnimationFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private static void startAnimations(Animation animation, View... views) {
        for(View v : views) {
            v.startAnimation(animation);
        }
    }

    public interface OnReturnAnimationFinished {
        void onAnimationFinished();
    }
}
