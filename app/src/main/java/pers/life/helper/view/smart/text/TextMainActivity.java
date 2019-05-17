package pers.life.helper.view.smart.text;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.utils.FileUtil;
import pers.life.helper.utils.LogUtil;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.smart.RecognizeService;
import pers.life.helper.view.smart.text.entity.CarLicenceBean;
import pers.life.helper.view.smart.text.entity.CarPlateBean;
import pers.life.helper.view.smart.text.entity.TextBean;

/**
 * 文本识别
 * 基于BaiDu-AI
 */
public class TextMainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_banck)
    TextView tvBanck;
    @BindView(R.id.tv_driving_licence)
    TextView tvDrivingLicence;
    @BindView(R.id.tv_car_plate)
    TextView tvCarPlate;
    @BindView(R.id.tv_init_msg)
    TextView tvInitMsg;
    private static final int REQUEST_CODE_GENERAL_BASIC = 106;
    private static final int REQUEST_CODE_BANKCARD = 111;
    private static final int REQUEST_CODE_DRIVING_LICENSE = 121;
    private static final int REQUEST_CODE_LICENSE_PLATE = 122;

    private boolean hasGotToken = false;//是否获取到Token
    private Gson gson;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance(this).release();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_text_main;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbarNav();
        setTitle("AI-文本识别");
        alertDialog = new AlertDialog.Builder(this);
        showProgressDialog("初始化数据中..", false);
        initAccessToken();
    }


    /**
     * 以license文件方式初始化
     * 加密
     */
    private void initAccessToken() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                hasGotToken = true;
                runOnUiThread(() -> {
                    //初始化成功
                    closeProgressDialog();
                    tvInitMsg.setVisibility(View.INVISIBLE);
                    tvText.setTextColor(getResources().getColor(R.color.main_color));
                    tvCard.setTextColor(getResources().getColor(R.color.main_color));
                    tvBanck.setTextColor(getResources().getColor(R.color.main_color));
                    tvDrivingLicence.setTextColor(getResources().getColor(R.color.main_color));
                    tvCarPlate.setTextColor(getResources().getColor(R.color.main_color));
                });
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                hasGotToken = false;
                runOnUiThread(() -> {
                    //初始化失败
                    closeProgressDialog();
                    ToastUtil.showToast("初始化数据失败！");
                    tvInitMsg.setVisibility(View.VISIBLE);
                    tvInitMsg.setText("数据初始化失败! \n ERROR_MSG=" + error.getMessage());
                    tvText.setTextColor(getResources().getColor(R.color.font_color));
                    tvCard.setTextColor(getResources().getColor(R.color.font_color));
                    tvBanck.setTextColor(getResources().getColor(R.color.font_color));
                    tvDrivingLicence.setTextColor(getResources().getColor(R.color.font_color));
                    tvCarPlate.setTextColor(getResources().getColor(R.color.font_color));
                });
            }
        }, getApplicationContext());
    }

    /**
     * 检测Token 是否初始化成功
     *
     * @return
     */
    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            ToastUtil.showToast("数据初始化失败,功能无法使用!");
        }
        return hasGotToken;
    }

    @OnClick({R.id.tv_text, R.id.tv_card, R.id.tv_banck, R.id.tv_driving_licence, R.id.tv_car_plate})
    public void onViewClicked(View view) {
        if (!checkTokenStatus()) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        switch (view.getId()) {
            case R.id.tv_text://文本
                intent.setClass(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
                break;
            case R.id.tv_card://身份证
                intent.setClass(this, IDCardActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_banck://银行卡
                intent.setClass(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, REQUEST_CODE_BANKCARD);
                break;
            case R.id.tv_driving_licence://驾驶证
                intent.setClass(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_DRIVING_LICENSE);
                break;
            case R.id.tv_car_plate://车牌
                intent.setClass(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_LICENSE_PLATE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String file_path = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
            showProgressDialog("识别中,请稍后..");
            switch (requestCode) {
                case REQUEST_CODE_GENERAL_BASIC://文本
                    RecognizeService.recGeneralBasic(this, file_path, result -> InfoPopText(result, 1));
                    break;
                case REQUEST_CODE_BANKCARD://银行卡
                    RecognizeService.recBankCard(this, file_path, result -> InfoPopText(result, 2));
                    break;
                case REQUEST_CODE_DRIVING_LICENSE://驾驶证
                    RecognizeService.recDrivingLicense(this, file_path, result -> InfoPopText(result, 3));
                    break;
                case REQUEST_CODE_LICENSE_PLATE://车牌
                    RecognizeService.recLicensePlate(this, file_path, result -> InfoPopText(result, 4));
                    break;
                default:
                    closeProgressDialog();
                    break;
            }
        }
    }

    private void InfoPopText(String result, int code) {
        LogUtil.e("tagaggas", result);
        if (gson == null) {
            gson = new Gson();
        }
        closeProgressDialog();
        StringBuffer stringBuffer = new StringBuffer();
        switch (code) {
            case 1://文本识别
                try {
                    TextBean mTextBean = gson.fromJson(result, TextBean.class);
                    for (TextBean.WordsResultBean str : mTextBean.getWords_result()) {
                        stringBuffer.append(str.getWords());
                        stringBuffer.append("\n");
                    }
                    stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());//去掉最后一个字符
                    ShowTextDialog("【文本识别结果】", stringBuffer.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showToast("识别失败,请重试!");
                }
                break;
            case 2://银行卡
                ShowTextDialog("【银行卡识别结果】", result);
                break;
            case 3://驾驶证
                try {
                    CarLicenceBean mCarLicenceBean = gson.fromJson(result, CarLicenceBean.class);
                    stringBuffer.append("姓名:" + mCarLicenceBean.getWords_result().get姓名().getWords());
                    stringBuffer.append("\n");
                    stringBuffer.append("性别:" + mCarLicenceBean.getWords_result().get性别().getWords());
                    stringBuffer.append("\n");
                    stringBuffer.append("出生日期:" + mCarLicenceBean.getWords_result().get出生日期().getWords());
                    stringBuffer.append("\n");
                    stringBuffer.append("国籍:" + mCarLicenceBean.getWords_result().get国籍().getWords());
                    stringBuffer.append("\n");
                    stringBuffer.append("住址:" + mCarLicenceBean.getWords_result().get住址().getWords());
                    stringBuffer.append("\n");

                    stringBuffer.append("证号:" + mCarLicenceBean.getWords_result().get证号().getWords());
                    stringBuffer.append("\n");
                    stringBuffer.append("准驾车型:" + mCarLicenceBean.getWords_result().get准驾车型().getWords());
                    stringBuffer.append("\n");
                    stringBuffer.append("有效期限:" + mCarLicenceBean.getWords_result().get有效期限().getWords());
                    stringBuffer.append("至");
                    stringBuffer.append(mCarLicenceBean.getWords_result().get至().getWords());
                    stringBuffer.append("\n");
                    stringBuffer.append("初次领证日期:" + mCarLicenceBean.getWords_result().get初次领证日期().getWords());
                    stringBuffer.append("\n");
                    ShowTextDialog("【驾驶证识别结果】", stringBuffer.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showToast("识别失败,请重试!");
                }
                break;
            case 4://车牌
                try {
                    CarPlateBean mCarPlateBean = gson.fromJson(result, CarPlateBean.class);
                    stringBuffer.append("车牌颜色:" + mCarPlateBean.getWords_result().getColor());
                    stringBuffer.append("\n");
                    stringBuffer.append("车牌号码:" + mCarPlateBean.getWords_result().getNumber());
                    stringBuffer.append("\n");
                    ShowTextDialog("【车牌识别结果】", stringBuffer.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showToast("识别失败,请重试!");
                }
                break;
            default:
                break;
        }
    }
    private void ShowTextDialog(final String title, final String message) {
        ClipboardManager cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        alertDialog.setTitle(title)
                .setMessage(message)
                .setNegativeButton("取消", null)
                .setPositiveButton("复制", (dialog, which) -> {
                    ClipData mClipData = ClipData.newPlainText("message", message);
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.showToast("复制成功！");
                })
                .show();
    }
}
