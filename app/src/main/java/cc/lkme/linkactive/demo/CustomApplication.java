package cc.lkme.linkactive.demo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import cc.lkme.linkactive.BuildConfig;
import cc.lkme.linkactive.LinkedME;

/**
 * Created by LinkedME06 on 17/05/2017.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LinkedME.getLinkActiveInstance(this, "11cb5514641d67ea6c65836b07541d3b");
        if (BuildConfig.DEBUG) {
            // 该设置有两个作用：
            // 1. 打印LinkedME日志
            // 2. 在此模式下，无论是否是沉默用户都会返回广告
            LinkedME.getLinkActiveInstance().setDebug();
        }
        Fresco.initialize(this);
    }
}
