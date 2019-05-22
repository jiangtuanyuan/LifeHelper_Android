package pers.life.helper.view.smart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.entity.Quanxian;
import pers.life.helper.net.API;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.smart.animal.AnimalMainActivity;
import pers.life.helper.view.smart.car.CarMainActivity;
import pers.life.helper.view.smart.plant.PlantMainActivity;
import pers.life.helper.view.smart.text.TextMainActivity;

public class SmartActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_plant)
    TextView tvPlant;
    @BindView(R.id.tv_animal)
    TextView tvAnimal;
    @BindView(R.id.tv_car)
    TextView tvCar;
    private Quanxian mQuanxian;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_smart;
    }

    @Override
    protected void initVariables() {

    }
    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbarNav();
        setTitle("智能识别");
        //重新获取相机权限
        showProgressDialog("加载中..");
        getQuanxian();
        AppUtils.checkPermissions(this);
    }

    @OnClick({R.id.tv_text, R.id.tv_plant, R.id.tv_animal,R.id.tv_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_text://文本
                if (mQuanxian == null) {
                    ToastUtil.showToast("请等待加载完成..");
                } else {
                    if (mQuanxian.getText() == 1) {
                        startActivity(new Intent(this, TextMainActivity.class));
                    } else {
                        ToastUtil.showToast("权限拒绝!");
                    }
                }
                break;
            case R.id.tv_plant://植物识别
                if (mQuanxian == null) {
                    ToastUtil.showToast("请等待加载完成..");
                } else {
                    if (mQuanxian.getPlant() == 1) {
                        startActivity(new Intent(this, PlantMainActivity.class));
                    } else {
                        ToastUtil.showToast("权限拒绝!");
                    }
                }
                break;
            case R.id.tv_animal://动物识别
                if (mQuanxian == null) {
                    ToastUtil.showToast("请等待加载完成..");
                } else {
                    if (mQuanxian.getAnima() == 1) {
                        startActivity(new Intent(this, AnimalMainActivity.class));
                    } else {
                        ToastUtil.showToast("权限拒绝!");
                    }
                }
                break;
            case R.id.tv_car://车辆识别
                if (mQuanxian == null) {
                    ToastUtil.showToast("请等待加载完成..");
                } else {
                    if (mQuanxian.getCar() == 1) {
                        startActivity(new Intent(this, CarMainActivity.class));
                    } else {
                        ToastUtil.showToast("权限拒绝!");
                    }
                }
                break;
            default:
                break;
        }
    }

    private void getQuanxian() {
        OkGo.<String>get(API.APP_QUANXIAN)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        closeProgressDialog();
                        try {
                            Gson gson = new Gson();
                            mQuanxian = gson.fromJson(response.body(), Quanxian.class);
                            if (mQuanxian.getText() == 1) {
                                tvText.setTextColor(getResources().getColor(R.color.main_color));
                            } else {
                                tvText.setTextColor(getResources().getColor(R.color.font_color));
                            }

                            if (mQuanxian.getPlant() == 1) {
                                tvPlant.setTextColor(getResources().getColor(R.color.main_color));
                            } else {
                                tvPlant.setTextColor(getResources().getColor(R.color.font_color));
                            }

                            if (mQuanxian.getAnima() == 1) {
                                tvAnimal.setTextColor(getResources().getColor(R.color.main_color));
                            } else {
                                tvAnimal.setTextColor(getResources().getColor(R.color.font_color));
                            }

                        } catch (Exception s) {
                            s.printStackTrace();
                            mQuanxian = null;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        closeProgressDialog();
                    }
                });

    }

}
