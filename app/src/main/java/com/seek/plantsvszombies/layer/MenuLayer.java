package com.seek.plantsvszombies.layer;

import android.util.Log;

import com.seek.plantsvszombies.utils.CommonUtils;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

/**
 * Created by admin on 2017/9/8.
 */

public class MenuLayer extends BaseLayer {
    private static final String TAG = MenuLayer.class.getSimpleName();

    public MenuLayer() {
        init();
    }

    private void init() {
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\menu\main_menu_bg.jpg
        CCSprite sprite = CCSprite.sprite("image/menu/main_menu_bg.jpg");
        sprite.setAnchorPoint(0, 0);
        this.addChild(sprite);

        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\menu\start_adventure_default.png
        CCSprite normalSprite = CCSprite.sprite("image/menu/start_adventure_default.png");
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\menu\start_adventure_press.png
        CCSprite selectorSprite = CCSprite.sprite("image/menu/start_adventure_press.png");
        CCMenuItem items = CCMenuItemSprite.item(normalSprite, selectorSprite, this, "click");

        CCMenu menu = CCMenu.menu(items);
        menu.setScale(0.5f);
        menu.setPosition(100, 100);
        menu.setPosition(winSize.width / 2-25, winSize.height / 2 - 110);
        menu.setRotation(4.5f);

        this.addChild(menu);
    }

    public void click(Object object){
        Log.i(TAG, "开始冒险吧！");
        CommonUtils.changeLayer(new FightLayer());
    }
}
