package pers.life.helper.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CouerierData {
    /**
     * company : 韵达
     * com : yd
     * no : 3834507780815
     * status : 1
     * status_detail : SIGNED
     * list : [{"datetime":"2019-04-26 21:13:39","remark":"【金华市】 浙江义乌青岩刘公司 已揽收","zone":""},{"datetime":"2019-04-26 22:08:30","remark":"【金华市】 已到达 浙江义乌分拨中心","zone":""},{"datetime":"2019-04-26 22:15:59","remark":"【金华市】 已离开 浙江义乌青岩刘公司 发往 湖南长沙网点包","zone":""},{"datetime":"2019-04-26 23:10:29","remark":"【金华市】 已离开 浙江义乌分拨中心 发往 湖南长沙分拨中心","zone":""},{"datetime":"2019-04-27 11:06:03","remark":"【长沙市】 已到达 湖南长沙分拨中心","zone":""},{"datetime":"2019-04-27 11:06:18","remark":"【长沙市】 已离开 湖南长沙分拨中心 发往 湖南长沙开福区长沙大学公司","zone":""},{"datetime":"2019-04-27 11:55:54","remark":"【长沙市】 已到达 湖南长沙分拨中心","zone":""},{"datetime":"2019-04-27 15:37:48","remark":"【长沙市】 已离开 湖南长沙开福区长沙大学公司 发往 湖南长沙开福区长沙大学公司政府便利分部18142660034","zone":""},{"datetime":"2019-04-27 16:00:17","remark":"【长沙市】 湖南长沙开福区长沙大学公司政府便利分部 派件员 曹才学18142660034正在为您派送","zone":""},{"datetime":"2019-04-27 19:01:39","remark":"【长沙市】 已签收 : 由 维智女生公寓门口龙蟠路84号  代签收，如有问题联系曹才学18142660034","zone":""}]
     */
    private String company;
    private String com;
    private String no;
    private String status;
    private String status_detail;
    private List<ListBean> list;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_detail() {
        return status_detail;
    }

    public void setStatus_detail(String status_detail) {
        this.status_detail = status_detail;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * datetime : 2019-04-26 21:13:39
         * remark : 【金华市】 浙江义乌青岩刘公司 已揽收
         * zone :
         */
        private String datetime;
        private String remark;
        private String zone;

        public static ListBean objectFromData(String str) {

            return new Gson().fromJson(str, ListBean.class);
        }

        public static ListBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), ListBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<ListBean> arrayListBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ListBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<ListBean> arrayListBeanFromData(String str, String key) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<ListBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return new ArrayList();
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }
    }
}
