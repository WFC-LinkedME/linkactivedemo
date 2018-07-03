package cc.lkme.linkactive.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cc.lkme.linkactive.R;
import cc.lkme.linkactive.callback.OnAdStatusListener;
import cc.lkme.linkactive.data.AdInfo;
import cc.lkme.linkactive.view.LMADContainer;
import cc.lkme.linkactive.view.LMInterstitialAd;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final Button ad_banner = (Button) findViewById(R.id.ad_banner);
        ad_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BannerActivity.class));
            }
        });

        Button ad_interstitial = (Button) findViewById(R.id.ad_interstitial);
        ad_interstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LMInterstitialAd lmInterstitialAd = new LMInterstitialAd(MainActivity.this, "4000061_374");
                lmInterstitialAd.loadAd();
                lmInterstitialAd.setOnAdStatusListener(new OnAdStatusListener() {

                    @Override
                    public void onReady() {
                        super.onReady();
                        lmInterstitialAd.show();
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 调用该方法关闭插屏广告
                                lmInterstitialAd.closePopupWindow();
                            }
                        }, 3000);
                    }

                    @Override
                    public void onExposure(AdInfo adInfo) {
                        super.onExposure(adInfo);

                    }
                });
            }
        });

        Button ad_recyclerview = (Button) findViewById(R.id.ad_recyclerview);
        ad_recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdRecyclerViewActivity.start(MainActivity.this, null);
            }
        });
        Button ad_listview = (Button) findViewById(R.id.ad_listview);
        ad_listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdListViewActivity.start(MainActivity.this, null);

            }
        });

        final LMADContainer lm_ad_container = (LMADContainer) findViewById(R.id.lm_ad_container);
        Button get_ad = (Button) findViewById(R.id.get_ad);

        get_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 不采用广告视图，直接获取可显示的广告数据，自定义广告显示样式
                lm_ad_container.getAd("4000061_390", new OnAdStatusListener() {
                    @Override
                    public void onGetAd(boolean status, AdInfo adInfo) {
                        if (status) {
                            // 调用方法显示广告视图
                            lm_ad_container.setAdVisibility(true);
                            // 以下处理自定义视图展示
                            // 标题
                            adInfo.getTitle();
                            // 副标题
                            adInfo.getSub_title();
                            // 内容
                            adInfo.getContent();
                            // 图片列表
                            adInfo.getImgs();
                            // adInfo.getImgs().get(0) 获取到的是1280*720尺寸图片
                            Uri uri = Uri.parse(adInfo.getImgs().get(0));
                            SimpleDraweeView draweeView = findViewById(R.id.ad_img);
                            draweeView.setImageURI(uri);
                            // adInfo.getImgs().get(1) 获取到的是120*120尺寸的logo图片
                            Uri iconUri = Uri.parse(adInfo.getImgs().get(1));
                            SimpleDraweeView ad_icon = findViewById(R.id.ad_icon);
                            ad_icon.setImageURI(iconUri);
                            TextView title = findViewById(R.id.title);
                            title.setText(adInfo.getTitle());
                            TextView sub_title = findViewById(R.id.sub_title);
                            sub_title.setText(adInfo.getSub_title());

                        } else {
                            // 无广告，不展示
                        }

                    }
                });
            }
        });

        Button get_mini_ad = findViewById(R.id.get_mini_ad);
        get_mini_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 不采用广告视图，直接获取可显示的广告数据，自定义广告显示样式
                lm_ad_container.getAd("4000061_417", new OnAdStatusListener() {
                    @Override
                    public void onGetAd(boolean status, AdInfo adInfo) {
                        if (status) {
                            // 调用方法显示广告视图
                            lm_ad_container.setAdVisibility(true);
                            // 以下处理自定义视图展示
                            // 标题
                            adInfo.getTitle();
                            // 副标题
                            adInfo.getSub_title();
                            // 内容
                            adInfo.getContent();
                            // 图片列表
                            adInfo.getImgs();
                            // adInfo.getImgs().get(0) 获取到的是1280*720尺寸图片
                            Uri uri = Uri.parse(adInfo.getImgs().get(0));
                            SimpleDraweeView draweeView = findViewById(R.id.ad_img);
                            draweeView.setImageURI(uri);
                            // adInfo.getImgs().get(1) 获取到的是120*120尺寸的logo图片
                            Uri iconUri = Uri.parse(adInfo.getImgs().get(1));
                            SimpleDraweeView ad_icon = findViewById(R.id.ad_icon);
                            ad_icon.setImageURI(iconUri);
                            TextView title = findViewById(R.id.title);
                            title.setText(adInfo.getTitle());
                            TextView sub_title = findViewById(R.id.sub_title);
                            sub_title.setText(adInfo.getSub_title());

                        } else {
                            // 无广告，不展示
                        }

                    }
                });
            }
        });

    }

}
