package pers.life.helper.view.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.io.File;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.net.API;
import pers.life.helper.utils.AppUtils;
import pers.life.helper.utils.LogUtil;
import pers.life.helper.utils.NumberProgressBar;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.card.QueryCardActivity;
import pers.life.helper.view.ip.QueryIPActivity;
import pers.life.helper.view.joke.JokeActivity;
import pers.life.helper.view.phone.QueryPhoneActivity;
import pers.life.helper.view.postcode.PostcodeActivity;
import pers.life.helper.view.weather.WeatherActivity;

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
        setTitle("\t\t" + getResources().getString(R.string.app_name) + "\t" + AppUtils.getVersionName(this));
        setSupportActionBar(toolbar);
        AppUtils.checkPermissions(this);
        checkAppUpdate();
    }

    @OnClick({R.id.tv_tel, R.id.tv_ip, R.id.tv_card, R.id.tv_zip_code, R.id.tv_joke, R.id.tv_weather})
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
            case R.id.tv_joke:
                startActivity(new Intent(this, JokeActivity.class));
                break;
            case R.id.tv_weather:
                startActivity(new Intent(this, WeatherActivity.class));
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

    /**
     * 检查更新
     */
    private void checkAppUpdate() {
        showProgressDialog("检查更新中..");
        OkGo.<String>get(API.APP_UPDATE_URL)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        closeProgressDialog();
                        LogUtil.e("appcode", "线上版本:" + response.body());
                        try {
                            int code = Integer.parseInt(response.body());
                            if (code > AppUtils.getVersionCode(MainActivity.this)) {
                                showNoticeDialog();
                            } else {
                                ToastUtil.showToast("已经是最新版本!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.showToast("更新检测失败!");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        closeProgressDialog();
                        ToastUtil.showToast("更新检测失败!");
                    }
                });
    }

    /**
     * 显示软件更新对话
     */
    public void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("软件更新");
        builder.setMessage("软件需要更新!");
        builder.setPositiveButton("更新",
                (dialog, which) -> {
                    dialog.dismiss();
                    showDownloadDialog();
                });
        Dialog noticeDialog = builder.create();
        noticeDialog.setCancelable(false);
        noticeDialog.show();
    }

    /**
     * 下载APK
     */
    private void showDownloadDialog() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_down, null);
        final TextView downStatus = (TextView) view.findViewById(R.id.down_status);
        final TextView tvDownloadSize = (TextView) view.findViewById(R.id.downloadSize);
        final TextView tvProgress = (TextView) view.findViewById(R.id.tvProgress);
        final TextView tvNetSpeed = (TextView) view.findViewById(R.id.netSpeed);
        final NumberProgressBar pbProgress = (NumberProgressBar) view.findViewById(R.id.pbProgress);
        final NumberFormat numberFormat;
        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        builder.setView(view);
        builder.setCancelable(false);
        builder.create();
        final android.support.v7.app.AlertDialog alertDialog = builder.show();
        OkGo.<File>get(API.APP_UPDATE_APK_URL)
                .tag(this)
                .execute(new FileCallback(AppUtils.getFileName(API.APP_UPDATE_APK_URL)) {
                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        downStatus.setText("下载中..");
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        alertDialog.dismiss();
                        //安装APK
                        AppUtils.InstallApk(MainActivity.this, response.body().getPath());
                    }

                    @Override
                    public void onError(Response<File> response) {
                        alertDialog.dismiss();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        String downloadLength = Formatter.formatFileSize(MainActivity.this, progress.currentSize);
                        String totalLength = Formatter.formatFileSize(MainActivity.this, progress.totalSize);
                        tvDownloadSize.setText(downloadLength + "/" + totalLength);
                        String speed = Formatter.formatFileSize(MainActivity.this, progress.speed);
                        tvNetSpeed.setText(String.format("%s/s", speed));
                        tvProgress.setText(numberFormat.format(progress.fraction));
                        pbProgress.setMax(10000);
                        pbProgress.setProgress((int) (progress.fraction * 10000));
                    }
                });
    }


}
