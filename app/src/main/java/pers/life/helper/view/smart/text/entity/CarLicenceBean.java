package pers.life.helper.view.smart.text.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarLicenceBean {

    /**
     * log_id : 4169908577930342577
     * words_result_num : 10
     * words_result : {"证号":{"words":"431129199610080010"},"有效期限":{"words":"20161221"},"准驾车型":{"words":"C1"},"住址":{"words":"湖南省永州市江华瑶族自治县沱江镇大六冲村269号"},"至":{"words":"20221221"},"姓名":{"words":"蒋团圆"},"国籍":{"words":"中国"},"出生日期":{"words":"19961008"},"性别":{"words":"男"},"初次领证日期":{"words":"20161221"}}
     */
    private long log_id;
    private int words_result_num;
    private WordsResultBean words_result;

    public static CarLicenceBean objectFromData(String str) {

        return new Gson().fromJson(str, CarLicenceBean.class);
    }

    public static CarLicenceBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CarLicenceBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CarLicenceBean> arrayCarLicenceBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<CarLicenceBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<CarLicenceBean> arrayCarLicenceBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<CarLicenceBean>>() {
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

    public WordsResultBean getWords_result() {
        return words_result;
    }

    public void setWords_result(WordsResultBean words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * 证号 : {"words":"431129199610080010"}
         * 有效期限 : {"words":"20161221"}
         * 准驾车型 : {"words":"C1"}
         * 住址 : {"words":"湖南省永州市江华瑶族自治县沱江镇大六冲村269号"}
         * 至 : {"words":"20221221"}
         * 姓名 : {"words":"蒋团圆"}
         * 国籍 : {"words":"中国"}
         * 出生日期 : {"words":"19961008"}
         * 性别 : {"words":"男"}
         * 初次领证日期 : {"words":"20161221"}
         */

        private 证号Bean 证号;
        private 有效期限Bean 有效期限;
        private 准驾车型Bean 准驾车型;
        private 住址Bean 住址;
        private 至Bean 至;
        private 姓名Bean 姓名;
        private 国籍Bean 国籍;
        private 出生日期Bean 出生日期;
        private 性别Bean 性别;
        private 初次领证日期Bean 初次领证日期;

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

        public 证号Bean get证号() {
            return 证号;
        }

        public void set证号(证号Bean 证号) {
            this.证号 = 证号;
        }

        public 有效期限Bean get有效期限() {
            return 有效期限;
        }

        public void set有效期限(有效期限Bean 有效期限) {
            this.有效期限 = 有效期限;
        }

        public 准驾车型Bean get准驾车型() {
            return 准驾车型;
        }

        public void set准驾车型(准驾车型Bean 准驾车型) {
            this.准驾车型 = 准驾车型;
        }

        public 住址Bean get住址() {
            return 住址;
        }

        public void set住址(住址Bean 住址) {
            this.住址 = 住址;
        }

        public 至Bean get至() {
            return 至;
        }

        public void set至(至Bean 至) {
            this.至 = 至;
        }

        public 姓名Bean get姓名() {
            return 姓名;
        }

        public void set姓名(姓名Bean 姓名) {
            this.姓名 = 姓名;
        }

        public 国籍Bean get国籍() {
            return 国籍;
        }

        public void set国籍(国籍Bean 国籍) {
            this.国籍 = 国籍;
        }

        public 出生日期Bean get出生日期() {
            return 出生日期;
        }

        public void set出生日期(出生日期Bean 出生日期) {
            this.出生日期 = 出生日期;
        }

        public 性别Bean get性别() {
            return 性别;
        }

        public void set性别(性别Bean 性别) {
            this.性别 = 性别;
        }

        public 初次领证日期Bean get初次领证日期() {
            return 初次领证日期;
        }

        public void set初次领证日期(初次领证日期Bean 初次领证日期) {
            this.初次领证日期 = 初次领证日期;
        }

        public static class 证号Bean {
            /**
             * words : 431129199610080010
             */

            private String words;

            public static 证号Bean objectFromData(String str) {

                return new Gson().fromJson(str, 证号Bean.class);
            }

            public static 证号Bean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), 证号Bean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<证号Bean> array证号BeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<证号Bean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<证号Bean> array证号BeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<证号Bean>>() {
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

        public static class 有效期限Bean {
            /**
             * words : 20161221
             */

            private String words;

            public static 有效期限Bean objectFromData(String str) {

                return new Gson().fromJson(str, 有效期限Bean.class);
            }

            public static 有效期限Bean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), 有效期限Bean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<有效期限Bean> array有效期限BeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<有效期限Bean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<有效期限Bean> array有效期限BeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<有效期限Bean>>() {
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

        public static class 准驾车型Bean {
            /**
             * words : C1
             */

            private String words;

            public static 准驾车型Bean objectFromData(String str) {

                return new Gson().fromJson(str, 准驾车型Bean.class);
            }

            public static 准驾车型Bean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), 准驾车型Bean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<准驾车型Bean> array准驾车型BeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<准驾车型Bean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<准驾车型Bean> array准驾车型BeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<准驾车型Bean>>() {
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

        public static class 住址Bean {
            /**
             * words : 湖南省永州市江华瑶族自治县沱江镇大六冲村269号
             */

            private String words;

            public static 住址Bean objectFromData(String str) {

                return new Gson().fromJson(str, 住址Bean.class);
            }

            public static 住址Bean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), 住址Bean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<住址Bean> array住址BeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<住址Bean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<住址Bean> array住址BeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<住址Bean>>() {
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

        public static class 至Bean {
            /**
             * words : 20221221
             */

            private String words;

            public static 至Bean objectFromData(String str) {

                return new Gson().fromJson(str, 至Bean.class);
            }

            public static 至Bean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), 至Bean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<至Bean> array至BeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<至Bean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<至Bean> array至BeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<至Bean>>() {
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

        public static class 姓名Bean {
            /**
             * words : 蒋团圆
             */

            private String words;

            public static 姓名Bean objectFromData(String str) {

                return new Gson().fromJson(str, 姓名Bean.class);
            }

            public static 姓名Bean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), 姓名Bean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<姓名Bean> array姓名BeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<姓名Bean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<姓名Bean> array姓名BeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<姓名Bean>>() {
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

        public static class 国籍Bean {
            /**
             * words : 中国
             */

            private String words;

            public static 国籍Bean objectFromData(String str) {

                return new Gson().fromJson(str, 国籍Bean.class);
            }

            public static 国籍Bean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), 国籍Bean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<国籍Bean> array国籍BeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<国籍Bean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<国籍Bean> array国籍BeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<国籍Bean>>() {
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

        public static class 出生日期Bean {
            /**
             * words : 19961008
             */

            private String words;

            public static 出生日期Bean objectFromData(String str) {

                return new Gson().fromJson(str, 出生日期Bean.class);
            }

            public static 出生日期Bean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), 出生日期Bean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<出生日期Bean> array出生日期BeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<出生日期Bean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<出生日期Bean> array出生日期BeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<出生日期Bean>>() {
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

        public static class 性别Bean {
            /**
             * words : 男
             */

            private String words;

            public static 性别Bean objectFromData(String str) {

                return new Gson().fromJson(str, 性别Bean.class);
            }

            public static 性别Bean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), 性别Bean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<性别Bean> array性别BeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<性别Bean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<性别Bean> array性别BeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<性别Bean>>() {
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

        public static class 初次领证日期Bean {
            /**
             * words : 20161221
             */

            private String words;

            public static 初次领证日期Bean objectFromData(String str) {

                return new Gson().fromJson(str, 初次领证日期Bean.class);
            }

            public static 初次领证日期Bean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), 初次领证日期Bean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<初次领证日期Bean> array初次领证日期BeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<初次领证日期Bean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<初次领证日期Bean> array初次领证日期BeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<初次领证日期Bean>>() {
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
}
