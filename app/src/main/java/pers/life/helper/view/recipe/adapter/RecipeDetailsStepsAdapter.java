package pers.life.helper.view.recipe.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import pers.life.helper.R;
import pers.life.helper.entity.RecipeEntity;
import pers.life.helper.utils.GlideRequestOptions;

public class RecipeDetailsStepsAdapter extends BaseQuickAdapter<RecipeEntity.StepsBean, BaseViewHolder> {
    public RecipeDetailsStepsAdapter(List<RecipeEntity.StepsBean> data) {
        super(R.layout.recipe_details_steps_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecipeEntity.StepsBean item) {
        helper.setText(R.id.tv_steps_number, item.getStep());
        Glide.with(mContext)
                .load(item.getImg())
                .apply(GlideRequestOptions.getGoodsImage())
                .into((ImageView) helper.getView(R.id.iv_image));
        helper.addOnClickListener(R.id.iv_image);
    }
}
