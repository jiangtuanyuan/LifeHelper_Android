package pers.life.helper.view.smart.text.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TextBean {
    /**
     * log_id : 5937116452458251601
     * direction : 0
     * words_result_num : 11
     * words_result : [{"words":"组长会议"},{"words":"绩效统计原则"},{"words":"1、每个月月底,以周为单位,列出本组下个月的计划任务安排"},{"words":"需列可预见的,可量化的工作,不可预见的和无法量化的暂时不"},{"words":"进来"},{"words":"2.每个月月头,统计本组所有成员在上个月从第一天到最后"},{"words":"实际完成的任务并折算成对应的金币数量(四舍五入保留整数位"},{"words":"如有不能在一个月之内全部完成的功能,套用定额再*完成比例"},{"words":"每个专业都是完整的100%;完成静态代码(前端完成U界"},{"words":"后端完成接口编写):33%、完成功能数据对接:33%、完成整体"},{"words":"并成功上线:34%"}]
     */
    private long log_id;
    private int direction;
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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
         * words : 组长会议
         */

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

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
