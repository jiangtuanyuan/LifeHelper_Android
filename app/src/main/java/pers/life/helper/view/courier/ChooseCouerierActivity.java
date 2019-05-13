package pers.life.helper.view.courier;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import pers.life.helper.R;
import pers.life.helper.entity.CouerierName;
import pers.life.helper.utils.EditUtlis;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;
import pers.life.helper.view.courier.adapter.ChooseNameAdapter;
import pers.life.helper.view.recipe.adapter.RecipeLiseAdapter;

public class ChooseCouerierActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, ChooseNameAdapter.ItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_edt)
    EditText mSearchEdt;
    @BindView(R.id.search_edt_clear)
    ImageView mSearchEdtClear;
    @BindView(R.id.search_img)
    ImageView searchImg;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    public static int RETURN_CODE = 0x1234;

    private ChooseNameAdapter mChooseNameAdapter;
    private List<CouerierName> mSumList = new ArrayList<>();
    private List<CouerierName> mList = new ArrayList<>();//用户显示选择列表
    private List<CouerierName> mCheckList = new ArrayList<>();//已经选项的用户列表

    public static void actionActivity(Activity mActivity, int retuencode) {
        Intent intent = new Intent(mActivity, ChooseCouerierActivity.class);
        mActivity.startActivityForResult(intent, retuencode);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_choose_couerier;
    }

    @Override
    protected void initVariables() {

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initViews(Bundle savedInstanceState) {
        initToolbarNav();
        setTitle("选择快递公司");
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        tvNodata.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        toolbar.inflateMenu(R.menu.menu_couerier_confirm);
        toolbar.setOnMenuItemClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChooseNameAdapter = new ChooseNameAdapter(mList);
        mChooseNameAdapter.setmItemClickListener(this);
        mChooseNameAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//添加缩放动画效果
        recyclerView.setAdapter(mChooseNameAdapter);

        StringBuilder builder = new StringBuilder();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("assets/" + "address.json");//android studio
            BufferedReader bufr = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bufr.readLine()) != null) {
                builder.append(line);
            }
            is.close();
            bufr.close();

            JSONArray jsonArray = new JSONArray(builder.toString());
            Gson gson = new Gson();
            CouerierName name;
            for (int i = 0; i < jsonArray.length(); i++) {
                name = gson.fromJson(jsonArray.optString(i), CouerierName.class);
                mSumList.add(name);
            }
            mList.addAll(mSumList);
            mChooseNameAdapter.notifyDataSetChanged();

            tvNodata.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        EditUtlis.EditTextChangedListener(mSearchEdt, mSearchEdtClear);
        RxTextView.textChanges(mSearchEdt)
                .subscribe(charSequence -> {
                    if (charSequence.length() > 0) {
                        mSearchEdtClear.setVisibility(View.VISIBLE);
                        Search(charSequence.toString() + "");
                    } else {
                        mList.clear();
                        mList.addAll(mSumList);
                        mChooseNameAdapter.notifyDataSetChanged();
                        mSearchEdtClear.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.confirm://确定
                if (mCheckList.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mCouerierName", mCheckList.get(0));
                    setResult(RESULT_OK, getIntent().putExtras(bundle));
                    finish();
                } else {
                    ToastUtil.showToast("请选择一个快递公司!");
                }
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 在总数据里面搜索名称
     */
    private void Search(String search) {
        mList.clear();
        for (CouerierName name : mSumList) {
            if (name.getCom().contains(search)) {
                mList.add(name);
            }
        }
        mChooseNameAdapter.notifyDataSetChanged();
    }

    /**
     * 列表的单击事件
     *
     * @param position
     * @param item
     */
    @Override
    public void onItemClick(int position, CouerierName item) {
        for (int i = 0; i < mCheckList.size(); i++) {
            CouerierName user = mCheckList.get(i);
            //判断是否存在
            if (user.getNo().equals(item.getNo())) {
                mCheckList.remove(i);
            }
        }
        //添加进选择的集合
        if (item.isIscheck()) {
            mCheckList.add(item);
        }
    }
}
