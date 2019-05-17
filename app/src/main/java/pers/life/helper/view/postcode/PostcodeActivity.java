package pers.life.helper.view.postcode;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.entity.PostcodeEntity;
import pers.life.helper.net.API;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;

public class PostcodeActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.et_postcode)
    EditText etPostcode;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.tv_sum_data)
    TextView tvSumData;
    @BindView(R.id.progress)
    ProgressBar progress;

    private PostcodeItemAdapter mPostcodeItemAdapter;
    private List<PostcodeEntity> mSumList = new ArrayList<>();//总数据


    private int totalcount = 0;//总数据
    private int totalpage = 1;//总页数
    private int currentpage = 1;//当前第几页
    private int pagesize = 20;//每页多少条

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_postcode;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("邮编查询");
        initToolbarNav();
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(this);//进行下拉刷新的监听
        refreshLayout.setOnLoadMoreListener(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mPostcodeItemAdapter = new PostcodeItemAdapter(mSumList);
        mPostcodeItemAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//添加缩放动画效果
        mRecycler.setAdapter(mPostcodeItemAdapter);
        progress.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        totalcount = 0;//总数据
        totalpage = 1;//总页数
        currentpage = 1;//当前第几页
        pagesize = 20;//每页多少条

        getInfos();
    }

    /**
     * 上拉加载
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (currentpage > totalpage) {
            ToastUtil.showToast("已经加载完了..");
            refreshLayout.finishLoadMore();
        } else {
            getInfos();
        }

    }

    @OnClick(R.id.bt_query)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(etPostcode.getText().toString())) {
            showProgressDialog("查询中,请稍后..", false);
            totalcount = 0;//总数据
            totalpage = 1;//总页数
            currentpage = 1;//当前第几页
            pagesize = 20;//每页多少条
            getInfos();
        } else {
            ToastUtil.showToast("请输入邮政编码!");
        }
    }

    /**
     * 查询
     */
    private void getInfos() {
        progress.setVisibility(View.VISIBLE);
        btQuery.setEnabled(false);
        OkGo.<String>get(API.POSTCODE_QUERY_URL)
                .tag(this)
                .params("key", "3f5f103d0e7d6ce980ed0dc0d5f8975c")
                .params("postcode", etPostcode.getText().toString())
                .params("page", currentpage)
                .params("pagesize", pagesize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        btQuery.setEnabled(true);
                        closeProgressDialog();
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        Logger.json(response.body());
                        try {
                            JSONObject JSON = new JSONObject(response.body());
                            int error_code = JSON.optInt("error_code");
                            if (error_code == 0) {
                                JSONObject result = new JSONObject(JSON.optString("result"));
                                if (currentpage == 1) {
                                    totalcount = result.optInt("totalcount");//总条数据
                                    totalpage = result.optInt("totalpage");//总页数
                                    tvSumData.setText("共" + totalcount + "条数据!");
                                    if (totalcount == 0) {
                                        tvNodata.setVisibility(View.VISIBLE);
                                        mRecycler.setVisibility(View.GONE);
                                        progress.setVisibility(View.GONE);
                                    } else {
                                        progress.setVisibility(View.GONE);
                                        tvNodata.setVisibility(View.GONE);
                                        mRecycler.setVisibility(View.VISIBLE);
                                    }
                                    mSumList.clear();
                                }
                                progress.setVisibility(View.GONE);
                                tvNodata.setVisibility(View.GONE);
                                mRecycler.setVisibility(View.VISIBLE);

                                JSONArray list = result.optJSONArray("list");
                                PostcodeEntity entity;
                                Gson gson = new Gson();
                                for (int i = 0; i < list.length(); i++) {
                                    entity = gson.fromJson(list.optString(i), PostcodeEntity.class);
                                    mSumList.add(entity);
                                }
                                mPostcodeItemAdapter.notifyDataSetChanged();
                                currentpage++;
                            } else {
                                tvSumData.setText(JSON.optString(API.SUCCESS_REASON));
                                ToastUtil.showToast(JSON.optString(API.SUCCESS_REASON));
                            }
                        } catch (Exception s) {
                            s.printStackTrace();
                            --currentpage;
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        --currentpage;
                        closeProgressDialog();
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        btQuery.setEnabled(true);
                        progress.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                        ToastUtil.showToast(API.ERROR_STRING);
                    }
                });
    }

}
