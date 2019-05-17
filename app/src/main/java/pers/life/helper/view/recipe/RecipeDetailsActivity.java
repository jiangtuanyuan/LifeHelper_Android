package pers.life.helper.view.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pers.life.helper.R;
import pers.life.helper.entity.RecipeEntity;
import pers.life.helper.utils.GlideRequestOptions;
import pers.life.helper.utils.RecyclerViewImplementsContextMenu;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.image.PreviewImageActivity;
import pers.life.helper.view.recipe.adapter.RecipeDetailsStepsAdapter;

public class RecipeDetailsActivity extends BaseActivity {
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tags)
    TextView tvTags;
    @BindView(R.id.tv_imtro)
    TextView tvImtro;
    @BindView(R.id.tv_burden)
    TextView tvBurden;
    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;
    @BindView(R.id.recycler_view)
    RecyclerViewImplementsContextMenu recyclerView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private RecipeEntity mRecipeEntity;


    private RecipeDetailsStepsAdapter mRecipeDetailsStepsAdapter;
    private List<RecipeEntity.StepsBean> mSumList = new ArrayList<>();
    private List<String> mImages = new ArrayList<>();
    private List<String> mInfos = new ArrayList<>();

    public static void actionActivity(Context mContext, RecipeEntity mRecipeEntity) {
        Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
        intent.putExtra("mRecipeEntity", mRecipeEntity);
        mContext.startActivity(intent);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_recipe_details;
    }

    @Override
    protected void initVariables() {
        mRecipeEntity = (RecipeEntity) getIntent().getSerializableExtra("mRecipeEntity");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbarNav();
        if (mRecipeEntity != null) {
            initView();
        } else {
            ToastUtil.showToast("数据异常!");
            finish();
        }
    }

    private void initView() {
        for (RecipeEntity.StepsBean stepsBean : mRecipeEntity.getSteps()) {
            mImages.add(stepsBean.getImg());
            mInfos.add(stepsBean.getStep());
        }

        setTitle(mRecipeEntity.getTitle());
        Glide.with(this)
                .load(mRecipeEntity.getAlbums().size() == 0 ? "" : mRecipeEntity.getAlbums().get(0))
                .apply(GlideRequestOptions.getGoodsImage())
                .into(ivImage);
        tvTitle.setText("菜名:" + mRecipeEntity.getTitle());
        tvTags.setText("标签:" + mRecipeEntity.getTags());
        tvImtro.setText("点评:" + mRecipeEntity.getImtro());
        tvBurden.setText("配料:" + mRecipeEntity.getBurden());
        tvIngredients.setText("材料:" + mRecipeEntity.getIngredients());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mRecipeDetailsStepsAdapter = new RecipeDetailsStepsAdapter(mSumList);
        mRecipeDetailsStepsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_image) {
                PreviewImageActivity.actionActivty(view.getContext(), mImages, mInfos, position);
            }
        });
        recyclerView.setAdapter(mRecipeDetailsStepsAdapter);
        mSumList.clear();
        mSumList.addAll(mRecipeEntity.getSteps());
        mRecipeDetailsStepsAdapter.notifyDataSetChanged();
        scrollView.scrollTo(0, 0);//滑动到顶部
    }
}
