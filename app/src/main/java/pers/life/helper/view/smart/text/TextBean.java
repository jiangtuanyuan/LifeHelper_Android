package pers.life.helper.view.smart.text;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TextBean {
    /**
     * log_id : 779810327213417456
     * words_result_num : 5
     * words_result : [{"location":{"width":41,"top":113,"left":296,"height":42},"words":"9"},{"location":{"width":55,"top":120,"left":132,"height":205},"words":"8↑5"},{"location":{"width":42,"top":260,"left":305,"height":45},"words":"6"},{"location":{"width":41,"top":442,"left":161,"height":50},"words":"2"},{"location":{"width":70,"top":407,"left":315,"height":70},"words":"3"}]
     */
    private long log_id;
    private int words_result_num;
    private List<WordsResultBean> words_result;

    public static TextBean objectFromData(String str) {

        return new Gson().fromJson(str, TextBean.class);
    }

    public static TextBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), TextBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<TextBean> arrayTextBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<TextBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<TextBean> arrayTextBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<TextBean>>() {
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

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * location : {"width":41,"top":113,"left":296,"height":42}
         * words : 9
         */

        private LocationBean location;
        private String words;

        public static WordsResultBean objectFromData(String str) {

            return new Gson().fromJson(str, WordsResultBean.class);
        }

        public static WordsResultBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), WordsResultBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<WordsResultBean> arrayWordsResultBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<WordsResultBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<WordsResultBean> arrayWordsResultBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<WordsResultBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public static class LocationBean {
            /**
             * width : 41
             * top : 113
             * left : 296
             * height : 42
             */

            private int width;
            private int top;
            private int left;
            private int height;

            public static LocationBean objectFromData(String str) {

                return new Gson().fromJson(str, LocationBean.class);
            }

            public static LocationBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), LocationBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<LocationBean> arrayLocationBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<LocationBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<LocationBean> arrayLocationBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<LocationBean>>() {
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

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
