package pers.life.helper.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import pers.life.helper.R;


/**
 * jiang 2019-01-01
 * 弹出框工具类
 */
public class IOSDialogUtils {
    private static IOSDialogUtils IOSDialogUtils;

    public static IOSDialogUtils getInstance() {
        if (IOSDialogUtils == null) {
            IOSDialogUtils = new IOSDialogUtils();
        }
        return IOSDialogUtils;
    }

    public void showDialogIOS(Context context, final IOSDialogBean bean) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.ios_dialog);
        dialog.setCancelable(bean.isCancelable);
        dialog.show();
        View view = (View) dialog.findViewById(R.id.view_line);
        //设置标题
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        if (bean.isTitle()) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(TextUtils.isEmpty(bean.getmTitle()) ? "" : bean.getmTitle());
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        //设置内容
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        if (bean.isMgs()) {
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(TextUtils.isEmpty(bean.getmMgs()) ? "" : bean.getmMgs());

        } else {
            tvMsg.setVisibility(View.GONE);
        }
        //设置取消
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        if (bean.isCancel()) {
            tvCancel.setVisibility(View.VISIBLE);
            tvCancel.setText(TextUtils.isEmpty(bean.getmCancel()) ? "取消" : bean.getmCancel());

        } else {
            tvCancel.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
        //设置OK
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
        if (bean.isOk()) {
            tvOk.setVisibility(View.VISIBLE);
            tvOk.setText(TextUtils.isEmpty(bean.getmOk()) ? "确定" : bean.getmOk());
        } else {
            tvOk.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.getOnButtonClickListener().onPositiveButtonClick(dialog);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.getOnButtonClickListener().onCancelButtonClick(dialog);
            }
        });
    }

    /**
     * 按钮点击回调接口
     */
    public interface OnButtonClickListener {
        void onPositiveButtonClick(IOSDialog dialog);

        void onCancelButtonClick(IOSDialog dialog);
    }

    /**
     * 设置的参数
     */
    public static class IOSDialogBean {
        private String mTitle;//弹框的标题
        private boolean isTitle = true;//是否显示标题 默认显示
        private String mMgs;//显示的内容
        private boolean isMgs = true;//是否显示内容 默认显示
        private String mCancel;//设置<取消>的文字
        private boolean isCancel = true;//是否显示取消这栏 默认显示
        private String mOk;//设置<确定>的文字
        private boolean isOk = true;//是否显示确定这栏 默认显示
        private boolean isCancelable = true;//是否点击返回键和其它地方弹框消失 默认可以
        private OnButtonClickListener onButtonClickListener;//点击事件

        public OnButtonClickListener getOnButtonClickListener() {
            return onButtonClickListener;
        }

        public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
            this.onButtonClickListener = onButtonClickListener;
        }

        public String getmTitle() {
            return mTitle;
        }

        public void setmTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public boolean isTitle() {
            return isTitle;
        }

        public void setTitle(boolean title) {
            isTitle = title;
        }

        public String getmMgs() {
            return mMgs;
        }

        public void setmMgs(String mMgs) {
            this.mMgs = mMgs;
        }

        public boolean isMgs() {
            return isMgs;
        }

        public void setMgs(boolean mgs) {
            isMgs = mgs;
        }

        public String getmCancel() {
            return mCancel;
        }

        public void setmCancel(String mCancel) {
            this.mCancel = mCancel;
        }

        public boolean isCancel() {
            return isCancel;
        }

        public void setCancel(boolean cancel) {
            isCancel = cancel;
        }

        public String getmOk() {
            return mOk;
        }

        public void setmOk(String mOk) {
            this.mOk = mOk;
        }

        public boolean isOk() {
            return isOk;
        }

        public void setOk(boolean ok) {
            isOk = ok;
        }

        public boolean isCancelable() {
            return isCancelable;
        }

        public void setCancelable(boolean cancelable) {
            isCancelable = cancelable;
        }
    }

}
