package pers.life.helper.utils;

import android.annotation.SuppressLint;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pers.life.helper.view.smart.plant.entity.CarResult;
import pers.life.helper.view.smart.plant.entity.PlantAnimalResult;

/**
 * Created by jiang on 2019/5/17.
 * 百度图像识别工具类
 */
public class AipImageUtils {
    /**
     * APP_ID
     */
    private final String APP_ID = "16268450";
    /**
     * API_KEY
     */
    private final String API_KEY = "rijYHVoqoRurnqnQfoEKDKnD";
    /**
     * SECRET_KEY
     */
    private final String SECRET_KEY = "DDP8EUbvXEHBFndTcweaOT6u6yGybGR1";
    /**
     * 单例
     */
    private final AipImageClassify mAipImageClassify;
    /**
     * Gson 对象
     */
    private Gson gson;

    private AipImageUtils() {
        LogUtil.e("AipImageUtils", "--初始化--AipImageUtils()");
        mAipImageClassify = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
        mAipImageClassify.setConnectionTimeoutInMillis(6000);
        mAipImageClassify.setSocketTimeoutInMillis(60000);
        gson = new Gson();
    }

    //静态内部类单例模式
    public static AipImageUtils getInstance() {
        return SPInInstance.instance;
    }

    private static class SPInInstance {
        private static final AipImageUtils instance = new AipImageUtils();
    }

    /**
     * 植物识别
     *
     * @param image_path 本地图片的路径
     * @param baikeNum   返回百度百科的查询数量
     * @return
     */
    public void PlantDetect(String image_path, int baikeNum, OnResults mOnResults, CompositeDisposable mDisposable) {
        if (checkAIPImageStatus()) {
            mOnResults.OnError("初始化失败!");
        } else {
            HashMap<String, String> options = new HashMap<>();
            options.put("baike_num", String.valueOf(baikeNum));//返回百科信息的结果数，默认不返回
            Observable.create((ObservableOnSubscribe<JSONObject>) e -> {
                e.onNext(mAipImageClassify.plantDetect(image_path, options));
                e.onComplete(); //结束
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONObject>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            mDisposable.add(d);
                        }

                        @Override
                        public void onNext(@NonNull JSONObject res) {
                            try {
                                Logger.json(res.toString());
                                PlantAnimalResult result = gson.fromJson(res.toString(), PlantAnimalResult.class);
                                mOnResults.OnSuccessful(result);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                mOnResults.OnError("数据异常!");
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mOnResults.OnError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    /**
     * 动物识别
     *
     * @param image_path 本地图片的路径
     * @param baikeNum   返回百度百科的查询数量
     * @return
     */
    public void AnimalDetect(String image_path, int top_num, int baikeNum, OnResults mOnResults, CompositeDisposable mDisposable) {
        if (checkAIPImageStatus()) {
            mOnResults.OnError("初始化失败!");
        } else {
            HashMap<String, String> options = new HashMap<>();
            options.put("top_num", top_num + "");
            options.put("baike_num", baikeNum + "");
            Observable.create((ObservableOnSubscribe<JSONObject>) e -> {
                e.onNext(mAipImageClassify.animalDetect(image_path, options));
                e.onComplete();
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONObject>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            mDisposable.add(d);
                        }

                        @Override
                        public void onNext(@NonNull JSONObject res) {
                            try {
                                Logger.json(res.toString());
                                PlantAnimalResult result = gson.fromJson(res.toString(), PlantAnimalResult.class);
                                mOnResults.OnSuccessful(result);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                mOnResults.OnError("数据异常!");
                            }

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mOnResults.OnError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    /**
     * 车辆识别
     *
     * @return
     */
    @SuppressLint("CheckResult")
    public void CarDetect(String image_path, int top_num, int baikeNum, OnCarResults results, CompositeDisposable mDisposable) {
        if (checkAIPImageStatus()) {
            results.OnError("初始化失败!");
        } else {
            HashMap<String, String> options = new HashMap<>();
            options.put("top_num", top_num + "");
            options.put("baike_num", baikeNum + "");

            Observable.create((ObservableOnSubscribe<JSONObject>) emitter -> {
                emitter.onNext(mAipImageClassify.carDetect(image_path, options));
                emitter.onComplete();
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable.add(d);

                        }
                        @Override
                        public void onNext(JSONObject json) {
                            try {
                                Logger.json(json.toString());
                                CarResult result = gson.fromJson(json.toString(), CarResult.class);
                                results.OnSuccessful(result);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                results.OnError("数据异常!");
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            results.OnError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }


    private boolean checkAIPImageStatus() {
        if (mAipImageClassify == null) {
            return true;
        }
        return false;
    }

    public interface OnResults {
        void OnSuccessful(PlantAnimalResult result);

        void OnError(String erros);
    }

    public interface OnCarResults {
        void OnSuccessful(CarResult result);

        void OnError(String erros);
    }
}
