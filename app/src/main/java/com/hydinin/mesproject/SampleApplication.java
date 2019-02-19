package com.hydinin.mesproject;

import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.hydinin.base_module.utils.DisplayUtil;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.hydinin.mesproject.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
