package pers.life.helper.net;

/**
 * author : jiang
 * date   : 2019/5/5  22:04
 * desc   :
 */
public class API {
    public final static String APP_KEY = "7095b604f4fd60d85cf524f8bdeae4fd";
    public final static String SUCCESS_CODE = "resultcode";
    public final static String ERROR_CODE = "error_code";
    public final static String SUCCESS_REASON = "reason";
    public final static String SUCCESS_DATA = "result";

    //异常
    public final static String ERROR_STRING = "网络不好,请重试!";
    //ip 查询
    public final static String IP_QUERY_URL = "http://apis.juhe.cn/ip/ipNew";
    //号码归属地
    public final static String PHONE_QUERY_URL = "http://apis.juhe.cn/mobile/get";
    //身份证
    public final static String CARD_QUERY_URL = "http://apis.juhe.cn/idcard/index";
    //邮编
    public final static String POSTCODE_QUERY_URL = "http://v.juhe.cn/postcode/query";

}
