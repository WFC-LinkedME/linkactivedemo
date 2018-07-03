package cc.lkme.linkactive.demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cc.lkme.linkactive.LinkedME;
import cc.lkme.linkactive.R;
import cc.lkme.linkactive.callback.OnAdStatusListener;
import cc.lkme.linkactive.data.AdInfo;
import cc.lkme.linkactive.demo.dummy.DummyContent;
import cc.lkme.linkactive.view.LMADContainer;

import static cc.lkme.linkactive.demo.dummy.DummyContent.itemViewTypeAd;
import static cc.lkme.linkactive.demo.dummy.DummyContent.itemViewTypeNormal;

public class AdListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_list_view);
        ListView ad_listview = findViewById(R.id.ad_listview);
        ad_listview.setAdapter(new ListViewAdapter(this, DummyContent.ITEMS));
        ad_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AdListViewActivity.this, DummyContent.ITEMS.get(i).content, Toast.LENGTH_SHORT).show();
            }
        });
        ad_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                Log.i("LinkedME", "ListView onScrollStateChanged >>> ");
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                Log.i("LinkedME", "ListView onScroll >>> ");
            }
        });
    }

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, AdListViewActivity.class);
        if (bundle != null) {
            starter.putExtras(bundle);
        }
        context.startActivity(starter);
    }

    static class ListViewAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<DummyContent.DummyItem> ITEMS;

        public ListViewAdapter(Context mContext, List<DummyContent.DummyItem> ITEMS) {
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.ITEMS = ITEMS;
        }

        @Override
        public int getCount() {
            return ITEMS.size();
        }

        @Override
        public Object getItem(int i) {
            return ITEMS.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 2 || position == 28) {
                return itemViewTypeAd;
            }
            return itemViewTypeNormal;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                if (getItemViewType(i) == itemViewTypeAd) {
                    view = mInflater.inflate(R.layout.fragment_item_ad, viewGroup, false);
                    holder.lm_ad_container = view.findViewById(R.id.lm_ad_container);
                    holder.ad_icon = view.findViewById(R.id.ad_icon);
                    holder.ad_img = view.findViewById(R.id.ad_img);
                    holder.title = view.findViewById(R.id.title);
                    holder.sub_title = view.findViewById(R.id.sub_title);
                } else {
                    view = mInflater.inflate(R.layout.fragment_item, viewGroup, false);
                    holder.mIdView = view.findViewById(R.id.id);
                    holder.mContentView = view.findViewById(R.id.content);
                }
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (getItemViewType(i) == itemViewTypeAd) {
                holder.lm_ad_container.getAd("4000061_390", new OnAdStatusListener() {
                    @Override
                    public void onGetAd(boolean status, AdInfo adInfo) {
                        if (status) {
                            Log.i(LinkedME.TAG, "存在匹配广告");
                            if (status) {
                                // 调用该方法显示广告
                                holder.lm_ad_container.setAdVisibility(true);
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
                                holder.ad_img.setImageURI(uri);
                                // adInfo.getImgs().get(1) 获取到的是120*120尺寸的logo图片
                                Uri iconUri = Uri.parse(adInfo.getImgs().get(1));
                                holder.ad_icon.setImageURI(iconUri);
                                holder.title.setText(adInfo.getTitle());
                                holder.sub_title.setText(adInfo.getSub_title());
                            } else {
                                // 无广告，不展示
                            }
                        } else {
                            Log.i(LinkedME.TAG, "无匹配广告");
                        }
                    }

                    @Override
                    public void onClose() {
                        Log.i(LinkedME.TAG, "Floating Activity 广告被关闭 ");
                    }

                    @Override
                    public void onClick(AdInfo adInfo) {
                        Log.i(LinkedME.TAG, "Floating Activiy 广告被点击");

                    }
                });
            } else {
                holder.mIdView.setText(((DummyContent.DummyItem) getItem(i)).id);
                holder.mContentView.setText(((DummyContent.DummyItem) getItem(i)).content);
            }
            return view;
        }

        final static class ViewHolder {
            private TextView mIdView;
            private TextView mContentView;
            private LMADContainer lm_ad_container;
            private SimpleDraweeView ad_icon;
            private SimpleDraweeView ad_img;
            private TextView title;
            private TextView sub_title;
        }

    }
}