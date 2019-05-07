package pers.life.helper.net;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;


import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import pers.life.helper.utils.ToastUtil;

/**
 * printer net Request DATA and Response
 * Jiang
 * 2019-01-15
 */
public class NetInterceptor implements Interceptor {
    private static final String TAG = "NetInterceptor";
    private Context mContext;
    private boolean showRequestLog = false;//是否显示请求的参数
    private boolean showResponseLog = false;//是否显示响应参数
    private List<String> mNoParameters = new ArrayList<>();//不需要添加公共参数的接口
    private Map<String, String> mPublisMap = new HashMap<>();//公共参数的key and value

    public NetInterceptor addParametersMap(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            this.mPublisMap.put(key, value);
        }
        return this;
    }

    public NetInterceptor setShowRequestLog(boolean showRequestLog) {
        this.showRequestLog = showRequestLog;
        return this;
    }

    public NetInterceptor setShowResponseLog(boolean showResponseLog) {
        this.showResponseLog = showResponseLog;
        return this;
    }

    public NetInterceptor addNoParametersApi(String api) {
        mNoParameters.add(api);
        return this;
    }

    public NetInterceptor addNoParametersApi(List<String> apis) {
        mNoParameters.addAll(apis);
        return this;
    }

    public NetInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtils.isNetworkAvailable(mContext)) {
            Response response;
            boolean isadddid = true;
            String RequestUrl = request.url().toString();
            for (String str : mNoParameters) {
                if (RequestUrl.contains(str)) {
                    isadddid = false;
                    break;
                }
            }
            if (isadddid) {
                //判断是get请求还是post请求
                if ("GET".equals(request.method())) {
                    String url;
                    if (request.url().toString().contains("?")) {
                        String str = "";
                        for (Map.Entry<String, String> entry : mPublisMap.entrySet()) {
                            str = str + "&" + entry.getKey() + "=" + entry.getValue();
                        }
                        url = request.url().toString() + str;
                    } else {
                        String str = "?";
                        int i = 1;
                        for (Map.Entry<String, String> entry : mPublisMap.entrySet()) {
                            if (i == 1) {
                                str = str + entry.getKey() + "=" + entry.getValue();
                                i = 2;
                            } else {
                                str = str + "&" + entry.getKey() + "=" + entry.getValue();
                            }
                        }
                        url = request.url().toString() + str;
                    }
                    Request.Builder builder = request.newBuilder();
                    builder.get().url(url);
                    request = builder.build();
                    response = chain.proceed(request);
                } else {
                    //如果是表单的形式提交
                    if (request.body() instanceof FormBody) {
                        FormBody.Builder builder = new FormBody.Builder();
                        RequestBody requestBody = request.body();
                        FormBody body = (FormBody) requestBody;
                        for (Map.Entry<String, String> entry : mPublisMap.entrySet()) {
                            builder.add(entry.getKey(), entry.getValue());
                        }
                        //将以前的参数添加
                        for (int i = 0; i < body.size(); i++) {
                            builder.addEncoded(body.encodedName(i), body.encodedValue(i));//不进行URL编码添加
                        }
                        request = request.newBuilder().post(builder.build()).build();
                    } else {
                        FormBody.Builder builder = new FormBody.Builder();
                        for (Map.Entry<String, String> entry : mPublisMap.entrySet()) {
                            builder.add(entry.getKey(), entry.getValue());
                        }
                        request = request.newBuilder().post(builder.build()).build();
                    }
                    response = chain.proceed(request);
                }
            } else {
                response = chain.proceed(request);
            }
            //是否显示请求日志
            if (showRequestLog) {
                LogForRequest(request);
            }
            //设置读入时间
            int maxAge = 60;
            response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
            //是否显示响应日志
            if (showResponseLog) {
                return LogForResponse(response);
            } else {
                return response;
            }

        } else {
            ToastUtil.showToast("当前无网络!为你智能加载缓存");
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            int maxStale = 60 * 60 * 24 * 3;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }

    /**
     * 响应参数
     *
     * @param response
     * @return
     */
    private Response LogForResponse(Response response) {
        try {
            Log.d(TAG, "========响应参数(Start)=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.d(TAG, "url : " + clone.request().url());
            Log.d(TAG, "code : " + clone.code());
            Log.d(TAG, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message())) {
                Log.d(TAG, "message : " + clone.message());
            }
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    Log.d(TAG, "responseBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = body.string();
                        Log.d(TAG, "responseBody's content: " + resp);
                        Logger.json(resp);
                        body = ResponseBody.create(mediaType, resp);
                        Log.d(TAG, "========响应参数(End)=======");
                        return response.newBuilder().body(body).build();
                    } else {
                        Log.d(TAG, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Log.e(TAG, "========响应参数(End)=======");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 请求参数
     *
     * @param request
     */
    private void LogForRequest(Request request) {
        StringBuffer RequestStr = new StringBuffer();
        try {
            String url = request.url().toString();
            RequestStr.append("========请求参数(Start)=======\n\n");
            RequestStr.append("请求方式(Method): " + request.method() + "\n\n");
            RequestStr.append("请求地址(URL):" + url + "\n\n");
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    RequestStr.append("携带参数:" + bodyToString(requestBody, mediaType) + "\n\n");
                }
            }
            RequestStr.append("========请求参数(End)=======");
            Logger.d(RequestStr.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 响应数据的类型
     *
     * @param mediaType
     * @return
     */
    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
            ) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取请求内容
     *
     * @param requestBody
     * @param mediaType
     * @return
     */
    private String bodyToString(final RequestBody requestBody, MediaType mediaType) {
        try {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            charset = mediaType.charset(charset);
            return buffer.readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
