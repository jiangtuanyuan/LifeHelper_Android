package pers.life.helper.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * jiang 2019-01-01
 */
public class IOSDialog extends Dialog {
    private Context context;
    private int resId;
    private View view;

    public IOSDialog(Context context, int resLayout) {
        this(context, 0, 0);
    }


    public IOSDialog(Context context, int themeResId, int resLayout) {
        super(context, themeResId);
        this.context = context;
        this.resId = resLayout;
    }

    public IOSDialog(Context context, int themeResId, View view) {
        super(context, themeResId);
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.resId != 0) {
            this.setContentView(resId);
        }
        if (this.view != null) {
            this.setContentView(view);
        }
    }
}
