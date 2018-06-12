package cc.lkme.linkactivedemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import cc.lkme.linkactivedemo.dummy.DummyContent;

public class AdRecyclerViewActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_recycler_view);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_container, ItemFragment.newInstance(1)).commit();
    }

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, AdRecyclerViewActivity.class);
        if (bundle != null) {
            starter.putExtras(bundle);
        }
        context.startActivity(starter);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
