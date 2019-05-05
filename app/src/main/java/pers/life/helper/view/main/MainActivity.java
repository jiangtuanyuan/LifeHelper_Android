package pers.life.helper.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.ip.QueryIPActivity;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_ip)
    TextView tvIp;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //showProgressDialog("加载中..");
        setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        AppUtils.checkPermissions(this);

    }

    @OnClick({R.id.tv_tel, R.id.tv_ip, R.id.tv_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tel:
                ToastUtil.showToast("归属地查询");
                break;
            case R.id.tv_ip:
                startActivity(new Intent(this, QueryIPActivity.class));
                break;
            case R.id.tv_card:
                ToastUtil.showToast("身份证");
                break;
            default:
                break;
        }
    }
}
