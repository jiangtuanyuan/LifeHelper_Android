package pers.life.helper.view.recipe.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import pers.life.helper.R;
import pers.life.helper.entity.RecipeEntity;
import pers.life.helper.utils.GlideRequestOptions;
import pers.life.helper.view.recipe.RecipeDetailsActivity;

public class RecipeLiseAdapter extends BaseQuickAdapter<RecipeEntity, BaseViewHolder> {
    public RecipeLiseAdapter(List<RecipeEntity> data) {
        super(R.layout.recipe_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecipeEntity item) {
        Glide.with(mContext)
                .load(item.getAlbums().size() == 0 ? "" : item.getAlbums().get(0))
                .apply(GlideRequestOptions.getGoodsImage())
                .into((ImageView) helper.getView(R.id.iv_image));
        helper.setText(R.id.tv_title, "菜名:" + item.getTitle())
                .setText(R.id.tv_tags, "标签:" + item.getTags())
                .setText(R.id.tv_ingredients, "材料:" + item.getIngredients());
        helper.itemView.setOnClickListener(v -> RecipeDetailsActivity.actionActivity(mContext, item));
    }
}
