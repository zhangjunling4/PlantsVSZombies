package com.seek.plantsvszombies.utils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFlipXTransition;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/9/8.
 */

public class CommonUtils {

    /**
     * 切换新的图层
     * @param newLayer 新进入的图层
     */
    public static void changeLayer(CCLayer newLayer){
        CCScene scene = CCScene.node();
        scene.addChild(newLayer);

        CCFlipXTransition transition = CCFlipXTransition.transition(1, scene, 0);
        CCDirector.sharedDirector().replaceScene(transition);
    }

    /**
     * 解析地图上 对象的所有点
     * @param map 地图
     * @param name 对象名字
     * @return
     */
    public static List<CGPoint> getMapPoints(CCTMXTiledMap map, String name){
        List<CGPoint> points = new ArrayList<>();
        CCTMXObjectGroup objectGroupNamed = map.objectGroupNamed(name);
        ArrayList<HashMap<String, String>> objects = objectGroupNamed.objects;

        for (HashMap<String, String> hashMap : objects){
            int x = Integer.parseInt(hashMap.get("x"));
            int y = Integer.parseInt(hashMap.get("y"));

            CGPoint cgPoint = CCNode.ccp(x, y);
            points.add(cgPoint);
        }
        return points;
    }

    /**
     * 创建了序列帧的动作
     * @param foramt 格式化路径
     * @param num 帧数
     * @param isForver 是否永不停止循环
     * @return
     */
    public static CCAction getAnimate(String foramt, int num, boolean isForver){
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
//        String foramt = "image/loading/loading_%02d.png";
        for (int i=1; i<=num; i++){
            CCSpriteFrame ccSpriteFrame = CCSprite.sprite(String.format(foramt, i)).displayedFrame();
            frames.add(ccSpriteFrame);
        }
        CCAnimation anim = CCAnimation.animation("loading", 0.2f, frames);

        if (isForver){
            CCAnimate animate = CCAnimate.action(anim);
            CCRepeatForever forever = CCRepeatForever.action(animate);
            return forever;
        }else{
            //序列帧一般必须是永不停止的播放，不需要永不停止播放需要制定第二个参数为false
            CCAnimate animate = CCAnimate.action(anim, false);
            return animate;
        }


    }

}
