package pers.life.helper.view.regist;

import android.os.Bundle;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.net.API;
import pers.life.helper.utils.EditUtlis;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;

public class RegistActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_name_clear)
    ImageView etUserNameClear;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_tel_clear)
    ImageView etTelClear;
    @BindView(R.id.et_user_pwd1)
    EditText etUserPwd1;
    @BindView(R.id.et_user_pwd1_cb)
    CheckBox etUserPwd1Cb;
    @BindView(R.id.et_user_pwd2)
    EditText etUserPwd2;
    @BindView(R.id.et_user_pwd2_cb)
    CheckBox etUserPwd2Cb;
    @BindView(R.id.submit_btn)
    Button submitBtn;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbarNav();
        setTitle("注册");
        EditUtlis.EditTextChangedListener(etUserName, etUserNameClear);
        EditUtlis.EditTextChangedListener(etTel, etTelClear);
        EditUtlis.EditTextChangedListener(etUserPwd1, etUserPwd1Cb);
        EditUtlis.EditTextChangedListener(etUserPwd2, etUserPwd2Cb);
    }

    @OnClick(R.id.submit_btn)
    public void onViewClicked() {
        if (isckeck()) {
            commitRegist();
        }
    }

    private boolean isckeck() {
        String s1 = etUserName.getText().toString();
        String s2 = etTel.getText().toString();
        String s3 = etUserPwd1.getText().toString();
        String s4 = etUserPwd2.getText().toString();
        if (TextUtils.isEmpty(s1)) {
            ToastUtil.showToast("用户名不能为空！");
            return false;
        }
        if (TextUtils.isEmpty(s2) || s2.length() != 11) {
            ToastUtil.showToast("手机号码输入错误！");
            return false;
        }
        if (TextUtils.isEmpty(s3)) {
            ToastUtil.showToast("请输入密码!");
            return false;
        }
        if (TextUtils.isEmpty(s4)) {
            ToastUtil.showToast("请确定密码!");
            return false;
        }
        if (!s3.equals(s4)) {
            ToastUtil.showToast("两次密码不一致!");
            return false;
        }
        return true;
    }

    private void commitRegist() {
        String s1 = etUserName.getText().toString();
        String s2 = etTel.getText().toString();
        String s3 = etUserPwd1.getText().toString();
        showProgressDialog("注册中,请稍后..");
        submitBtn.setEnabled(false);
        OkGo.<String>get(API.REGIST)
                .tag(this)
                .params("AddNmae", s1)
                .params("UserTel", s2)
                .params("UserPwd", s3)
                .params("UserSex", "男")
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
                                ToastUtil.showToast("注册成功!");
                                finish();
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

}
