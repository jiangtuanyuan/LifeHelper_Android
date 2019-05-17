package pers.life.helper.view.smart.text.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarPlateBean {

    /**
     * log_id : 8319606053702132081
     * words_result : {"color":"blue","number":"渝AN7968","probability":[0.9999690055847168,0.9998435974121094,0.9998425245285034,0.9998406171798706,0.9998188018798828,0.9990044236183167,0.9989345669746399],"vertexes_location":[{"y":102,"x":32},{"y":100,"x":223},{"y":152,"x":223},{"y":154,"x":33}]}
     */

    private long log_id;
    private WordsResultBean words_result;

    public static CarPlateBean objectFromData(String str) {

        return new Gson().fromJson(str, CarPlateBean.class);
    }

    public static CarPlateBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CarPlateBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CarPlateBean> arrayCarPlateBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<CarPlateBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<CarPlateBean> arrayCarPlateBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<CarPlateBean>>() {
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

    public WordsResultBean getWords_result() {
        return words_result;
    }

    public void setWords_result(WordsResultBean words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * color : blue
         * number : 渝AN7968
         * probability : [0.9999690055847168,0.9998435974121094,0.9998425245285034,0.9998406171798706,0.9998188018798828,0.9990044236183167,0.9989345669746399]
         * vertexes_location : [{"y":102,"x":32},{"y":100,"x":223},{"y":152,"x":223},{"y":154,"x":33}]
         */

        private String color;
        private String number;
        private List<Double> probability;
        private List<VertexesLocationBean> vertexes_location;

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

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public List<Double> getProbability() {
            return probability;
        }

        public void setProbability(List<Double> probability) {
            this.probability = probability;
        }

        public List<VertexesLocationBean> getVertexes_location() {
            return vertexes_location;
        }

        public void setVertexes_location(List<VertexesLocationBean> vertexes_location) {
            this.vertexes_location = vertexes_location;
        }

        public static class VertexesLocationBean {
            /**
             * y : 102
             * x : 32
             */

            private int y;
            private int x;

            public static VertexesLocationBean objectFromData(String str) {

                return new Gson().fromJson(str, VertexesLocationBean.class);
            }

            public static VertexesLocationBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), VertexesLocationBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<VertexesLocationBean> arrayVertexesLocationBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<VertexesLocationBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<VertexesLocationBean> arrayVertexesLocationBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<VertexesLocationBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }
    }
}
