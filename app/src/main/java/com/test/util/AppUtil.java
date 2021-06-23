package com.test.util;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.test.R;

public class AppUtil {

    public static void clearTransStatusBar(AppCompatActivity activity) {
        if(Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            clearWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }

        if(Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

        if(Build.VERSION.SDK_INT >= 21) {
            clearWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.purple_700));
        }
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void clearWindowFlag(AppCompatActivity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        win.clearFlags(bits);
    }
}
