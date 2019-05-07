package pers.life.helper.view.postcode;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import pers.life.helper.R;
import pers.life.helper.entity.PostcodeEntity;

public class PostcodeItemAdapter extends BaseQuickAdapter<PostcodeEntity, BaseViewHolder> {
    public PostcodeItemAdapter(List<PostcodeEntity> data) {
        super(R.layout.postcode_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PostcodeEntity item) {
        helper.setText(R.id.tv_PostNumber, item.getPostNumber())
                .setText(R.id.tv_Province, item.getProvince())
                .setText(R.id.tv_City, item.getCity())
                .setText(R.id.tv_District, item.getDistrict())
                .setText(R.id.tv_Address, item.getAddress())
                .setText(R.id.tv_number, (helper.getLayoutPosition() + 1) + "");
    }
}
