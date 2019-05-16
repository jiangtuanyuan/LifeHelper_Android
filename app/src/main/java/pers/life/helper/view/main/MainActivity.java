package pers.life.helper.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.utils.AppUpdate;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.IOSDialog;
import pers.life.helper.utils.IOSDialogUtils;
import pers.life.helper.utils.SPUtils;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.card.QueryCardActivity;
import pers.life.helper.view.courier.CouerierActivity;
import pers.life.helper.view.dukeofzhou.ZhouGongActivity;
import pers.life.helper.view.ip.QueryIPActivity;
import pers.life.helper.view.joke.JokeActivity;
import pers.life.helper.view.login.LoginActivity;
import pers.life.helper.view.phone.QueryPhoneActivity;
import pers.life.helper.view.postcode.PostcodeActivity;
import pers.life.helper.view.recipe.RecipeListActivity;
import pers.life.helper.view.smart.SmartActivity;
import pers.life.helper.view.weather.WeatherActivity;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    private String mUserName = SPUtils.getInstance().getString(SPUtils.USER_NAME);

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("\t\t" + getResources().getString(R.string.app_name) + "\t" + AppUtils.getVersionName(this));
        setSupportActionBar(toolbar);
        AppUtils.checkPermissions(this);
        tvUserName.setText("欢迎您," + mUserName);
        AppUpdate.checkAppUpdate(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_exit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.exit:
                LoginExit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * 退出
     */
    private void LoginExit() {
        IOSDialogUtils.IOSDialogBean iosDialogBean = new IOSDialogUtils.IOSDialogBean();
        iosDialogBean.setmTitle("提示");
        iosDialogBean.setCancelable(false);
        iosDialogBean.setmMgs("是否注销登陆?");
        iosDialogBean.setOnButtonClickListener(new IOSDialogUtils.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(IOSDialog dialog) {
                dialog.dismiss();
                SPUtils.getInstance().clear();
                SPUtils.getInstance().putString(SPUtils.USER_NAME, mUserName);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
            @Override
            public void onCancelButtonClick(IOSDialog dialog) {
                dialog.dismiss();
            }
        });
        IOSDialogUtils.getInstance().showDialogIOS(this, iosDialogBean);
    }

    @OnClick({R.id.tv_smart,R.id.tv_couerier, R.id.tv_zhou, R.id.tv_recipe, R.id.tv_tel, R.id.tv_ip, R.id.tv_card, R.id.tv_zip_code, R.id.tv_joke, R.id.tv_weather})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tel://号码归属地
                startActivity(new Intent(this, QueryPhoneActivity.class));
                break;
            case R.id.tv_ip://IP查询
                startActivity(new Intent(this, QueryIPActivity.class));
                break;
            case R.id.tv_card://身份证效验
                startActivity(new Intent(this, QueryCardActivity.class));
                break;
            case R.id.tv_zip_code://邮编
                startActivity(new Intent(this, PostcodeActivity.class));
                break;
            case R.id.tv_joke://笑话
                startActivity(new Intent(this, JokeActivity.class));
                break;
            case R.id.tv_weather://天气
                startActivity(new Intent(this, WeatherActivity.class));
                break;
            case R.id.tv_recipe://菜谱大全
                startActivity(new Intent(this, RecipeListActivity.class));
                break;
            case R.id.tv_zhou://周公解梦
                startActivity(new Intent(this, ZhouGongActivity.class));
                break;
            case R.id.tv_couerier://快递查询
                startActivity(new Intent(this, CouerierActivity.class));
                break;
            case R.id.tv_smart://AI 识别
                startActivity(new Intent(this, SmartActivity.class));
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
