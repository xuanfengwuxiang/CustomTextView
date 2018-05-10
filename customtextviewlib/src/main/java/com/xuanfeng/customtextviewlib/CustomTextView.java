package com.xuanfeng.customtextviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by xuanfengwuxiang on 2017/2/8.
 */

public class CustomTextView extends TextView {

    //背景形状
    private GradientDrawable mGradientDrawable;
    private int mShapeType;

    //正常状态
    private float mStrokeRadius;
    private float mTopLeftRadius;
    private float mTopRightRadius;
    private float mBottomLeftRadius;
    private float mBottomRightRadius;
    private float mStrokeWidth;
    private int mStrokeColor = -1;
    private int mSolidColor = -1;


    //按压状态
    private int mStrokePressedColor = -1;
    private int mSolidPressedColor = -1;
    private int mTextPressColor;
    private int mTextColor;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        getAttrs(context, attrs);
        initData();
    }

    //从xml中获取属性
    private void getAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        if (typedArray != null) {
            //正常状态
            mStrokeRadius = typedArray.getDimension(R.styleable.CustomTextView_stroke_radius, 0);
            mTopLeftRadius = typedArray.getDimension(R.styleable.CustomTextView_topLeftRadius, 0);
            mTopRightRadius = typedArray.getDimension(R.styleable.CustomTextView_topRightRadius, 0);
            mBottomLeftRadius = typedArray.getDimension(R.styleable.CustomTextView_bottomLeftRadius, 0);
            mBottomRightRadius = typedArray.getDimension(R.styleable.CustomTextView_bottomRightRadius, 0);
            mStrokeWidth = typedArray.getDimension(R.styleable.CustomTextView_stroke_width, 0);
            mStrokeColor = typedArray.getInt(R.styleable.CustomTextView_stroke_color, Color.TRANSPARENT);
            mSolidColor = typedArray.getInt(R.styleable.CustomTextView_solid_color, Color.TRANSPARENT);
            mTextColor = getCurrentTextColor();


            //按压状态
            mStrokePressedColor = typedArray.getInt(R.styleable.CustomTextView_stroke_press_color, 0);
            mSolidPressedColor = typedArray.getInt(R.styleable.CustomTextView_solid_press_color, 0);
            mTextPressColor = typedArray.getInt(R.styleable.CustomTextView_text_press_color, 0);

            //形状
            mShapeType = typedArray.getInt(R.styleable.CustomTextView_shapeTpe, GradientDrawable.RECTANGLE);

            typedArray.recycle();
        }

    }

    private void initData() {
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setShape(mShapeType);
        if (mShapeType == GradientDrawable.RECTANGLE) {
            if (mStrokeRadius != 0) {//统一设置圆角
                mGradientDrawable.setCornerRadius(mStrokeRadius);
            } else { //分别表示 左上 右上 右下 左下
                mGradientDrawable.setCornerRadii(new float[]{mTopLeftRadius, mTopLeftRadius, mTopRightRadius, mTopRightRadius, mBottomRightRadius, mBottomRightRadius, mBottomLeftRadius, mBottomLeftRadius});
            }
        }
        drawBackground(false);
    }

    //
    public void drawBackground(boolean isTouch) {
        if (isTouch) {//按压状态
            if (mSolidPressedColor != 0) {//按压背景色
                mGradientDrawable.setColor(mSolidPressedColor);
            }
            if (mStrokeWidth != 0 && mStrokePressedColor != 0) {
                mGradientDrawable.setStroke((int) mStrokeWidth, mStrokePressedColor);
            }
            if (mTextPressColor != 0) {
                setTextColor(mTextPressColor);
            }
        } else {//普通状态
            mGradientDrawable.setColor(mSolidColor);
            mGradientDrawable.setStroke((int) mStrokeWidth, mStrokeColor);
            setTextColor(mTextColor);

        }
        setBackground(mGradientDrawable);
        postInvalidate();
    }

    float downX = 0;
    float downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                drawBackground(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltX = event.getX() - downX;
                float deltY = event.getY() - downY;
                if (deltX > 10 || deltY > 10) {
                    drawBackground(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                drawBackground(false);
                break;
        }
        return true;
    }

    //============================方法区
    public void setSelection(boolean selection) {
        drawBackground(selection);
    }

    public void setShapeType(int shapeType) {
        mShapeType = shapeType;
        drawBackground(false);
    }

    public void setStrokeRadius(float strokeRadius) {
        mStrokeRadius = strokeRadius;
        drawBackground(false);
    }

    public void setTopLeftRadius(float topLeftRadius) {
        mTopLeftRadius = topLeftRadius;
        drawBackground(false);
    }

    public void setTopRightRadius(float topRightRadius) {
        mTopRightRadius = topRightRadius;
        drawBackground(false);
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        mBottomLeftRadius = bottomLeftRadius;
        drawBackground(false);
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        mBottomRightRadius = bottomRightRadius;
        drawBackground(false);
    }

    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
        drawBackground(false);
    }

    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        drawBackground(false);
    }

    public void setSolidColor(int solidColor) {
        mSolidColor = solidColor;
        drawBackground(false);
    }

    public void setStrokePressedColor(int strokePressedColor) {
        mStrokePressedColor = strokePressedColor;
        drawBackground(false);
    }

    public void setSolidPressedColor(int solidPressedColor) {
        mSolidPressedColor = solidPressedColor;
        drawBackground(false);
    }

    public void setTextPressColor(int textPressColor) {
        mTextPressColor = textPressColor;
        drawBackground(false);
    }

    public void setNormalTextColor(int textColor) {
        mTextColor = textColor;
        drawBackground(false);
    }
}
