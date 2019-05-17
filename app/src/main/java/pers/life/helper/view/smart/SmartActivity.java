package pers.life.helper.view.smart;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.FileUtil;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.smart.animal.AnimalMainActivity;
import pers.life.helper.view.smart.plant.PlantMainActivity;
import pers.life.helper.view.smart.text.TextMainActivity;

public class SmartActivity extends BaseActivity {
    private static final int REQUEST_CODE_GENERAL_BASIC = 106;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_plant)
    TextView tvPlant;
    @BindView(R.id.tv_animal)
    TextView tvAnimal;

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
        AppUtils.checkPermissions(this);
    }

    @OnClick({R.id.tv_text, R.id.tv_plant, R.id.tv_animal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_text://文本
                startActivity(new Intent(this, TextMainActivity.class));
                break;
            case R.id.tv_plant://植物识别
                startActivity(new Intent(this, PlantMainActivity.class));
                break;
            case R.id.tv_animal://动物识别
                startActivity(new Intent(this, AnimalMainActivity.class));
                break;
            default:
                break;
        }
    }
}
