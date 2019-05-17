package pers.life.helper.view.joke;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
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
import pers.life.helper.entity.JokeEntity;
import pers.life.helper.net.API;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;

public class JokeActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_return_top)
    ImageView ivReturnTop;
    @BindView(R.id.progress)
    ProgressBar progress;
    private JokeItemAdapter mJokeItemAdapter;
    private List<JokeEntity> mSumList = new ArrayList<>();//总数据

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_joke;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("随机笑话");
        initToolbarNav();
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(this);//进行下拉刷新的监听
        refreshLayout.setOnLoadMoreListener(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mJokeItemAdapter = new JokeItemAdapter(mSumList);
        mJokeItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mRecycler.setAdapter(mJokeItemAdapter);

        progress.setVisibility(View.GONE);
        // 开启滑动删除
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mJokeItemAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecycler);
        mJokeItemAdapter.enableSwipeItem();
        //获取数据
        getRandomJoke(true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getRandomJoke(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mSumList.clear();
        mJokeItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mJokeItemAdapter.notifyDataSetChanged();

        progress.setVisibility(View.VISIBLE);
        tvNodata.setVisibility(View.GONE);
        mRecycler.setVisibility(View.GONE);

        getRandomJoke(true);
    }

    /**
     * 获取十条随机笑话
     */
    private void getRandomJoke(final boolean tf) {
        OkGo.<String>get(API.JOKE_QUERY_URL)
                .tag(this)
                .params("key", "0eac712681940104050d3d96d03b87cd")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        closeProgressDialog();
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        Logger.json(response.body());
                        try {
                            JSONObject JSON = new JSONObject(response.body());
                            int error_code = JSON.optInt("error_code");
                            if (error_code == 0) {
                                JSONArray result = JSON.optJSONArray("result");
                                if (tf) {
                                    if (result.length() > 0) {
                                        progress.setVisibility(View.GONE);
                                        tvNodata.setVisibility(View.GONE);
                                        mRecycler.setVisibility(View.VISIBLE);
                                    } else {
                                        progress.setVisibility(View.GONE);
                                        tvNodata.setVisibility(View.VISIBLE);
                                        mRecycler.setVisibility(View.GONE);
                                    }
                                }
                                progress.setVisibility(View.GONE);
                                tvNodata.setVisibility(View.GONE);
                                mRecycler.setVisibility(View.VISIBLE);

                                JokeEntity entity;
                                Gson gson = new Gson();
                                for (int i = 0; i < result.length(); i++) {
                                    entity = gson.fromJson(result.optString(i), JokeEntity.class);
                                    mSumList.add(entity);
                                }
                                mJokeItemAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtil.showToast(JSON.optString(API.SUCCESS_REASON));
                            }
                        } catch (Exception s) {
                            s.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        closeProgressDialog();
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        ToastUtil.showToast(API.ERROR_STRING);
                    }
                });
    }

    @OnClick(R.id.iv_return_top)
    public void onViewClicked() {
        mRecycler.scrollToPosition(0);
    }
}
