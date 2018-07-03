package cc.lkme.linkactive.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import cc.lkme.linkactive.R;
import cc.lkme.linkactive.callback.OnSplashAdListener;
import cc.lkme.linkactive.data.AdInfo;
import cc.lkme.linkactive.view.LMSplashAd;

/**
 * Created by LinkedME06 on 3/23/18.
 */

public class SplashActivity extends Activity implements OnSplashAdListener {

    private LMSplashAd lmSplashAd;
    private ViewGroup container;
    private TextView skip;
    private FrameLayout launch_default;
    private static final String SKIP_TEXT = "跳过 %d";
    // 标识是否可以跳到app主页面
    private boolean canJump = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        // 因广告请求需要imei号，请先获取后再请求广告！！！！！！！！！！！！！
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }
        setContentView(R.layout.activity_splash);
        container = this.findViewById(R.id.splash_container);
        skip = findViewById(R.id.skip);
        launch_default = findViewById(R.id.launch_default);
        lmSplashAd = new LMSplashAd(this, "4000061_375", container, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump) {
            gotoMain();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(AdInfo adInfo) {
        Log.i("LinkedME", "开屏广告被点击！");
        canJump = true;
    }

    @Override
    public void onGetAd(boolean status) {
        if (status) {
            Log.i("LinkedME", "开屏广告有可展示广告！");
        } else {
            Log.i("LinkedME", "开屏广告无可展示广告！");
            gotoMain();
        }
    }

    @Override
    public void onClose() {
        Log.i("LinkedME", "开屏广告被关闭！");
        gotoMain();
    }

    @Override
    public void onExposure(AdInfo adInfo) {
        Log.i("LinkedME", "开屏广告曝光！");
    }

    @Override
    public void onReady() {
        Log.i("LinkedME", "开屏广告已准备就绪，可以显示！");
        // 开屏广告准备就绪后，需要将启动欢迎页面隐藏掉以显示开屏广告
        launch_default.setVisibility(View.GONE);
    }

    @Override
    public void onTick(long millis) {
        Log.i("LinkedME", "开屏广告倒计时: " + Math.round(millis / 1000f));
        skip.setText(String.format(SKIP_TEXT, Math.round(millis / 1000f)));
    }

    private void gotoMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // 禁止用户在开屏状态下点击返回按钮，否则将导致无法正常曝光
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
