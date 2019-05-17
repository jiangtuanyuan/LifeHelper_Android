package pers.life.helper.view.smart.animal;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import pers.life.helper.R;
import pers.life.helper.utils.AipImageUtils;
import pers.life.helper.utils.FileUtils;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.smart.plant.entity.PlantAnimalResult;
import pers.life.helper.view.smart.plant.adapter.FlowerCardPagerAdapter;
import pers.life.helper.view.smart.plant.view.ShadowTransformer;

public class AnimalMainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.ll_add_image)
    LinearLayout mLlAddImage;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<PlantAnimalResult.ResultBean> mSumList = new ArrayList<>();//总数据
    private ShadowTransformer mCardShadowTransformer;
    private FlowerCardPagerAdapter flowerCardPagerAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_animal_main;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbarNav();
        setTitle("动物识别");
    }

    @OnClick({R.id.ll_add_image, R.id.iv_image})
    public void onViewClicked() {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            CropImage.activity()
                                    .setAllowFlipping(false)
                                    .setActivityTitle("图片裁剪")
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .start(AnimalMainActivity.this);
                        } else {
                            ToastUtil.showToast("权限被拒绝,功能无法使用!");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE://图像裁剪成功
                    mLlAddImage.setVisibility(View.GONE);
                    mIvImage.setVisibility(View.VISIBLE);

                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    Glide.with(this)
                            .load(resultUri)
                            .into(mIvImage);
                    String path = FileUtils.getPhotoPathFromContentUri(this, resultUri);
                    tvProgress.setText("识别中..");
                    AipImageUtils.getInstance().AnimalDetect(path, 3, 5,
                            new AipImageUtils.OnResults() {
                                @Override
                                public void OnSuccessful(PlantAnimalResult result) {
                                    mSumList.clear();
                                    mSumList.addAll(result.getResult());
                                    tvProgress.setText("识别成功");
                                    flowerCardPagerAdapter = new FlowerCardPagerAdapter(AnimalMainActivity.this);
                                    for (PlantAnimalResult.ResultBean resultBean : result.getResult()) {
                                        flowerCardPagerAdapter.addCardItem(resultBean);
                                    }
                                    mCardShadowTransformer = new ShadowTransformer(mViewPager, flowerCardPagerAdapter);
                                    mCardShadowTransformer.enableScaling(true);
                                    mViewPager.setAdapter(flowerCardPagerAdapter);
                                    mViewPager.setPageTransformer(false, mCardShadowTransformer);
                                    mViewPager.setOffscreenPageLimit(result.getResult().size());

                                }

                                @Override
                                public void OnError(String erros) {
                                    tvProgress.setText("识别成失败");
                                }
                            }
                    );
                    break;
                default:
                    break;
            }
        }
        //图像裁剪错误
        if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            result.getError().printStackTrace();
            mLlAddImage.setVisibility(View.VISIBLE);
            mIvImage.setVisibility(View.GONE);
        }
    }
}