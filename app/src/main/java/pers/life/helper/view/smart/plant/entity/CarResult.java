package pers.life.helper.view.smart.plant.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarResult {


    /**
     * log_id : 4086212218842203806
     * location_result : {"width":447,"top":226,"height":209,"left":188}
     * result : [{"baike_info":{"baike_url":"http://baike.baidu.com/item/%E5%B8%83%E5%8A%A0%E8%BF%AAChiron/20419512","description":"布加迪Chiron是法国跑车品牌布加迪出品的豪华超跑车。配置四涡轮增压发动机，420 公里每小时，有23种颜色的选择，售价高达260万美元。"},"score":0.98793351650238,"name":"布加迪Chiron","year":"无年份信息"},{"score":0.0021970034576952,"name":"奥迪RS5","year":"2011-2017"},{"score":0.0021096928976476,"name":"奥迪RS4","year":"无年份信息"},{"score":0.0015581247862428,"name":"奥迪RS7","year":"2014-2016"},{"score":8.2337751518935E-4,"name":"布加迪威航","year":"2004-2015"}]
     * color_result : 颜色无法识别
     */

    private long log_id;
    private LocationResultBean location_result;
    private String color_result;
    private List<ResultBean> result;

    public static CarResult objectFromData(String str) {

        return new Gson().fromJson(str, CarResult.class);
    }

    public static CarResult objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CarResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CarResult> arrayCarResultFromData(String str) {

        Type listType = new TypeToken<ArrayList<CarResult>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<CarResult> arrayCarResultFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<CarResult>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public LocationResultBean getLocation_result() {
        return location_result;
    }

    public void setLocation_result(LocationResultBean location_result) {
        this.location_result = location_result;
    }

    public String getColor_result() {
        return color_result;
    }

    public void setColor_result(String color_result) {
        this.color_result = color_result;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class LocationResultBean {
        /**
         * width : 447
         * top : 226
         * height : 209
         * left : 188
         */

        private int width;
        private int top;
        private int height;
        private int left;

        public static LocationResultBean objectFromData(String str) {

            return new Gson().fromJson(str, LocationResultBean.class);
        }

        public static LocationResultBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), LocationResultBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<LocationResultBean> arrayLocationResultBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<LocationResultBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<LocationResultBean> arrayLocationResultBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<LocationResultBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }
    }

    public static class ResultBean {
        /**
         * baike_info : {"baike_url":"http://baike.baidu.com/item/%E5%B8%83%E5%8A%A0%E8%BF%AAChiron/20419512","description":"布加迪Chiron是法国跑车品牌布加迪出品的豪华超跑车。配置四涡轮增压发动机，420 公里每小时，有23种颜色的选择，售价高达260万美元。"}
         * score : 0.98793351650238
         * name : 布加迪Chiron
         * year : 无年份信息
         */

        private BaikeInfoBean baike_info;
        private double score;
        private String name;
        private String year;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public static ResultBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), ResultBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<ResultBean> arrayResultBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<ResultBean> arrayResultBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<ResultBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public BaikeInfoBean getBaike_info() {
            return baike_info;
        }

        public void setBaike_info(BaikeInfoBean baike_info) {
            this.baike_info = baike_info;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public static class BaikeInfoBean {
            /**
             * baike_url : http://baike.baidu.com/item/%E5%B8%83%E5%8A%A0%E8%BF%AAChiron/20419512
             * description : 布加迪Chiron是法国跑车品牌布加迪出品的豪华超跑车。配置四涡轮增压发动机，420 公里每小时，有23种颜色的选择，售价高达260万美元。
             */

            private String baike_url;
            private String image_url;
            private String description;

            public static BaikeInfoBean objectFromData(String str) {

                return new Gson().fromJson(str, BaikeInfoBean.class);
            }

            public static BaikeInfoBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), BaikeInfoBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<BaikeInfoBean> arrayBaikeInfoBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<BaikeInfoBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<BaikeInfoBean> arrayBaikeInfoBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<BaikeInfoBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getBaike_url() {
                return baike_url;
            }

            public void setBaike_url(String baike_url) {
                this.baike_url = baike_url;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }
        }
    }
}
