package com.seek.plantsvszombies.layer;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import com.seek.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;

/**
 * Created by admin on 2017/9/8.
 */

public class WeicomeLayer extends BaseLayer {
    private static final String TAG = WeicomeLayer.class.getSimpleName();

    private CCSprite logo;
    private CCSprite start;

    public WeicomeLayer() {

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(6000);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if (start != null){
                    start.setVisible(true);
                    setIsTouchEnabled(true);//打开触摸事件开关
                }

            }
        }.execute();

        init();
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {

        CGPoint cgPoint = this.convertTouchToNodeSpace(event);
        CGRect boundingBox = start.getBoundingBox();
        if (CGRect.containsPoint(boundingBox, cgPoint)){
//            Log.i(TAG, "点击开始...");

            CommonUtils.changeLayer(new MenuLayer());
        }
        return super.ccTouchesBegan(event);
    }

    private void init() {
        logo();
    }

    private void logo() {
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\popcap_logo.png
        logo = CCSprite.sprite("image/popcap_logo.png");
        winSize = CCDirector.sharedDirector().getWinSize();
        logo.setPosition(winSize.width/2, winSize.height/2);

        CCHide ccHide = CCHide.action();//隐藏
        CCDelayTime delayTime = CCDelayTime.action(1);//停留1s

        CCSequence actions = CCSequence.actions(delayTime, delayTime,
                ccHide, delayTime,
                CCCallFunc.action(this,"loadWelcome"));
        logo.runAction(actions);
        this.addChild(logo);
    }

    /**
     * 当动作执行完毕后条用
     */
    public void loadWelcome(){
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\welcome.jpg
        CCSprite bg = CCSprite.sprite("image/welcome.jpg");
        bg.setAnchorPoint(0, 0);
        this.addChild(bg);

        loading();
    }

    /**
     * 加载序列帧播放
     */
    private void loading() {
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\loading\loading_01.png
        CCSprite loading = CCSprite.sprite("image/loading/loading_01.png");
        loading.setPosition(winSize.width / 2, 30);
        this.addChild(loading);

        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        String foramt = "image/loading/loading_%02d.png";
        for (int i=1; i<=9; i++){
            CCSpriteFrame ccSpriteFrame = CCSprite.sprite(String.format(foramt, i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        CCAnimation anim = CCAnimation.animation("loading", 0.2f, frames);

        //序列帧一般必须是永不停止的播放，不需要永不停止播放需要制定第二个参数为false
        CCAnimate animate = CCAnimate.action(anim, false);
        loading.runAction(animate);

        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\loading\loading_start.png
        start = CCSprite.sprite("image/loading/loading_start.png");
        start.setPosition(winSize.width / 2, 30);
        start.setVisible(false);
        this.addChild(start);
    }
}
