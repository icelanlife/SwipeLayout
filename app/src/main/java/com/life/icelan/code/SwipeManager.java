package com.life.icelan.code;

/**
 * Created by Administrator on 2016/10/7 0007.
 */

public class SwipeManager {
    //单例设计，保证只有一个manager；
    private static SwipeManager manager=new SwipeManager();
    private SwipeManager(){}
    public static SwipeManager getInstance(){
        return manager;
    }
    private SwipeLayout sl;
    public void setSwipeLayout(SwipeLayout sl){
        this.sl=sl;
    }
    public SwipeLayout getSwipeLayout(){
        return sl;
    }
    public void clearSwipeLayout(){
        this.sl=null;
    }

}
