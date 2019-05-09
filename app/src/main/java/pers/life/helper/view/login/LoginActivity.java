package pers.life.helper.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.EditUtlis;
import pers.life.helper.utils.SPUtils;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.main.MainActivity;

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
        userName.setText(SPUtils.getInstance().getString(SPUtils.USER_TEL));
        userPwd.setText(SPUtils.getInstance().getString(SPUtils.USER_PWD));
        tvVersion.setText("v " + AppUtils.getVersionName(this));
    }

    @OnClick({R.id.submit_btn, R.id.tv_regist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_regist:
                break;
            default:
                break;
        }
    }
}
