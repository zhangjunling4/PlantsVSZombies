package com.seek.plantsvszombies.bean;

import com.seek.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCTexture2D;

/**
 * Created by admin on 2017/9/9.
 */

public class ShowZombies extends CCSprite {
    public ShowZombies() {
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\zombies\zombies_1\shake\z_1_01.png
        super("image/zombies/zombies_1/shake/z_1_01.png");
        this.setScale(0.5f);
        this.setAnchorPoint(0.5f, 0);
        CCAction animatate = CommonUtils.getAnimate(
                "image/zombies/zombies_1/shake/z_1_%02d.png", 2, true);//来回摇摆
        this.runAction(animatate);
    }
}
