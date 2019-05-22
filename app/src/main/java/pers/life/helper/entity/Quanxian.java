package pers.life.helper.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Quanxian {
    /**
     * text : 1
     * plant : 1
     * anima : 1
     */
    private int text = 0;
    private int plant = 0;
    private int anima = 0;
    private int car = 0;

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public static Quanxian objectFromData(String str) {

        return new Gson().fromJson(str, Quanxian.class);
    }

    public static Quanxian objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), Quanxian.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Quanxian> arrayQuanxianFromData(String str) {

        Type listType = new TypeToken<ArrayList<Quanxian>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<Quanxian> arrayQuanxianFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<Quanxian>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public int getPlant() {
        return plant;
    }

    public void setPlant(int plant) {
        this.plant = plant;
    }

    public int getAnima() {
        return anima;
    }

    public void setAnima(int anima) {
        this.anima = anima;
    }
}
