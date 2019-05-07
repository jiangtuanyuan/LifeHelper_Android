package pers.life.helper.view.phone;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.entity.PhoneEntity;
import pers.life.helper.net.API;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;

public class QueryPhoneActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.tv_query_status)
    TextView tvQueryStatus;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_areacode)
    TextView tvAreacode;
    @BindView(R.id.tv_zip)
    TextView tvZip;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_card)
    TextView tvCard;

    private PhoneEntity mPhoneEntity;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_query_phone;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("号码归属地查询");
        initToolbarNav();
    }

    /**
     * 出错
     */
    private void initError(String s) {
        tvQueryStatus.setText(s);
        tvQueryStatus.setTextColor(getResources().getColor(R.color.red));

        tvProvince.setText("无");
        tvProvince.setTextColor(getResources().getColor(R.color.red));

        tvCity.setText("无");
        tvCity.setTextColor(getResources().getColor(R.color.red));

        tvAreacode.setText("无");
        tvAreacode.setTextColor(getResources().getColor(R.color.red));

        tvZip.setText("无");
        tvZip.setTextColor(getResources().getColor(R.color.red));

        tvCompany.setText("无");
        tvCompany.setTextColor(getResources().getColor(R.color.red));

        tvCard.setText("无");
        tvCard.setTextColor(getResources().getColor(R.color.red));

    }

    /**
     * 成功
     */
    private void initSuccess() {
        if (mPhoneEntity == null) {
            return;
        }
        tvQueryStatus.setText("成功");
        tvQueryStatus.setTextColor(getResources().getColor(R.color.main_color));

        tvProvince.setText(mPhoneEntity.getProvince());
        tvProvince.setTextColor(getResources().getColor(R.color.main_color));

        tvCity.setText(mPhoneEntity.getCity());
        tvCity.setTextColor(getResources().getColor(R.color.main_color));

        tvAreacode.setText(mPhoneEntity.getAreacode());
        tvAreacode.setTextColor(getResources().getColor(R.color.main_color));

        tvZip.setText(mPhoneEntity.getZip());
        tvZip.setTextColor(getResources().getColor(R.color.main_color));

        tvCompany.setText(mPhoneEntity.getCompany());
        tvCompany.setTextColor(getResources().getColor(R.color.main_color));

        tvCard.setText(mPhoneEntity.getCard());
        tvCard.setTextColor(getResources().getColor(R.color.main_color));
    }


    @OnClick(R.id.bt_query)
    public void onViewClicked() {
        if (etPhone.getText().toString().length() == 11) {
            if (AppUtils.isMobileNO(etPhone.getText().toString())) {
                showProgressDialog("查询中,请稍后..", false);
                getPhoneInfos();
            } else {
                ToastUtil.showToast("手机号码不正确!");
            }
        } else {
            ToastUtil.showToast("手机号码不正确!");
        }
    }

    /**
     * 查询
     */
    private void getPhoneInfos() {
        OkGo.<String>get(API.PHONE_QUERY_URL)
                .tag(this)
                .params("key", "59d782a7e1a229e751a30e2c6cb18c92")
                .params("phone", etPhone.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        btQuery.setEnabled(true);
                        closeProgressDialog();
                        Logger.json(response.body());
                        try {
                            JSONObject JSON = new JSONObject(response.body());
                            String code = JSON.optString(API.SUCCESS_CODE);
                            String result = JSON.optString(API.SUCCESS_DATA);
                            if (code.equals("200")) {
                                Gson gson = new Gson();
                                mPhoneEntity = gson.fromJson(result, PhoneEntity.class);
                                initSuccess();
                            } else {
                                initError(JSON.optString(API.SUCCESS_REASON));
                            }
                        } catch (Exception s) {
                            s.printStackTrace();
                            initError("解析错误!");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        closeProgressDialog();
                        initError("网络异常!");
                        btQuery.setEnabled(true);
                        ToastUtil.showToast(API.ERROR_STRING);
                    }
                });
    }

}
