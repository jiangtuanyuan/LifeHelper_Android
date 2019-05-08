package pers.life.helper.view.card;

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
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.entity.CardEntity;
import pers.life.helper.entity.PhoneEntity;
import pers.life.helper.net.API;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;

public class QueryCardActivity extends BaseActivity {
    @BindView(R.id.et_card_id)
    EditText etCardId;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.tv_query_status)
    TextView tvQueryStatus;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_verify)
    TextView tvVerify;
    private CardEntity mCardEntity;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_query_card;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("身份证效验");
        initToolbarNav();
    }

    /**
     * 出错
     */
    private void initError(String s) {
        tvQueryStatus.setText(s);
        tvQueryStatus.setTextColor(getResources().getColor(R.color.red));

        tvArea.setText("无");
        tvArea.setTextColor(getResources().getColor(R.color.red));

        tvSex.setText("无");
        tvSex.setTextColor(getResources().getColor(R.color.red));

        tvBirthday.setText("无");
        tvBirthday.setTextColor(getResources().getColor(R.color.red));

        tvVerify.setText("无");
        tvVerify.setTextColor(getResources().getColor(R.color.red));

    }

    /**
     * 成功
     */
    private void initSuccess() {
        if (mCardEntity == null) {
            return;
        }
        tvQueryStatus.setText("成功");
        tvQueryStatus.setTextColor(getResources().getColor(R.color.main_color));

        tvArea.setText(mCardEntity.getArea());
        tvArea.setTextColor(getResources().getColor(R.color.main_color));

        tvSex.setText(mCardEntity.getSex());
        tvSex.setTextColor(getResources().getColor(R.color.main_color));

        tvBirthday.setText(mCardEntity.getBirthday());
        tvBirthday.setTextColor(getResources().getColor(R.color.main_color));

        tvVerify.setText(mCardEntity.getVerify());
        tvVerify.setTextColor(getResources().getColor(R.color.main_color));
    }


    @OnClick(R.id.bt_query)
    public void onViewClicked() {
        if (etCardId.getText().toString().length() == 18) {
            showProgressDialog("查询中,请稍后..", false);
            getPhoneInfos();
        } else {
            ToastUtil.showToast("输入的身份证不正确!");
        }
    }

    /**
     * 查询
     */
    private void getPhoneInfos() {
        OkGo.<String>get(API.CARD_QUERY_URL)
                .tag(this)
                .params("key", "81821ba9b68783b12dd734e8c0fb83f2")
                .params("cardno", etCardId.getText().toString())
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
                                mCardEntity = gson.fromJson(result, CardEntity.class);
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
