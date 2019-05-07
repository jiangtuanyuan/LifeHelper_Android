package pers.life.helper.utils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;


import java.util.List;

import pers.life.helper.app.MyApp;

/**
 * $activityName
 *
 * @author LiuTao
 * @date 2019/3/29/029
 */


public class LocationAmpUtils implements GeocodeSearch.OnGeocodeSearchListener {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private static LocationAmpUtils mLocationAmpUtils = null;
    private double mLat = 0;
    private double mLng = 0;
    private LocationListener mLocationListener;
    private RegeocodeSearchedListener mRegeocodeSearchedlistener;
    private RegeocodeSearchedLatlngListener mSearchedLatlngListener;
    private String mCode = "";
    private String address = "";
    private GeocodeSearch mGeocoderSearch;

    public static synchronized LocationAmpUtils getInstance() {
        if (null == mLocationAmpUtils) {
            mLocationAmpUtils = new LocationAmpUtils();
        }
        return mLocationAmpUtils;
    }

    public void setLocationListener(LocationListener locationListener) {
        mLocationListener = locationListener;
    }


    public LocationAmpUtils() {
        initLocation();
    }

    public void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(MyApp.getContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(mAMapLocationListener);
        locationClient.startLocation();
        mGeocoderSearch = new GeocodeSearch(MyApp.getContext());
        mGeocoderSearch.setOnGeocodeSearchListener(this);
    }


    public void startLocation() {
        locationClient.startLocation();
    }

    /**
     * 停止定位
     */
    public void stoplocation() {
        locationClient.stopLocation();
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(4000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setMockEnable(false); //是否允许模拟未知
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    if (mLocationListener != null) {
                        mLocationListener.onLocation(location);
                    }
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");
                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    mLat = location.getLatitude();
                    mLng = location.getLongitude();
                    mCode = location.getAdCode();
                    address = location.getAddress();
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }

                LogUtil.i("手机定位", sb.toString());
            } else {
                String errText = "定位失败," + location.getErrorCode() + ": " + location.getErrorInfo();
                LogUtil.i("Amap", errText);
            }
        }
    };


    public String getCode() {
        return mCode;
    }

    public double getLat() {
        return mLat;
    }


    public double getLng() {
        return mLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String addressName = "";

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint, RegeocodeSearchedListener listener) {
        this.mRegeocodeSearchedlistener = listener;
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        mGeocoderSearch.getFromLocationAsyn(query);
    }

    /**
     * 响应逆地理编码
     */
    public void getAddressLatlng(final String name, RegeocodeSearchedLatlngListener listener) {
        this.mSearchedLatlngListener = listener;
        GeocodeQuery query = new GeocodeQuery(name, "");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        mGeocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                addressName = result.getRegeocodeAddress().getFormatAddress();
                if (mRegeocodeSearchedlistener != null) {
                    mRegeocodeSearchedlistener.onRegeocodeSearched(addressName);
                }
            }

        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);
                if (address != null) {
                    if (mSearchedLatlngListener != null) {
                        mSearchedLatlngListener.onGeocodeSearched(address.getLatLonPoint());
                    }
                }
            }
        }
    }

    public interface LocationListener {
        void onLocation(AMapLocation location);

    }

    public interface RegeocodeSearchedListener {
        void onRegeocodeSearched(String addressName);
    }

    public interface RegeocodeSearchedLatlngListener {
        void onGeocodeSearched(LatLonPoint latLonPoint);
    }
}
