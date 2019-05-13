package pers.life.helper.view.courier;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.entity.CouerierData;
import pers.life.helper.entity.CouerierName;
import pers.life.helper.entity.RecipeEntity;
import pers.life.helper.net.API;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.courier.adapter.ChooseDataAdapter;
import pers.life.helper.view.recipe.adapter.RecipeLiseAdapter;

public class CouerierActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_choose)
    TextView tvChoose;
    @BindView(R.id.et_numbers)
    EditText etNumbers;
    @BindView(R.id.et_sf_senderPhone)
    EditText etSfSenderPhone;
    @BindView(R.id.ll_sf_layout)
    LinearLayout llSfLayout;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.et_sf_receiverPhone)
    EditText etSfReceiverPhone;

    private CouerierName mCouerierName;
    private boolean isSF = false;//是否选择了顺风
    private String SfSenderPhone = "";
    private String SfReceiverPhone = "";

    private CouerierData mCouerierData;

    private ChooseDataAdapter mChooseDataAdapter;
    private List<CouerierData.ListBean> mSumList = new ArrayList<>();

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_couerier;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbarNav();
        setTitle("快递查询");
        tvChoose.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        tvNodata.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChooseDataAdapter = new ChooseDataAdapter(mSumList);
        recyclerView.setAdapter(mChooseDataAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ChooseCouerierActivity.RETURN_CODE && data != null) {
            Bundle bundle = data.getExtras();
            mCouerierName = (CouerierName) bundle.getSerializable("mCouerierName");
            if (mCouerierName != null) {
                tvChoose.setText(mCouerierName.getCom());
                if (mCouerierName.getNo().equals("sf")) {
                    llSfLayout.setVisibility(View.VISIBLE);
                    isSF = true;
                } else {
                    isSF = false;
                    llSfLayout.setVisibility(View.GONE);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.tv_choose, R.id.bt_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choose://选择公司
                ChooseCouerierActivity.actionActivity(this, ChooseCouerierActivity.RETURN_CODE);
                break;
            case R.id.bt_query://查询
                if (mCouerierName == null) {
                    ToastUtil.showToast("请选择一家快递公司");
                    return;
                }
                if (isSF) {
                    //顺风查询
                    SfSenderPhone = etSfSenderPhone.getText().toString();
                    SfReceiverPhone = etSfReceiverPhone.getText().toString();
                    if (TextUtils.isEmpty(SfSenderPhone) || TextUtils.isEmpty(SfReceiverPhone)) {
                        ToastUtil.showToast("请输入顺风快递必填的手机尾号..");
                    } else {
                        QuerCoerierData();
                    }
                } else {
                    QuerCoerierData();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 查询
     */
    private void QuerCoerierData() {
        if (TextUtils.isEmpty(etNumbers.getText().toString())) {
            ToastUtil.showToast("单号不能为空!");
            return;
        }
        progress.setVisibility(View.VISIBLE);
        tvNodata.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        btQuery.setEnabled(false);
        OkGo.<String>get(API.COUERIER_URL)
                .tag(this)
                .params("key", "ccb34eea6dd7eeddfef1bb280b58feed")
                .params("com", mCouerierName.getNo())
                .params("no", etNumbers.getText().toString())
                .params("senderPhone", SfSenderPhone)
                .params("receiverPhone", SfReceiverPhone)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        btQuery.setEnabled(true);
                        closeProgressDialog();
                        Logger.json(response.body());
                        try {
                            JSONObject JSON = new JSONObject(response.body());
                            int error_code = JSON.optInt("error_code");
                            if (error_code == 0) {
                                Gson gson = new Gson();
                                mCouerierData = gson.fromJson(JSON.optString("result"), CouerierData.class);
                                progress.setVisibility(View.GONE);
                                tvNodata.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                mSumList.clear();
                                mSumList.addAll(mCouerierData.getList());
                                mChooseDataAdapter.notifyDataSetChanged();
                            } else {
                                progress.setVisibility(View.GONE);
                                tvNodata.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                ToastUtil.showToast(JSON.optString(API.SUCCESS_REASON));
                            }
                        } catch (Exception s) {
                            s.printStackTrace();
                            progress.setVisibility(View.GONE);
                            tvNodata.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        btQuery.setEnabled(true);
                        progress.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);
                        ToastUtil.showToast(API.ERROR_STRING);
                    }
                });
    }
}
