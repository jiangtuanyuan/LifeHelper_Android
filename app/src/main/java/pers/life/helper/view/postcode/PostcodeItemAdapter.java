package pers.life.helper.view.postcode;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pers.life.helper.R;
import pers.life.helper.entity.PostcodeEntity;


/**
 * Created by 蒋 on 2018/9/22.
 * 学生列表适配器
 */

public class PostcodeItemAdapter extends RecyclerView.Adapter<PostcodeItemAdapter.ViewHolder> {
    private Context mContext;
    private List<PostcodeEntity> mList;

    public PostcodeItemAdapter(Context context, List<PostcodeEntity> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.postcode_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PostcodeEntity entity = mList.get(position);
        holder.tvPostNumber.setText(entity.getPostNumber());

        holder.tvProvince.setText(entity.getProvince());

        holder.tvCity.setText(entity.getCity());

        holder.tvDistrict.setText(entity.getDistrict());

        holder.tvAddress.setText(entity.getAddress());

        holder.tvNumber.setText((position + 1) + "");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_PostNumber)
        TextView tvPostNumber;
        @BindView(R.id.tv_Province)
        TextView tvProvince;
        @BindView(R.id.tv_City)
        TextView tvCity;
        @BindView(R.id.tv_District)
        TextView tvDistrict;
        @BindView(R.id.tv_Address)
        TextView tvAddress;
        @BindView(R.id.tv_number)
        TextView tvNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
