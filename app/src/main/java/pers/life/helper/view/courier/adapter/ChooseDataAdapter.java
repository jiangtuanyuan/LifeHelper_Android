package pers.life.helper.view.courier.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import pers.life.helper.R;
import pers.life.helper.entity.CouerierData;

public class ChooseDataAdapter extends BaseQuickAdapter<CouerierData.ListBean, BaseViewHolder> {
    public ChooseDataAdapter(List<CouerierData.ListBean> data) {
        super(R.layout.coueriers_data_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouerierData.ListBean item) {
        helper.setText(R.id.tv_time, "时间:" + item.getDatetime())
                .setText(R.id.tv_status, "状态:" + item.getRemark());
    }
}
