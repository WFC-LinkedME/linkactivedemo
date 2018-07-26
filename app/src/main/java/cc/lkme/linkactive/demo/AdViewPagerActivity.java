package cc.lkme.linkactive.demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cc.lkme.linkactive.R;
import cc.lkme.linkactive.callback.OnAdStatusListener;
import cc.lkme.linkactive.data.AdInfo;
import cc.lkme.linkactive.view.LMADContainer;

public class AdViewPagerActivity extends AppCompatActivity {


    private ViewPager mPager;
    private ViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.viewpager);
        mPagerAdapter = new ViewPagerAdapter();
        mPager.setAdapter(mPagerAdapter);
    }

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, AdViewPagerActivity.class);
        if (bundle != null) {
            starter.putExtras(bundle);
        }
        context.startActivity(starter);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;
            if (position == 2) {
                view = getLayoutInflater().inflate(R.layout.viewpager_ad, container, false);
                final LMADContainer lm_ad_container = view.findViewById(R.id.lm_ad_container);
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
            } else {
                view = getLayoutInflater().inflate(R.layout.viewpager_none, container, false);
            }
            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
