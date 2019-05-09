package pers.life.helper.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import butterknife.BindView;
import pers.life.helper.R;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.view.login.LoginActivity;
import pers.life.helper.view.main.MainActivity;
import pers.life.helper.view.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariables() {


    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            SplashActivity.this.finish();
        }, 2000);
        tvVersion.setText("v: " + AppUtils.getVersionName(this));
    }
}
