package com.hydinin.base_module;

import android.app.Application;


import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.hydinin.base_module.utils.DisplayUtil;

/**
 * Created by Android Studio
 * Project：ZhiChuang
 * Author：httvc
 * Email：jfjie2013@163.com
 * Date：2018/3/25.
 */

public class BaseApplication extends Application {
    private static BaseApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        this.application = this;
        if (true) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化

        DisplayUtil.init(this);
        Stetho.initializeWithDefaults(this);
    }

    public static BaseApplication getInstance() {
        return application;
    }
}
