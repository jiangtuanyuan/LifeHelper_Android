package pers.life.helper.view.weather;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.utils.ToastUtil;


/**
 * Created by 蒋 on 2018/12/5.
 * 人员在线的弹出框
 */

public class QueryCityNameDialog extends DialogFragment {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.bt_query)
    Button btQuery;
    private OnCityNameOk mOnCityNameOk;

    public void setmOnCityNameOk(OnCityNameOk mOnCityNameOk) {
        this.mOnCityNameOk = mOnCityNameOk;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全透明
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.weaher_query_city_name, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.bt_query)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(etName.getText().toString())) {
            mOnCityNameOk.onCituNmae(etName.getText().toString());
            this.dismiss();
        } else {
            ToastUtil.showToast("请输入城市名称..");
        }
    }

    interface OnCityNameOk {
        void onCituNmae(String name);
    }
}
