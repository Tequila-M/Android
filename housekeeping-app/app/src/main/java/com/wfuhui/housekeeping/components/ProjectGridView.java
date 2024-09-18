package com.wfuhui.housekeeping.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 项目列表组件
 */
public class ProjectGridView extends GridView {
    public ProjectGridView(Context context) {
        super(context);
    }

    public ProjectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProjectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
