package pers.life.helper.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.net.API;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.EditUtlis;
import pers.life.helper.utils.SPUtils;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.main.MainActivity;
import pers.life.helper.view.regist.RegistActivity;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.user_name)
    EditText userName;//用户名的文本框
    @BindView(R.id.img_btn_account)
    ImageView imgBtnAccount;//用户名的文本框右边的清除按钮
    @BindView(R.id.user_pwd)
    EditText userPwd;//密码框
    @BindView(R.id.img_btn_pwd)
    CheckBox imgBtnPwd;//密码框的文本框右边的显示隐藏
    @BindView(R.id.submit_btn)
    Button submitBtn;//登陆按钮
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_regist)
    TextView tvRegist;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        EditUtlis.EditTextChangedListener(userName, imgBtnAccount);
        EditUtlis.EditTextChangedListener(userPwd, imgBtnPwd);
        userName.setText(SPUtils.getInstance().getString(SPUtils.USER_NAME));
        userPwd.setText(SPUtils.getInstance().getString(SPUtils.USER_PWD));
        tvVersion.setText("v " + AppUtils.getVersionName(this));
    }

    @OnClick({R.id.submit_btn, R.id.tv_regist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                if (!TextUtils.isEmpty(userName.getText().toString()) && !TextUtils.isEmpty(userPwd.getText().toString())) {
                    Login(userName.getText().toString(), userPwd.getText().toString());
                } else {
                    ToastUtil.showToast("用户名和密码不能为空..");
                }
                break;
            case R.id.tv_regist:
                startActivity(new Intent(this, RegistActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 登陆
     */
    private void Login(String username, String pwd) {
        showProgressDialog("登陆中,请稍后..");
        submitBtn.setEnabled(false);
        OkGo.<String>get(API.LOGON)
                .tag(this)
                .params("username", username)
                .params("userpwd", pwd)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        closeProgressDialog();
                        submitBtn.setEnabled(true);
                        Logger.json(response.body());
                        try {
                            JSONObject JSON = new JSONObject(response.body());
                            int error_code = JSON.optInt("Error_code");
                            if (error_code == 0) {
                                SPUtils.getInstance().putString(SPUtils.USER_TEL, JSON.optString("UserTel"));
                                SPUtils.getInstance().putString(SPUtils.USER_NAME, username);
                                SPUtils.getInstance().putString(SPUtils.USER_PWD, pwd);

                                ToastUtil.showToast("登陆成功!");
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                LoginActivity.this.finish();
                            } else {
                                ToastUtil.showToast(JSON.optString("Error_info"));
                            }
                        } catch (Exception s) {
                            s.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        submitBtn.setEnabled(true);
                        closeProgressDialog();
                        ToastUtil.showToast(API.ERROR_STRING);
                    }
                });
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
