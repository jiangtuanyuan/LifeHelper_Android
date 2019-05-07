package pers.life.helper.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WeatherEntity {
    /**
     * sk : {"temp":"18","wind_direction":"东风","wind_strength":"4级","humidity":"29%","time":"17:33"}
     * today : {"temperature":"14℃~19℃","weather":"多云转阴","weather_id":{"fa":"01","fb":"02"},"wind":"东北风微风","week":"星期二","city":"苏州","date_y":"2019年05月07日","dressing_index":"舒适","dressing_advice":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。","uv_index":"最弱","comfort_index":"","wash_index":"较适宜","travel_index":"较适宜","exercise_index":"较适宜","drying_index":""}
     * future : [{"temperature":"14℃~19℃","weather":"多云转阴","weather_id":{"fa":"01","fb":"02"},"wind":"东北风微风","week":"星期二","date":"20190507"},{"temperature":"13℃~20℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"南风微风","week":"星期三","date":"20190508"},{"temperature":"16℃~24℃","weather":"多云转晴","weather_id":{"fa":"01","fb":"00"},"wind":"东风微风","week":"星期四","date":"20190509"},{"temperature":"15℃~24℃","weather":"多云转晴","weather_id":{"fa":"01","fb":"00"},"wind":"东南风微风","week":"星期五","date":"20190510"},{"temperature":"15℃~25℃","weather":"多云转阴","weather_id":{"fa":"01","fb":"02"},"wind":"东南风微风","week":"星期六","date":"20190511"},{"temperature":"15℃~24℃","weather":"多云转晴","weather_id":{"fa":"01","fb":"00"},"wind":"东南风微风","week":"星期日","date":"20190512"},{"temperature":"16℃~24℃","weather":"多云转晴","weather_id":{"fa":"01","fb":"00"},"wind":"东风微风","week":"星期一","date":"20190513"}]
     */

    private SkBean sk;
    private TodayBean today;
    private List<FutureBean> future;

    public static WeatherEntity objectFromData(String str) {

        return new Gson().fromJson(str, WeatherEntity.class);
    }

    public static WeatherEntity objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), WeatherEntity.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<WeatherEntity> arrayWeatherFromData(String str) {

        Type listType = new TypeToken<ArrayList<WeatherEntity>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<WeatherEntity> arrayWeatherFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<WeatherEntity>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public SkBean getSk() {
        return sk;
    }

    public void setSk(SkBean sk) {
        this.sk = sk;
    }

    public TodayBean getToday() {
        return today;
    }

    public void setToday(TodayBean today) {
        this.today = today;
    }

    public List<FutureBean> getFuture() {
        return future;
    }

    public void setFuture(List<FutureBean> future) {
        this.future = future;
    }

    public static class SkBean {
        /**
         * temp : 18  【温度】
         * wind_direction : 东风 【风向；风向选择；风的角度】
         * wind_strength : 4级 【风力】
         * humidity : 29% 【湿度】
         * time : 17:33 【更新时间】
         */

        private String temp;
        private String wind_direction;
        private String wind_strength;
        private String humidity;
        private String time;

        public static SkBean objectFromData(String str) {

            return new Gson().fromJson(str, SkBean.class);
        }

        public static SkBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), SkBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<SkBean> arraySkBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<SkBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<SkBean> arraySkBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<SkBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getWind_direction() {
            return wind_direction;
        }

        public void setWind_direction(String wind_direction) {
            this.wind_direction = wind_direction;
        }

        public String getWind_strength() {
            return wind_strength;
        }

        public void setWind_strength(String wind_strength) {
            this.wind_strength = wind_strength;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class TodayBean {
        /**
         * temperature : 14℃~19℃  【温度】
         * weather : 多云转阴       【天气】
         * weather_id : {"fa":"01","fb":"02"}  【天气ID 什么转什么】
         * wind : 东北风微风  【】
         * week : 星期二
         * city : 苏州
         * date_y : 2019年05月07日
         * dressing_index : 舒适  【】
         * dressing_advice : 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
         * uv_index : 最弱 【紫外线】
         * comfort_index :       【安慰指数】
         * wash_index : 较适宜   【洗衣指数】
         * travel_index : 较适宜   【旅游指数】
         * exercise_index : 较适宜 【运动指数】
         * drying_index : 烘干指数
         */

        private String temperature;
        private String weather;
        private WeatherIdBean weather_id;
        private String wind;
        private String week;
        private String city;
        private String date_y;
        private String dressing_index;
        private String dressing_advice;
        private String uv_index;
        private String comfort_index;
        private String wash_index;
        private String travel_index;
        private String exercise_index;
        private String drying_index;

        public static TodayBean objectFromData(String str) {

            return new Gson().fromJson(str, TodayBean.class);
        }

        public static TodayBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), TodayBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<TodayBean> arrayTodayBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<TodayBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<TodayBean> arrayTodayBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<TodayBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public WeatherIdBean getWeather_id() {
            return weather_id;
        }

        public void setWeather_id(WeatherIdBean weather_id) {
            this.weather_id = weather_id;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDate_y() {
            return date_y;
        }

        public void setDate_y(String date_y) {
            this.date_y = date_y;
        }

        public String getDressing_index() {
            return dressing_index;
        }

        public void setDressing_index(String dressing_index) {
            this.dressing_index = dressing_index;
        }

        public String getDressing_advice() {
            return dressing_advice;
        }

        public void setDressing_advice(String dressing_advice) {
            this.dressing_advice = dressing_advice;
        }

        public String getUv_index() {
            return uv_index;
        }

        public void setUv_index(String uv_index) {
            this.uv_index = uv_index;
        }

        public String getComfort_index() {
            return comfort_index;
        }

        public void setComfort_index(String comfort_index) {
            this.comfort_index = comfort_index;
        }

        public String getWash_index() {
            return wash_index;
        }

        public void setWash_index(String wash_index) {
            this.wash_index = wash_index;
        }

        public String getTravel_index() {
            return travel_index;
        }

        public void setTravel_index(String travel_index) {
            this.travel_index = travel_index;
        }

        public String getExercise_index() {
            return exercise_index;
        }

        public void setExercise_index(String exercise_index) {
            this.exercise_index = exercise_index;
        }

        public String getDrying_index() {
            return drying_index;
        }

        public void setDrying_index(String drying_index) {
            this.drying_index = drying_index;
        }

        public static class WeatherIdBean {
            /**
             * fa : 01
             * fb : 02
             */

            private String fa;
            private String fb;

            public static WeatherIdBean objectFromData(String str) {

                return new Gson().fromJson(str, WeatherIdBean.class);
            }

            public static WeatherIdBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), WeatherIdBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<WeatherIdBean> arrayWeatherIdBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<WeatherIdBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<WeatherIdBean> arrayWeatherIdBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<WeatherIdBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getFa() {
                return fa;
            }

            public void setFa(String fa) {
                this.fa = fa;
            }

            public String getFb() {
                return fb;
            }

            public void setFb(String fb) {
                this.fb = fb;
            }
        }
    }

    public static class FutureBean {
        /**
         * temperature : 14℃~19℃
         * weather : 多云转阴
         * weather_id : {"fa":"01","fb":"02"}
         * wind : 东北风微风
         * week : 星期二
         * date : 20190507
         */

        private String temperature;
        private String weather;
        private WeatherIdBeanX weather_id;
        private String wind;
        private String week;
        private String date;

        public static FutureBean objectFromData(String str) {

            return new Gson().fromJson(str, FutureBean.class);
        }

        public static FutureBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), FutureBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<FutureBean> arrayFutureBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<FutureBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<FutureBean> arrayFutureBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<FutureBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public WeatherIdBeanX getWeather_id() {
            return weather_id;
        }

        public void setWeather_id(WeatherIdBeanX weather_id) {
            this.weather_id = weather_id;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public static class WeatherIdBeanX {
            /**
             * fa : 01
             * fb : 02
             */

            private String fa;
            private String fb;

            public static WeatherIdBeanX objectFromData(String str) {

                return new Gson().fromJson(str, WeatherIdBeanX.class);
            }

            public static WeatherIdBeanX objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), WeatherIdBeanX.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<WeatherIdBeanX> arrayWeatherIdBeanXFromData(String str) {

                Type listType = new TypeToken<ArrayList<WeatherIdBeanX>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<WeatherIdBeanX> arrayWeatherIdBeanXFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<WeatherIdBeanX>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getFa() {
                return fa;
            }

            public void setFa(String fa) {
                this.fa = fa;
            }

            public String getFb() {
                return fb;
            }

            public void setFb(String fb) {
                this.fb = fb;
            }
        }
    }
}
