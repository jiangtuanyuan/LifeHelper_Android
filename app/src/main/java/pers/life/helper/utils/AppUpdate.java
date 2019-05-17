package pers.life.helper.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.text.NumberFormat;

import pers.life.helper.R;
import pers.life.helper.net.API;

public class AppUpdate {
    /**
     * 检查更新
     */
    public static void checkAppUpdate(Context mContext) {
        OkGo.<String>get(API.APP_UPDATE_URL)
                .tag(mContext)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            int code = Integer.parseInt(response.body());
                            if (code > AppUtils.getVersionCode(mContext)) {
                                showNoticeDialog(mContext);
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
                        ToastUtil.showToast("更新检测失败!");
                    }
                });
    }

    /**
     * 显示软件更新对话
     */
    private static void showNoticeDialog(Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件更新");
        builder.setMessage("软件需要更新!");
        builder.setPositiveButton("更新",
                (dialog, which) -> {
                    dialog.dismiss();
                    showDownloadDialog(mContext);
                });
        Dialog noticeDialog = builder.create();
        noticeDialog.setCancelable(false);
        noticeDialog.show();
    }

    /**
     * 下载APK
     */
    private static void showDownloadDialog(Context mContext) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
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
        final AlertDialog alertDialog = builder.show();
        OkGo.<File>get(API.APP_UPDATE_APK_URL)
                .tag(mContext)
                .execute(new FileCallback(AppUtils.getFileName(API.APP_UPDATE_APK_URL)) {
                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        downStatus.setText("下载中..");
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        alertDialog.dismiss();
                        //安装APK
                        AppUtils.InstallApk(mContext, response.body().getPath());
                    }

                    @Override
                    public void onError(Response<File> response) {
                        alertDialog.dismiss();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        String downloadLength = Formatter.formatFileSize(mContext, progress.currentSize);
                        String totalLength = Formatter.formatFileSize(mContext, progress.totalSize);
                        tvDownloadSize.setText(downloadLength + "/" + totalLength);
                        String speed = Formatter.formatFileSize(mContext, progress.speed);
                        tvNetSpeed.setText(String.format("%s/s", speed));
                        tvProgress.setText(numberFormat.format(progress.fraction));
                        pbProgress.setMax(10000);
                        pbProgress.setProgress((int) (progress.fraction * 10000));
                    }
                });
    }


}
