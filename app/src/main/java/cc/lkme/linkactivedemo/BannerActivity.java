package cc.lkme.linkactivedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import cc.lkme.linkactive.callback.OnAdStatusListener;
import cc.lkme.linkactive.data.AdInfo;
import cc.lkme.linkactive.view.LMBannerAdView;

public class BannerActivity extends AppCompatActivity {

    private static final String TAG = "BannerActivity";

    private LMBannerAdView lm_banner;
    private FrameLayout ad_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_banner);
        ad_container = findViewById(R.id.ad_container);
        lm_banner = new LMBannerAdView(this, "4000061_373");
        lm_banner.setOnAdStatusListener(new OnAdStatusListener() {
            @Override
            public void onClick(AdInfo adInfo) {
                super.onClick(adInfo);
                Log.i(TAG, "点击广告！");
            }

            @Override
            public void onExposure(AdInfo adInfo) {
                super.onExposure(adInfo);
                Log.i(TAG, "曝光广告！");
            }

            @Override
            public void onGetAd(boolean status) {
                super.onGetAd(status);
                if (status) {
                    Log.i(TAG, "有可展示广告！");
                } else {
                    Log.i(TAG, "无展示广告！");
                }
            }

            @Override
            public void onClose() {
                super.onClose();
                Log.i(TAG, "关闭广告！");
            }
        });
        // 设置轮播时间间隔，单位秒，可设置20到200秒
        lm_banner.setRefresh(5);
        // 设置是否显示关闭按钮，默认不显示
        lm_banner.setShowClose(true);
        // 添加视图
        ad_container.addView(lm_banner);
        // 开始加载广告
        lm_banner.loadAd();
    }

}
