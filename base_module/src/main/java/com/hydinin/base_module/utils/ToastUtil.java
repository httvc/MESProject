package com.hydinin.base_module.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Android Studio
 * Project：ZhiChuangPda
 * Author：httvc
 * Email：jfjie2013@163.com
 * Date：2017/12/4.
 */

public class ToastUtil {
    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showDeBugMessage(String err) {
    }

    public static void err(Context context, int code, String msg) {
        Toast.makeText(context, code + ":" + msg, Toast.LENGTH_SHORT).show();
    }
}
