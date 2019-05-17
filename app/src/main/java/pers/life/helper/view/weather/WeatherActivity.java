package pers.life.helper.view.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pers.life.helper.R;
import pers.life.helper.entity.WeatherEntity;
import pers.life.helper.net.API;
import pers.life.helper.utils.LocationAmpUtils;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;

public class WeatherActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, LocationAmpUtils.LocationListener, OnRefreshListener, QueryCityNameDialog.OnCityNameOk {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.tv_city_new)
    TextView tvCityNew;
    @BindView(R.id.re_top)
    RelativeLayout reTop;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_temp)
    TextView tvTemp;
    @BindView(R.id.tv_wind_direction)
    TextView tvWindDirection;
    @BindView(R.id.tv_wind_strength)
    TextView tvWindStrength;
    @BindView(R.id.tv_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_today_top_time)
    TextView tvTodayTopTime;
    @BindView(R.id.tv_today_weather)
    TextView tvTodayWeather;
    @BindView(R.id.tv_today_temperature)
    TextView tvTodayTemperature;
    @BindView(R.id.tv_today_wind)
    TextView tvTodayWind;
    @BindView(R.id.tv_today_dressing)
    TextView tvTodayDressing;
    @BindView(R.id.tv_today_uv)
    TextView tvTodayUv;
    @BindView(R.id.tv_today_comfort)
    TextView tvTodayComfort;
    @BindView(R.id.tv_today_wash)
    TextView tvTodayWash;
    @BindView(R.id.tv_today_travel)
    TextView tvTodayTravel;
    @BindView(R.id.tv_today_exercise)
    TextView tvTodayExercise;
    @BindView(R.id.tv_today_drying)
    TextView tvTodayDrying;
    @BindView(R.id.tv_today_advice)
    TextView tvTodayAdvice;
    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private RxPermissions mRxPermissions;
    private boolean isLocationing = true;//是否定位成功
    private boolean isFirstLoc = true;//正在定位

    private Drawable mDrawableGray;
    private Drawable mDrawableGreen;

    private String mCityName = "";
    private WeatherEntity mWeatherEntity;

    private WeatherAdapter mWeatherAdapter;
    private List<WeatherEntity.FutureBean> mSumList = new ArrayList<>();//总数据

    private FragmentManager fragmentManager;
    private QueryCityNameDialog mQueryCityNameDialog;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_weather;
    }

    @Override
    protected void initVariables() {


    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        toolbar.inflateMenu(R.menu.activity_weather_selete_city);
        toolbar.setOnMenuItemClickListener(this);
        setTitle("天气查询");
        initToolbarNav();
        mDrawableGray = getResources().getDrawable(R.mipmap.ic_weather_location_gray);
        mDrawableGray.setBounds(0, 0, mDrawableGray.getMinimumWidth(), mDrawableGray.getMinimumHeight()); //设置边界
        mDrawableGreen = getResources().getDrawable(R.mipmap.ic_weather_location);
        mDrawableGreen.setBounds(0, 0, mDrawableGreen.getMinimumWidth(), mDrawableGreen.getMinimumHeight()); //设置边界
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(this);//进行下拉刷新的监听
        initLocation();
    }

    /**
     * 初始化定位
     */
    @SuppressLint("CheckResult")
    public void initLocation() {
        tvCityName.setCompoundDrawables(mDrawableGray, null, null, null);
        tvCityNew.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvCityName.setTextColor(getResources().getColor(R.color.font_color));
        tvCityName.setText("定位中..");
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(this);
        }
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        LocationAmpUtils.getInstance().setLocationListener(WeatherActivity.this);
                    } else {
                        tvCityName.setCompoundDrawables(mDrawableGray, null, null, null);
                        tvCityName.setText("定位失败!");
                        tvCityName.setTextColor(getResources().getColor(R.color.font_color));
                        isLocationing = true;
                    }
                });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.city_query) {
            if (fragmentManager == null) {
                fragmentManager = getSupportFragmentManager();
            }
            if (mQueryCityNameDialog == null) {
                mQueryCityNameDialog = new QueryCityNameDialog();
            }
            mQueryCityNameDialog.setmOnCityNameOk(this);
            if (!mQueryCityNameDialog.isAdded()) {
                mQueryCityNameDialog.show(fragmentManager, "mQueryCityNameDialog");
            }
        }
        return false;
    }

    @Override
    public void onLocation(AMapLocation location) {
        refreshLayout.finishRefresh();
        if (null != location) {
            if (isFirstLoc) {
                mCityName = location.getCity();
                tvCityName.setCompoundDrawables(mDrawableGreen, null, null, null);
                tvCityName.setText(mCityName + location.getDistrict());
                tvCityName.setTextColor(getResources().getColor(R.color.main_color));
                isFirstLoc = false;
                getWeatherData();
            } else {
                isLocationing = false;
            }
        } else {
            tvCityName.setCompoundDrawables(mDrawableGray, null, null, null);
            tvCityName.setText("定位失败");
            tvCityName.setTextColor(getResources().getColor(R.color.font_color));
            isLocationing = false;
        }
    }

    @OnClick(R.id.tv_city_new)
    public void onViewClicked() {
        isFirstLoc = true;
        progress.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        initLocation();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isFirstLoc = true;
        progress.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        initLocation();
    }

    /**
     * 获取天气数据
     */
    private void getWeatherData() {
        progress.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        OkGo.<String>get(API.WEATHER_QUERT)
                .tag(this)
                .params("key", "e598ae1920638764eca2acde743aae4d")
                .params("format", "2")
                .params("cityname", mCityName)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        closeProgressDialog();
                        refreshLayout.finishRefresh();
                        Logger.json(response.body());
                        try {
                            JSONObject JSON = new JSONObject(response.body());
                            int error_code = JSON.optInt("error_code");
                            if (error_code == 0) {
                                Gson gson = new Gson();
                                mWeatherEntity = gson.fromJson(JSON.optString("result"), WeatherEntity.class);
                                initToView();
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
                        ToastUtil.showToast(API.ERROR_STRING);
                    }
                });
    }

    private void initToView() {
        progress.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        //1.风向
        tvTemp.setText(mWeatherEntity.getSk().getTemp() + "C°");
        tvWindDirection.setText("风向:" + mWeatherEntity.getSk().getWind_direction());
        tvWindStrength.setText("风力:" + mWeatherEntity.getSk().getWind_strength());
        tvHumidity.setText("湿度:" + mWeatherEntity.getSk().getHumidity());
        tvTime.setText("更新时间:" + mWeatherEntity.getSk().getTime());
        //2.今日天气
        tvTodayTopTime.setText(mWeatherEntity.getToday().getDate_y() + " 【" + mWeatherEntity.getToday().getWeek() + "】");
        tvTodayWeather.setText("天气:" + mWeatherEntity.getToday().getWeather());
        tvTodayTemperature.setText("温度差:" + mWeatherEntity.getToday().getTemperature());
        tvTodayWind.setText("温度差:" + mWeatherEntity.getToday().getWind());
        tvTodayDressing.setText("风舒适度:" + mWeatherEntity.getToday().getDressing_index());
        tvTodayUv.setText("紫外线:" + mWeatherEntity.getToday().getUv_index());
        tvTodayComfort.setText("安慰指数:" + mWeatherEntity.getToday().getComfort_index());
        tvTodayWash.setText("洗衣指数:" + mWeatherEntity.getToday().getWash_index());
        tvTodayTravel.setText("旅游指数:" + mWeatherEntity.getToday().getTravel_index());
        tvTodayExercise.setText("运动指数:" + mWeatherEntity.getToday().getExercise_index());
        tvTodayDrying.setText("烘干指数:" + mWeatherEntity.getToday().getDrying_index());
        tvTodayAdvice.setText("穿着建议:" + mWeatherEntity.getToday().getDressing_advice());
        //3.未来七天
        mSumList.clear();
        mSumList.addAll(mWeatherEntity.getFuture());
        mWeatherAdapter = new WeatherAdapter(mSumList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mWeatherAdapter);
    }

    @Override
    public void onCituNmae(String name) {
        mCityName = name;
        tvCityName.setText(mCityName);
        getWeatherData();
    }
}
