package cc.lkme.linkactivedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cc.lkme.linkactive.callback.OnAdStatusListener;
import cc.lkme.linkactive.view.LMImageView;

/**
 * Created by LinkedME06 on 15/01/2017.
 */

public class LinkActiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        LMImageView ad_one = (LMImageView) findViewById(R.id.ad_one);
        ad_one.getAdWithFrame("8005", new OnAdStatusListener() {
            @Override
            public void onGetAd(boolean status) {
                Toast.makeText(LinkActiveActivity.this, status + "", Toast.LENGTH_SHORT).show();
            }
        });
//        LMImageView ad_two = (LMImageView) findViewById(R.id.ad_two);
//        ad_two.setAdId("8001");
//        LMImageView ad_three = (LMImageView) findViewById(R.id.ad_three);
//        ad_three.setAdId("8002");
//        LMImageView ad_four = (LMImageView) findViewById(R.id.ad_four);
//        ad_four.setAdId("8003");
//        LMImageView ad_five = (LMImageView) findViewById(R.id.ad_five);
//        ad_five.setAdId("8004");
    }
}
