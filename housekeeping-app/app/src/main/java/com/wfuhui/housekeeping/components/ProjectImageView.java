package com.wfuhui.housekeeping.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class ProjectImageView extends ImageView {
    //宽高比
    private float ratio = 1f;

    public ProjectImageView(Context context) {
        super(context);
    }

    public ProjectImageView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //从widthMeasureSpec中反向获取父VIew计算好的size
        int width = MeasureSpec.getSize(widthMeasureSpec);

        //根据宽高比和width，计算出对应的height
        if(ratio != 0){
            float height = width / ratio;
            //重新组建heightMeasureSpec，传递给super.onMeasure
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
