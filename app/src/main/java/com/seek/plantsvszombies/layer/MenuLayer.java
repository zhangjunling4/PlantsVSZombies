package com.seek.plantsvszombies.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;

/**
 * Created by admin on 2017/9/8.
 */

public class MenuLayer extends CCLayer {
    public MenuLayer() {
        init();
    }

    private void init() {
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\menu\main_menu_bg.jpg
        CCSprite sprite = CCSprite.sprite("image/menu/main_menu_bg.jpg");
        sprite.setAnchorPoint(0, 0);
        this.addChild(sprite);
    }
}
