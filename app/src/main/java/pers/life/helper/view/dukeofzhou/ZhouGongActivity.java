package pers.life.helper.view.dukeofzhou;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.entity.ZhouGongEntity;
import pers.life.helper.net.API;
import pers.life.helper.utils.LogUtil;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;

public class ZhouGongActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.progress)
    ProgressBar progress;

    private String mMuenName = "黄金";
    private ZhouGongLiseAdapter mZhouGongLiseAdapter;
    private List<ZhouGongEntity> mSumList = new ArrayList<>();

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_zhou_gong;
    }

    @Override
    protected void initVariables() {

    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbarNav();
        setTitle("周公解梦");
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        tvNodata.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mZhouGongLiseAdapter = new ZhouGongLiseAdapter(mSumList);
        mZhouGongLiseAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//添加缩放动画效果
        recyclerView.setAdapter(mZhouGongLiseAdapter);
    }

    @OnClick(R.id.bt_query)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(etName.getText().toString())) {
            mMuenName = etName.getText().toString().trim();
            QueryNmae();
        } else {
            QueryNmae();
        }
    }

    private void QueryNmae() {
        tvNodata.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        btQuery.setEnabled(false);
        OkGo.<String>get(API.ZHOUGONG_URL)
                .tag(this)
                .params("key", "19347f3d9ad295bdfb571bc57606ff81")
                .params("q", mMuenName)
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
                                JSONArray list = JSON.optJSONArray("result");
                                if (list.length() > 0) {
                                    progress.setVisibility(View.GONE);
                                    tvNodata.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    mSumList.clear();
                                    ZhouGongEntity entity;
                                    Gson gson = new Gson();
                                    for (int i = 0; i < list.length(); i++) {
                                        entity = gson.fromJson(list.optString(i), ZhouGongEntity.class);
                                        mSumList.add(entity);
                                    }
                                    mZhouGongLiseAdapter.notifyDataSetChanged();
                                } else {
                                    progress.setVisibility(View.GONE);
                                    tvNodata.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }

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
