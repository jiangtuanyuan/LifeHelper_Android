package pers.life.helper.app;

import android.app.Application;
import android.content.Context;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pers.life.helper.BuildConfig;
import pers.life.helper.net.NetInterceptor;

//app入口
public class MyApp extends Application {

    private static MyApp sInstance;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sContext = getApplicationContext();
        initOkGo();
    }

    /**
     * 初始化Okgo
     */
    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(5000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(10000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        NetInterceptor mNetInterceptor = new NetInterceptor(this)
                .setShowRequestLog(BuildConfig.DEBUG)
                .setShowResponseLog(BuildConfig.DEBUG);
        builder.addInterceptor(mNetInterceptor);
        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(2);
    }

    //获取实例
    public synchronized static MyApp getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("App is null or dead.");
        }
        return sInstance;
    }

    //获取Context
    public static Context getContext() {
        return sContext;
    }
}