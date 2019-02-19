package com.hydinin.base_module.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 屏幕大小、距离单位转换工具类
 *
 * @author httvc
 */
public class DisplayUtil {
    private static DisplayMetrics metrics;

    public static void init(Application application) {
        if (metrics == null) {
            metrics = application.getResources().getDisplayMetrics();
        }
    }

    public static int getScreenHeight() {
        checkInit();
        return metrics.heightPixels;
    }

    public static int getScreenWidth() {
        checkInit();
        return metrics.widthPixels;
    }

    /**
     * 根据绝对尺寸得到相对尺寸，在不同的分辨率设备上有一致的显示效果, dip->pix
     *
     * @param givenAbsSize 绝对尺寸
     * @return
     */
    public static int getSizeByGivenAbsSize(int givenAbsSize) {
        checkInit();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                givenAbsSize, metrics);
    }

    /**
     * px to sp
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        checkInit();
        return (int) (pxValue / metrics.scaledDensity + 0.5f);
    }

    /**98
     * sp to px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        checkInit();
        return (int) (spValue * metrics.scaledDensity + 0.5f);
    }

    /**
     * px to dip
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        checkInit();
        return (int) (pxValue / metrics.density + 0.5f);
    }

    /**
     * dip to px
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        checkInit();
        return (int) (dipValue * metrics.density + 0.5f);
    }

    private static void checkInit() {
        if (metrics == null) {
            throw new NullPointerException("Place init DisplayUtil");
        }
    }

    public static void getScreenRect(Context ctx_, Rect outrect_) {
        Display screenSize = ((WindowManager) ctx_.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        outrect_.set(0, 0, screenSize.getWidth(), screenSize.getHeight());
    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        statusHeight = rect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object object = localClass.newInstance();
                int height = Integer.parseInt(localClass.getField("status_bar_height").get(object).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 隐藏键盘
     *
     * @param activity
     */
    public static void hideSoftInput(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private static final int StatusBarColor = android.R.color.transparent;

    @SuppressLint("NewApi")
    public static void initSystemBar(Activity activity) {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//			activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//		}
        if (android.os.Build.VERSION.SDK_INT > 20) {
            Window window = activity.getWindow();
            // 设置透明状态栏,这样才能让 ContentView 向上
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 设置状态栏颜色
            window.setStatusBarColor(activity.getColor(StatusBarColor));

            ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                // 注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView
                // 的第一个子 View . 使其不为系统 View 预留空间.
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }
        } else {
            Window window = activity.getWindow();
            ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
            // 首先使 ChildView 不预留空间
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }
            int statusBarHeight = getStatusBarHeight(activity);
            // 需要设置这个 flag 才能设置状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 避免多次调用该方法时,多次移除了 View
            if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
                // 移除假的 View.
                mContentView.removeView(mChildView);
                mChildView = mContentView.getChildAt(0);
            }
            if (mChildView != null) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                // 清除 ChildView 的 marginTop 属性
                if (lp != null && lp.topMargin >= statusBarHeight) {
                    lp.topMargin -= statusBarHeight;
                    mChildView.setLayoutParams(lp);
                }
            }
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param context
     * @param fontSize
     * @return
     * @category 通过字符大小获取单个字符的Px
     */
    public static float getWidthFontSize(Context context, int fontSize) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(fontSize);
        textView.setText("单");
        return getcharacterWidth(textView);
    }

    /**
     * @param text
     * @return
     * @category 获取文本字符串的像素大小
     */
    public static float getcharacterWidth(TextView text) {
        if (null == text || "".equals(text)) {
            return 0f;
        }
        Paint paint = new Paint();
        paint.setTextSize(text.getTextSize() * text.getTextScaleX());
        float text_width = paint.measureText(text.getText().toString());
        return text_width;
    }

}