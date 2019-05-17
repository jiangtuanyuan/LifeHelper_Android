package pers.life.helper.view.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pers.life.helper.R;
import pers.life.helper.utils.GlideRequestOptions;
import pers.life.helper.utils.PreImageViewPager;
import pers.life.helper.view.base.BaseActivity;

/**
 * 图片预览
 *
 * @author LiuTao
 */
public class PreviewImageActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    PreImageViewPager mViewPager;
    @BindView(R.id.tv_infos)
    TextView mTvinfos;

    private ArrayList<String> mMImages;
    private ArrayList<String> mInfos;
    private int mPosition;
    private PreviewPagerAdapter mPreviewPagerAdapter;

    public static void actionActivty(Context mContext, List<String> imageList, List<String> infos, int position) {
        Intent intent = new Intent(mContext, PreviewImageActivity.class);
        intent.putStringArrayListExtra("imageList", (ArrayList<String>) imageList);
        intent.putStringArrayListExtra("infos", (ArrayList<String>) infos);
        intent.putExtra("position", position);
        mContext.startActivity(intent);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initVariables() {
        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            mMImages = getIntent().getStringArrayListExtra("imageList");
            mInfos = getIntent().getStringArrayListExtra("infos");
            mPosition = intent.getIntExtra("position", 0);
            mPreviewPagerAdapter = new PreviewPagerAdapter(this, mMImages);
            mViewPager.setAdapter(mPreviewPagerAdapter);
            mViewPager.setCurrentItem(mPosition);
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbarNav();
        setTitle("图片预览(" + (mPosition + 1) + "/" + mMImages.size() + ")");
        mTvinfos.setText(mInfos.get(mPosition));
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int pos) {
                mPosition = pos;
                setTitle("图片预览(" + (mPosition + 1) + "/" + mMImages.size() + ")");
                mTvinfos.setText(mInfos.get(mPosition));
            }
        });
    }

    class PreviewPagerAdapter extends PagerAdapter {
        ArrayList<String> mImages;
        Context mContenxt;
        private int mChildCount = 0;

        public PreviewPagerAdapter(Context context, ArrayList<String> images) {
            this.mContenxt = context;
            this.mImages = images;
        }

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            try {//放大缩小旋转的
                PhotoView photoView = new PhotoView(mContenxt);
                photoView.enable();//启动图片缩放功能
                photoView.setMaxScale(8);
                Glide.with(mContenxt)
                        .load(mImages.get(position))
                        .apply(GlideRequestOptions.getGoodsImage())
                        .into(photoView);
                container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                return photoView;
            } catch (Exception e) {
                e.printStackTrace();
                //普通的ImageView
                ImageView imageView = new ImageView(mContenxt);
                Glide.with(mContenxt)
                        .load(mImages.get(position))
                        .apply(GlideRequestOptions.getGoodsImage())
                        .into(imageView);
                container.addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                return imageView;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
    }
}
