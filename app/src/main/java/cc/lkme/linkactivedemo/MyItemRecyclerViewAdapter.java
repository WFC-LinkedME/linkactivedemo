package cc.lkme.linkactivedemo;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cc.lkme.linkactive.LinkedME;
import cc.lkme.linkactive.callback.OnAdStatusListener;
import cc.lkme.linkactive.data.AdInfo;
import cc.lkme.linkactive.view.LMADContainer;
import cc.lkme.linkactivedemo.dummy.DummyContent;

import static cc.lkme.linkactivedemo.dummy.DummyContent.itemViewTypeAd;
import static cc.lkme.linkactivedemo.dummy.DummyContent.itemViewTypeNormal;


/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyContent.DummyItem} and makes a call to the
 * specified {@link ItemFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyContent.DummyItem> mValues;
    private final ItemFragment.OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    public MyItemRecyclerViewAdapter(RecyclerView recyclerView, List<DummyContent.DummyItem> items, ItemFragment.OnListFragmentInteractionListener listener) {
        this.recyclerView = recyclerView;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (viewType == itemViewTypeAd) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item_ad, parent, false);
            return new ViewHolderAd(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case itemViewTypeAd:
                final ViewHolderAd viewHolderAd = (ViewHolderAd) holder;
                viewHolderAd.lm_ad_container.getAd("4000061_390", new OnAdStatusListener() {
                    @Override
                    public void onGetAd(boolean status, AdInfo adInfo) {
                        if (status) {
                            Log.i(LinkedME.TAG, "存在匹配广告");
                            if (status) {
                                // 调用该方法显示广告
                                viewHolderAd.lm_ad_container.setAdVisibility(true);
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
                                viewHolderAd.ad_img.setImageURI(uri);
                                // adInfo.getImgs().get(1) 获取到的是120*120尺寸的logo图片
                                Uri iconUri = Uri.parse(adInfo.getImgs().get(1));
                                viewHolderAd.ad_icon.setImageURI(iconUri);
                                viewHolderAd.title.setText(adInfo.getTitle());
                                viewHolderAd.sub_title.setText(adInfo.getSub_title());
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
                break;

            case itemViewTypeNormal:
                ViewHolder viewHolder = (ViewHolder) holder;
                holder.mItem = mValues.get(position);
                holder.mIdView.setText(mValues.get(position).id);
                holder.mContentView.setText(mValues.get(position).content);

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mListener.onListFragmentInteraction(holder.mItem);
                            Toast.makeText(holder.mView.getContext(), mValues.get(position).content, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyContent.DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public class ViewHolderAd extends ViewHolder {
        public final LMADContainer lm_ad_container;
        public final SimpleDraweeView ad_icon;
        public final SimpleDraweeView ad_img;
        public final TextView title;
        public final TextView sub_title;

        public ViewHolderAd(View view) {
            super(view);
            lm_ad_container = view.findViewById(R.id.lm_ad_container);
            ad_icon = view.findViewById(R.id.ad_icon);
            ad_img = view.findViewById(R.id.ad_img);
            title = view.findViewById(R.id.title);
            sub_title = view.findViewById(R.id.sub_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2 || position == 28) {
            return itemViewTypeAd;
        }
        return itemViewTypeNormal;
    }
}
