package pers.life.helper.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

public class RecyclerViewImplementsContextMenu extends RecyclerView {
    private AdapterView.AdapterContextMenuInfo contextMenuInfo;

    public RecyclerViewImplementsContextMenu(Context context) {
        super(context);
    }

    public RecyclerViewImplementsContextMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewImplementsContextMenu(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public AdapterView.AdapterContextMenuInfo getContextMenuInfo() {
        return contextMenuInfo;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        int position = getChildAdapterPosition(originalView);
        long longId = getChildItemId(originalView);
        contextMenuInfo = new AdapterView.AdapterContextMenuInfo(originalView, position, longId);
        return super.showContextMenuForChild(originalView);
    }

}