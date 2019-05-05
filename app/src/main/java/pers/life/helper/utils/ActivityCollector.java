package pers.life.helper.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动管理器
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        if (activity == null)
            return;
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        if (activities == null || activities.isEmpty()) {
            return;
        }
        activities.remove(activity);
        activity.finish();
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (activities == null || activities.isEmpty()) {
            return;
        }
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getClass().equals(cls)) {
                removeActivity(activities.get(i));
            }
        }
    }

    /**
     * 销毁指定Activity
     *
     * @param ActivityName 绝对路劲
     */
    public static void finish(final String ActivityName) {
        for (Activity activity : activities) {
            if (activity.getClass().getName().equals(ActivityName)) {
                activity.finish();
                break;
            }
        }
    }
    /**
     * 判断某个界面是否在前台,返回true，为显示,否则不是
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }
}
