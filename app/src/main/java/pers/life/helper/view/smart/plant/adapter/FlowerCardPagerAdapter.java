package pers.life.helper.view.smart.plant.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pers.life.helper.R;
import pers.life.helper.utils.GlideRequestOptions;
import pers.life.helper.view.smart.plant.entity.PlantAnimalResult;

public class FlowerCardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<PlantAnimalResult.ResultBean> mData;
    private float mBaseElevation;
    private Context mContext;

    public FlowerCardPagerAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(PlantAnimalResult.ResultBean item) {
        mViews.add(null);
        mData.add(item);
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card_page_item, container, false);
        container.addView(view);
        CardView cardView = view.findViewById(R.id.cardView);
        bind(mData.get(position), view);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(PlantAnimalResult.ResultBean item, View view) {
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        titleTextView.setText(item.getName());
        TextView tvScore = view.findViewById(R.id.tvScore);
        DecimalFormat df = new DecimalFormat("0.00");
        String score = "匹配度：" + df.format(item.getScore() * 100) + "%";
        tvScore.setText(score);
        ImageView imageView = view.findViewById(R.id.image);
        if (!TextUtils.isEmpty(item.getBaike_info().getImage_url())) {
            Glide.with(mContext)
                    .load(item.getBaike_info().getImage_url())
                    .apply(GlideRequestOptions.getGoodsImage())
                    .into(imageView);
        } else {
            Glide.with(mContext)
                    .load("")
                    .apply(GlideRequestOptions.getGoodsImage())
                    .into(imageView);
        }
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        contentTextView.setText(item.getBaike_info().getDescription() == null ? "" : item.getBaike_info().getDescription());
    }

}
