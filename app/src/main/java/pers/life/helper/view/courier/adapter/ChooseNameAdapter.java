package pers.life.helper.view.courier.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import pers.life.helper.R;
import pers.life.helper.entity.CouerierName;
import pers.life.helper.utils.ToastUtil;

public class ChooseNameAdapter extends BaseQuickAdapter<CouerierName, BaseViewHolder> {
    private int ischeckUser = 0;//没用选择用 0 或者 1

    public ChooseNameAdapter(List<CouerierName> data) {
        super(R.layout.coueriers_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouerierName item) {
        helper.setText(R.id.user_name, item.getCom())
                .setText(R.id.user_account, item.getNo())
                .setChecked(R.id.checkbox, item.isIscheck());
        helper.itemView.setOnClickListener(v -> {
            try {
                if (item.isIscheck()) {
                    ischeckUser = 0;
                    helper.setChecked(R.id.checkbox, false);
                    item.setIscheck(false);
                } else {
                    if (ischeckUser == 1) {
                        ToastUtil.showToast("只能选择一个用户!");
                    } else {
                        ischeckUser = 1;
                        helper.setChecked(R.id.checkbox, true);
                        item.setIscheck(true);
                    }
                }
                mItemClickListener.onItemClick(helper.getLayoutPosition(), item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private ItemClickListener mItemClickListener;

    public void setmItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, CouerierName item);
    }
}
