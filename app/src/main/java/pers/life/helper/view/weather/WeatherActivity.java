package pers.life.helper.view.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import pers.life.helper.R;
import pers.life.helper.utils.LocationAmpUtils;
import pers.life.helper.utils.LogUtil;
import pers.life.helper.utils.ToastUtil;
import pers.life.helper.view.base.BaseActivity;

public class WeatherActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, LocationAmpUtils.LocationListener {
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
    private RxPermissions mRxPermissions;
    private boolean isLocationing = true;//是否定位成功
    private boolean isFirstLoc = true;//正在定位

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
        initLocation();
    }

    /**
     * 初始化定位
     */
    @SuppressLint("CheckResult")
    public void initLocation() {
        tvCityNew.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
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
                        Drawable drawable = getResources().getDrawable(R.mipmap.ic_weather_location_gray);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                        tvCityName.setCompoundDrawables(drawable, null, null, null);
                        isLocationing = true;
                        tvCityName.setText("定位失败!");
                    }
                });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.city_query) {
            ToastUtil.showToast("搜索城市!");
        }
        return false;
    }

    @Override
    public void onLocation(AMapLocation location) {
        if (null != location) {
            if (isFirstLoc) {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_weather_location);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                tvCityName.setCompoundDrawables(drawable, null, null, null);
                tvCityName.setText(location.getDistrict());
                tvCityName.setTextColor(getResources().getColor(R.color.main_color));
                isFirstLoc = false;
                isLocationing = false;
            }
        } else {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_weather_location_gray);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            tvCityName.setCompoundDrawables(drawable, null, null, null);
            isLocationing = true;
            tvCityName.setText("定位失败");
            tvCityName.setTextColor(getResources().getColor(R.color.font_color));
        }
    }

    @OnClick(R.id.tv_city_new)
    public void onViewClicked() {
        isFirstLoc = true;
        initLocation();
    }
}
