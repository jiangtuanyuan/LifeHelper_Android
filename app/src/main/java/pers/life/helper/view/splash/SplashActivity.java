package pers.life.helper.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import butterknife.BindView;
import pers.life.helper.R;
import pers.life.helper.net.API;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.SPUtils;
import pers.life.helper.utils.ToastUtil;
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
            String userName = SPUtils.getInstance().getString(SPUtils.USER_NAME);
            String userPwd = SPUtils.getInstance().getString(SPUtils.USER_PWD);
            if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPwd)) {
                Login(userName, userPwd);
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                SplashActivity.this.finish();
            }
        }, 2000);
        tvVersion.setText("v: " + AppUtils.getVersionName(this));
    }

    /**
     * 登陆
     */
    private void Login(String username, String pwd) {
        OkGo.<String>get(API.LOGON)
                .tag(this)
                .params("username", username)
                .params("userpwd", pwd)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject JSON = new JSONObject(response.body());
                            int error_code = JSON.optInt("Error_code");
                            if (error_code == 0) {
                                SPUtils.getInstance().putString(SPUtils.USER_TEL, JSON.optString("UserTel"));
                                SPUtils.getInstance().putString(SPUtils.USER_NAME, username);
                                SPUtils.getInstance().putString(SPUtils.USER_PWD, pwd);
                                ToastUtil.showToast("登陆成功!");
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                SplashActivity.this.finish();
                            } else {
                                ToastUtil.showToast(JSON.optString("Error_info"));
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                SplashActivity.this.finish();
                            }
                        } catch (Exception s) {
                            s.printStackTrace();
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            SplashActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ToastUtil.showToast(API.ERROR_STRING);
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        SplashActivity.this.finish();
                    }
                });

    }
}
