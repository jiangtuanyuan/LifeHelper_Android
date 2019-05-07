package pers.life.helper.view.ip;

import android.os.Bundle;
import android.text.TextUtils;
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
import pers.life.helper.entity.IPEntity;
import pers.life.helper.net.API;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;

public class QueryIPActivity extends BaseActivity {
    @BindView(R.id.et_no1)
    EditText etNo1;
    @BindView(R.id.et_no2)
    EditText etNo2;
    @BindView(R.id.et_no3)
    EditText etNo3;
    @BindView(R.id.et_no4)
    EditText etNo4;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.tv_this_ip)
    TextView tvThisIp;
    @BindView(R.id.tv_query_status)
    TextView tvQueryStatus;
    @BindView(R.id.tv_ip_country)
    TextView tvIpCountry;
    @BindView(R.id.tv_ip_province)
    TextView tvIpProvince;
    @BindView(R.id.tv_ip_city)
    TextView tvIpCity;
    @BindView(R.id.tv_ip_isp)
    TextView tvIpIsp;

    private IPEntity mIPEntity;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_query_ip;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("IP地址查询");
        initToolbarNav();
        tvThisIp.setText("本机IP:" + AppUtils.getIP(this));
    }

    @OnClick(R.id.bt_query)
    public void onViewClicked() {
        if (cheStringIP()) {
            showProgressDialog("查询中,请稍后..", false);
            btQuery.setEnabled(false);
            getIpInfos();
        } else {
            initError("IP地址不能为空!");
        }
    }

    /**
     * 效验
     *
     * @return
     */
    private boolean cheStringIP() {
        if (TextUtils.isEmpty(etNo1.getText().toString()) || TextUtils.isEmpty(etNo2.getText().toString()) || TextUtils.isEmpty(etNo3.getText().toString()) || TextUtils.isEmpty(etNo4.getText().toString())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 出错
     */
    private void initError(String s) {
        tvQueryStatus.setText(s);
        tvQueryStatus.setTextColor(getResources().getColor(R.color.red));

        tvIpCountry.setText("无");
        tvIpCountry.setTextColor(getResources().getColor(R.color.red));

        tvIpProvince.setText("无");
        tvIpProvince.setTextColor(getResources().getColor(R.color.red));

        tvIpCity.setText("无");
        tvIpCity.setTextColor(getResources().getColor(R.color.red));

        tvIpIsp.setText("无");
        tvIpIsp.setTextColor(getResources().getColor(R.color.red));
    }

    /**
     * 成功
     */
    private void initSuccess() {
        if (mIPEntity == null) {
            return;
        }
        tvQueryStatus.setText("成功");
        tvQueryStatus.setTextColor(getResources().getColor(R.color.main_color));

        tvIpCountry.setText(mIPEntity.getCountry());
        tvIpCountry.setTextColor(getResources().getColor(R.color.main_color));

        tvIpProvince.setText(mIPEntity.getProvince());
        tvIpProvince.setTextColor(getResources().getColor(R.color.main_color));

        tvIpCity.setText(mIPEntity.getCity());
        tvIpCity.setTextColor(getResources().getColor(R.color.main_color));

        tvIpIsp.setText(mIPEntity.getIsp());
        tvIpIsp.setTextColor(getResources().getColor(R.color.main_color));
    }

    /**
     * 查询
     */
    private void getIpInfos() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(etNo1.getText().toString())
                .append(".");
        buffer.append(etNo2.getText().toString())
                .append(".");
        buffer.append(etNo3.getText().toString())
                .append(".");
        buffer.append(etNo4.getText().toString());
        OkGo.<String>get(API.IP_QUERY_URL)
                .tag(this)
                .params("key", API.APP_KEY)
                .params("ip", buffer.toString())
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
                                mIPEntity = gson.fromJson(result, IPEntity.class);
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
