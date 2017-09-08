package com.seek.plantsvszombies.utils;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFlipXTransition;

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
}
