package pers.life.helper.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.card.QueryCardActivity;
import pers.life.helper.view.ip.QueryIPActivity;
import pers.life.helper.view.phone.QueryPhoneActivity;
import pers.life.helper.view.postcode.PostcodeActivity;

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
        setTitle("\t\t" + getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        AppUtils.checkPermissions(this);
    }

    @OnClick({R.id.tv_tel, R.id.tv_ip, R.id.tv_card,R.id.tv_zip_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tel:
                startActivity(new Intent(this, QueryPhoneActivity.class));
                break;
            case R.id.tv_ip:
                startActivity(new Intent(this, QueryIPActivity.class));
                break;
            case R.id.tv_card:
                startActivity(new Intent(this, QueryCardActivity.class));
                break;
            case R.id.tv_zip_code:
                startActivity(new Intent(this, PostcodeActivity.class));
                break;
            default:
                break;
        }
    }

    //实现home键盘
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
        }
        return super.onKeyDown(keyCode, event);
    }
}
