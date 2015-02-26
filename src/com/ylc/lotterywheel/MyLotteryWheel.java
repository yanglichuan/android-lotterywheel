
package com.ylc.lotterywheel;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

public class MyLotteryWheel extends View {

    public MyLotteryWheel(Context context) {
        super(context);
        init();
    }

    public MyLotteryWheel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyLotteryWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private int suggestW = 200;//default
    private int suggestH = 200;//default
    private Paint paint;
    public ObjectAnimator animator;

    public Bitmap bgBitmap;
    public Bitmap centerBitmap;

    public Bitmap gift1;
    public Bitmap gift2;
    public Bitmap gift3;
    public Bitmap gift4;
    public Bitmap gift5;
    public Bitmap gift6;
    private int[] mImgs = new int[] {
            R.drawable.danfan, R.drawable.ipad,
            R.drawable.f040, R.drawable.iphone, R.drawable.meizi,
            R.drawable.f040
    };

    
    class MyGift{
        public Bitmap bmp;
        public int startAngle;
        public int endAngle;
    }
    MyGift mGift1 = new MyGift();
    MyGift mGift2 = new MyGift();
    MyGift mGift3 = new MyGift();
    MyGift mGift4 = new MyGift();
    MyGift mGift5 = new MyGift();
    MyGift mGift6 = new MyGift();
    ArrayList<MyGift> list = new ArrayList<MyLotteryWheel.MyGift>();
    ArrayList<String> giftstrs = new ArrayList<String>();
    @SuppressLint("NewApi")
    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(3);
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
        centerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);

        gift1 = BitmapFactory.decodeResource(getResources(), mImgs[0]);
        gift2 = BitmapFactory.decodeResource(getResources(), mImgs[1]);
        gift3 = BitmapFactory.decodeResource(getResources(), mImgs[2]);
        gift4 = BitmapFactory.decodeResource(getResources(), mImgs[3]);
        gift5 = BitmapFactory.decodeResource(getResources(), mImgs[4]);
        gift6 = BitmapFactory.decodeResource(getResources(), mImgs[5]);
        mGift1.bmp = gift1;
        mGift2.bmp = gift2;
        mGift3.bmp = gift3;
        mGift4.bmp = gift4;
        mGift5.bmp = gift5;
        mGift6.bmp = gift6;
        list.add(mGift1);
        list.add(mGift2);
        list.add(mGift3);
        list.add(mGift4);
        list.add(mGift5);
        list.add(mGift6);
        giftstrs.add("单反");
        giftstrs.add("ipad");
        giftstrs.add("笑脸1");
        giftstrs.add("iphone");
        giftstrs.add("妹子");
        giftstrs.add("笑脸2");
        
        animator = ObjectAnimator.ofInt(new Object(), "nimi", 0, 360);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(500);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);

    }

    public Handler handler = new Handler();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (suggestW + getPaddingLeft()
                    + getPaddingRight());
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     * 
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = suggestH + getPaddingTop()
                    + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public int screenW = 0;
    public int screenH = 0;

    public int startAngle = 50;
    public int distanceR = 50;

    public boolean bRunning = false;
    @SuppressLint("NewApi")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                if (bRunning) {
                    bRunning = false;
                    animator.cancel();
                    for(int i=0;i<list.size();i++){
                        if((list.get(i).endAngle%360) >=270 && (list.get(i).endAngle%360) <330){
                            if(listener != null){
                                //Toast.makeText(getContext(), giftstrs.get(i), 0).show();//注释掉
                                listener.onSelect(giftstrs.get(i));
                            }else{
                                Toast.makeText(getContext(), giftstrs.get(i), 0).show();
                            }
                            break;
                        }
                    }
                }else{
                    bRunning = true;
                    animator.start();
                }
                break;
            default:
                break;
        }
        return true;
    }

    public interface SelectListener{
        public void onSelect(String str);
    }
    SelectListener listener = null;
    public void setOnSelectListener(SelectListener listener){
        this.listener = listener;
    }
    
    
    //360 分成了6份
    private int sweepAngle = 60;

    @Override
    protected void onDraw(Canvas canvas) {
        // canvas.drawColor(Color.YELLOW);
        screenW = getMeasuredWidth();
        screenH = getMeasuredHeight();
        int centerx = screenH / 2;
        int centery = screenH / 2;

        float dis = screenH / 3f;
        paint.setStyle(Style.FILL);
        RectF dst = new RectF(0, 0, screenW, screenH);
        RectF arcRect = new RectF(distanceR, distanceR, screenW - distanceR, screenH - distanceR);
        // canvas.drawCircle(centerx, centery, r, paint);
        canvas.drawBitmap(bgBitmap, null, dst, paint);
        paint.setColor(0xFFFFC300);
        canvas.drawArc(arcRect, startAngle + 0 * sweepAngle, sweepAngle, true, paint);

        double radians = Math.toRadians(startAngle + sweepAngle / 2);
        int x = (int) (centerx + dis * Math.cos(radians)) - gift1.getWidth() / 2;
        int y = (int) (centery + dis * Math.sin(radians)) - gift1.getHeight() / 2;
        ;
        canvas.drawBitmap(mGift1.bmp, x, y, paint);
        mGift1.startAngle = startAngle + 0 * sweepAngle;
        mGift1.endAngle =  mGift1.startAngle + sweepAngle;
        

        paint.setColor(0xFFF17E01);
        canvas.drawArc(arcRect, startAngle + 1 * sweepAngle, sweepAngle, true, paint);
        radians = Math.toRadians(startAngle + sweepAngle / 2 + 1 * sweepAngle);
        x = (int) (centerx + dis * Math.cos(radians)) - gift2.getWidth() / 2;
        y = (int) (centery + dis * Math.sin(radians)) - gift2.getHeight() / 2;
        ;
        canvas.drawBitmap(mGift2.bmp, x, y, paint);
        mGift2.startAngle = startAngle + 1 * sweepAngle;
        mGift2.endAngle =  mGift2.startAngle + sweepAngle;

        paint.setColor(0xFFFFC300);
        canvas.drawArc(arcRect, startAngle + 2 * sweepAngle, sweepAngle, true, paint);
        radians = Math.toRadians(startAngle + sweepAngle / 2 + 2 * sweepAngle);
        x = (int) (centerx + dis * Math.cos(radians)) - gift3.getWidth() / 2;
        y = (int) (centery + dis * Math.sin(radians)) - gift3.getHeight() / 2;
        ;
        canvas.drawBitmap(mGift3.bmp, x, y, paint);
        mGift3.startAngle = startAngle + 2 * sweepAngle;
        mGift3.endAngle =  mGift3.startAngle + sweepAngle;

        paint.setColor(0xFFF17E01);
        canvas.drawArc(arcRect, startAngle + 3 * sweepAngle, sweepAngle, true, paint);
        radians = Math.toRadians(startAngle + sweepAngle / 2 + 3 * sweepAngle);
        x = (int) (centerx + dis * Math.cos(radians)) - gift4.getWidth() / 2;
        y = (int) (centery + dis * Math.sin(radians)) - gift4.getHeight() / 2;
        ;
        canvas.drawBitmap(mGift4.bmp, x, y, paint);
        mGift4.startAngle = startAngle + 3 * sweepAngle;
        mGift4.endAngle =  mGift4.startAngle + sweepAngle;

        paint.setColor(0xFFFFC300);
        canvas.drawArc(arcRect, startAngle + 4 * sweepAngle, sweepAngle, true, paint);
        radians = Math.toRadians(startAngle + sweepAngle / 2 + 4 * sweepAngle);
        x = (int) (centerx + dis * Math.cos(radians)) - gift5.getWidth() / 2;
        y = (int) (centery + dis * Math.sin(radians)) - gift5.getHeight() / 2;
        ;
        canvas.drawBitmap(mGift5.bmp, x, y, paint);
        mGift5.startAngle = startAngle + 4 * sweepAngle;
        mGift5.endAngle =  mGift5.startAngle + sweepAngle;

        paint.setColor(0xFFF17E01);
        canvas.drawArc(arcRect, startAngle + 5 * sweepAngle, sweepAngle, true, paint);
        radians = Math.toRadians(startAngle + sweepAngle / 2 + 5 * sweepAngle);
        x = (int) (centerx + dis * Math.cos(radians)) - gift6.getWidth() / 2;
        y = (int) (centery + dis * Math.sin(radians)) - gift6.getHeight() / 2;
        ;
        canvas.drawBitmap(mGift6.bmp, x, y, paint);
        mGift6.startAngle = startAngle + 5 * sweepAngle;
        mGift6.endAngle =  mGift6.startAngle + sweepAngle;

        canvas.drawBitmap(centerBitmap, centerx - centerBitmap.getWidth() / 2,
                centery - centerBitmap.getHeight() / 2, paint);
    }
}
