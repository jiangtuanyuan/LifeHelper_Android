package pers.life.helper.view.dukeofzhou;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import pers.life.helper.R;
import pers.life.helper.entity.ZhouGongEntity;

public class ZhouGongLiseAdapter extends BaseQuickAdapter<ZhouGongEntity, BaseViewHolder> {
    public ZhouGongLiseAdapter(List<ZhouGongEntity> data) {
        super(R.layout.zhougong_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhouGongEntity item) {
        helper.setText(R.id.tv_title, "标签:" + item.getTitle())
                .setText(R.id.tv_dec, "解梦:" + item.getDes());
    }
}
