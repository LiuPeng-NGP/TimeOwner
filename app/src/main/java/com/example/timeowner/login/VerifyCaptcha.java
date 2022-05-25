package com.example.timeowner.login;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.timeowner.R;

import java.util.Random;

public class VerifyCaptcha extends View {
    private String mCaptchaText;//验证码内容
    private int mCaptchaTextSize;//文本大小
    private int mCaptchaLength;//验证码长度
    private int mCaptchaBackground;//背景颜色
    private boolean mContainChar;//是否包含字母
    private int mPointNum;//干扰点数
    private int mLineNum;//干扰线数
    private Paint mPaint;//画笔
    private Rect mBound;//绘制范围
    private Bitmap mBitmap;//验证码图片
    private static Random mRandom = new Random();
    private static int mWidth;//控件宽度
    private static int mHeight;//控件高度

    public VerifyCaptcha(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化属性集合
        //获取属性的集合
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerifyCaptcha);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            //获取此项属性的id
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.VerifyCaptcha_captchaTextSize:
                    //将字体大小默认设置为16sp,TypeValue 实现px到sp转换
                    mCaptchaTextSize = typedArray.getDimensionPixelSize(index,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.VerifyCaptcha_captchaBackground:
                    //背景默认设置为白色
                    mCaptchaBackground = typedArray.getColor(index, Color.WHITE);
                    break;
                case R.styleable.VerifyCaptcha_captchaLength:
                    //将长度默认设置为4
                    mCaptchaLength = typedArray.getInteger(index, 4);
                    break;
                case R.styleable.VerifyCaptcha_isContainChar:
                    //默认设置为不包含字母
                    mContainChar = typedArray.getBoolean(index, false);
                    break;
                case R.styleable.VerifyCaptcha_pointNum:
                    //默认设置为100个干扰点
                    mPointNum = typedArray.getInteger(index, 100);
                    break;
                case R.styleable.VerifyCaptcha_linNum:
                    //默认设置10条干扰线
                    mLineNum = typedArray.getInteger(index, 10);
                    break;
            }
        }
        //官方解释：回收TypedArray 以便后面的使用者重用
        typedArray.recycle();
        //初始化数据
        mCaptchaText = getValidationCaptcha(mCaptchaLength, mContainChar);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//防锯齿
        mBound = new Rect();
        //获取文本矩形区域可以得到宽高
        mPaint.getTextBounds(mCaptchaText, 0, mCaptchaText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取控件宽高的显示模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽高的尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //设置宽高默认为建议使用最小值
        int width = getDefaultSize(getSuggestedMinimumHeight(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        // MeasureSpec父布局传递给后代的布局要求 包含 确定大小和三种模式
        // EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
        // AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
        // UNSPECIFIED：表示子布局想要多大就多大，很少使用
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mCaptchaTextSize);
            mPaint.getTextBounds(mCaptchaText, 0, mCaptchaText.length(), mBound);
            float textWidth = mBound.width();
            int tempWidth = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = tempWidth;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mCaptchaTextSize);
            mPaint.getTextBounds(mCaptchaText, 0 ,mCaptchaText.length(), mBound);
            float textHeight = mBound.height();
            int tempHeight = (int)(getPaddingTop() + textHeight +getPaddingBottom());
            height = tempHeight;
        }
        //设置测量的宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        if (mBitmap == null) {
            mBitmap = createBitmapValidate();
        }
        canvas.drawBitmap(mBitmap, 0 ,0, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                refresh();
                break;
        }
        return super.onTouchEvent(event);
    }
    //创建动态验证码
    private Bitmap createBitmapValidate() {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            //回收并设为null
            mBitmap.recycle();
            mBitmap = null;
        }
        //创建图片
        Bitmap sourceBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        //创建画布
        Canvas canvas = new Canvas(sourceBitmap);
        //背景颜色
        canvas.drawColor(mCaptchaBackground);
        //初始化文字画笔
        mPaint.setStrokeWidth(3f);//设置画笔宽度
        mPaint.setTextSize(mCaptchaTextSize);//设置文本大小
        //测量验证码字符串显示的宽度值
        float textWidth = mPaint.measureText(mCaptchaText);
        //画验证码
        int length = mCaptchaText.length();
        //计算一个字符所占的位置
        float charLength = textWidth / length;
        for (int i = 1; i <= length; i++) {
            int offsetDegree = mRandom.nextInt(15);
            //这里只会先生0和1，如果是1旋转正角度，是0旋转负角度
            offsetDegree = mRandom.nextInt(2) == 1 ? offsetDegree : -offsetDegree;
            //用来保存画布状态，save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
            canvas.save();
            //设置旋转
            canvas.rotate(offsetDegree, mWidth / 2, mHeight / 2);
            //给画笔设计随机颜色
            mPaint.setARGB(255, mRandom.nextInt(200) + 20,
                    mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20);
            //设置字体绘制位置
            canvas.drawText(String.valueOf(mCaptchaText.charAt(i - 1)), (i - 1)*charLength + 5,
                    mHeight * 4 / 5f, mPaint);
            //用来恢复画布之前的状态，防止save后对Canvas执行的操作对后续的绘制有影响。
            canvas.restore();
        }
        //重新设置画笔
        mPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20,
                mRandom.nextInt(200) + 20) ;
        mPaint.setStrokeWidth(1);
        //画干扰点
        for (int i = 0; i < mPointNum; i++) {
            drawPoint(canvas, mPaint);
        }
        //画干扰线
        for (int i = 0; i < mLineNum; i++) {
            drawLine(canvas,mPaint);
        }
        return sourceBitmap;
    }
    //干扰点
    private static void drawPoint(Canvas canvas, Paint paint) {
        PointF pointF = new PointF(mRandom.nextInt(mWidth) + 10,
                mRandom.nextInt(mHeight) + 10);
        canvas.drawPoint(pointF.x, pointF.y, paint);
    }
    //干扰线
    private static void drawLine(Canvas canvas, Paint paint) {
        int startX = mRandom.nextInt(mWidth);
        int startY = mRandom.nextInt(mHeight);
        int endX = mRandom.nextInt(mWidth);
        int endY = mRandom.nextInt(mHeight);
        canvas.drawLine(startX, startY, endX, endY, paint);
    }
    //获取验证码
    public  String getValidationCaptcha(int length, boolean contains) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            if (contains) {
                //字母或数字
                String code = random.nextInt(2) % 2 == 0 ? "char" : "num";
                //字符串
                if ("char".equalsIgnoreCase(code)) {
                    //大写或小写字母
                    int  choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char) (choice + random.nextInt(26));
                } else if ("num".equalsIgnoreCase(code)) {
                    val += String.valueOf(random.nextInt(10));
                }
            } else {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    //判断验证码是否一致，忽略大小写
    public Boolean isEqualsIgnoreCase(String codeString) {
        return mCaptchaText.equalsIgnoreCase(codeString);
    }
    //不忽略大小写
    public Boolean isEquals(String codeString) {
        return mCaptchaText.equals(codeString);
    }
    //提供外部调用的刷新方法
    public void refresh() {
        mCaptchaText = getValidationCaptcha(mCaptchaLength, mContainChar);
        mBitmap = createBitmapValidate();
        invalidate();
    }
}
