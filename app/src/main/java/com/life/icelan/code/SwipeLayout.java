package com.life.icelan.code;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/10/7 0007.
 */

public class SwipeLayout extends FrameLayout {

    private ViewDragHelper helper;
    private View longview;
    private View shortview;
    private int longWidth;
    private int longHeight;
    private int shortWidth;

    public SwipeLayout(Context context) {
        this(context,null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        helper = ViewDragHelper.create(this, callback);
    }


    private ViewDragHelper.Callback callback=new ViewDragHelper.Callback(){

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;//返回true表示谁都可以捕获
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //處理角標越界的問題
            if(child==longview){
                if(left<-shortWidth){
                    left=-shortWidth;
                }else if(left>0){
                    left=0;
                }
            }else{
                if(left<longWidth-shortWidth){
                    left=longWidth-shortWidth;
                }else if(left>longWidth){
                    left=longWidth;
                }
            }
            return left;
        }

        @Override
        //重寫這個方法，當longview懂動的時候，shortview 也跟這動
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView==longview){
                shortview.offsetLeftAndRight(dx);
            }else{
                longview.offsetLeftAndRight(dx);
            }
            invalidate();//可能会造成一个小bugshortview加载不出来，所以需要加上这个
            //如果sl被滑开，就记录下被划开的sl
            if(longview.getLeft()==-shortWidth){
                SwipeManager.getInstance().setSwipeLayout(SwipeLayout.this);
            }//下一步要处理ontouch的down事件
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //如果滑动shortview超过自身的一半就完全的打开，否则就关闭
            if(longview.getLeft()<-shortWidth*0.5f){
                open();
            }else{
                close();
            }

        }

    };

    private void open(){
        if(helper.smoothSlideViewTo(longview,-shortWidth,0)){
            invalidate();
        }
    }

    private void close() {
        if(helper.smoothSlideViewTo(longview,0,0)){
            invalidate();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(helper.continueSettling(true)){
            invalidate();
        }else{
            if(longview.getLeft()==0) {
                //当没有下一帧的时候，就是当完全关闭的时候，清除被记录的sl
                SwipeManager.getInstance().clearSwipeLayout();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //判断是否有sl被滑开，如果记录的sl和当前的sl不一样就关闭记录的sl
                SwipeLayout sl=SwipeManager.getInstance().getSwipeLayout();
                if(sl!=null){//默认第一次的时候sl为空，判断当sl不为空的时候在进行
                    if(sl!=SwipeLayout.this){
                        sl.close();
                        return true;
                        //做到这时，基本没问题，可是需求是只要有sl被打开，其他的sl就不能被打开
                        //所以需要下面
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                sl=SwipeManager.getInstance().getSwipeLayout();
                if (sl!=null){
                    if(sl!=SwipeLayout.this){
                        //请求父亲也就是listview不要拦截纵向的滑动。
                        requestDisallowInterceptTouchEvent(true);
                        return true;
                        //只有移动的时候return true才会不动
                    }
                }

        }
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }
    //重新排放孩子的位置應該重寫下面的方法

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        longview.layout(0,0,longWidth,longHeight);
        shortview.layout(longWidth,0,longWidth+shortWidth,longHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        longview = getChildAt(1);
        shortview = getChildAt(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        longWidth = longview.getMeasuredWidth();
        longHeight = longview.getMeasuredHeight();
        shortWidth = shortview.getMeasuredWidth();
    }

    //记住上一个被划开的sl判断当前滑动的sl是否一直，如果不一致，关闭上一个被划开的sl
    //再次滑动当前的sl划开后记录
    //private SwipeLayout sl=this;//不能这样记录sl因为对象有多个，不能一对多
}
