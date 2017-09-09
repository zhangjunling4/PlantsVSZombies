package com.seek.plantsvszombies.layer;

import com.seek.plantsvszombies.bean.ShowPlant;
import com.seek.plantsvszombies.bean.ShowZombies;
import com.seek.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.List;

/**
 * Created by admin on 2017/9/9.
 */

public class FightLayer extends BaseLayer {
    private CCTMXTiledMap map;
    private CGSize contentSize;

    CCSprite chooesd;
    CCSprite noChooes;

    private List<CGPoint> zombilesPoints;
    public FightLayer() {
        init();
    }

    private void init() {
        loadMap();
        parseMap();
        showZombies();
        moveMap();
    }

    /**
     * 加载展示用的僵尸
     */
    private void showZombies() {
        for (int i=0; i<zombilesPoints.size(); i++){
            CGPoint cgPoint = zombilesPoints.get(i);
            ShowZombies showZombies = new ShowZombies();
            showZombies.setPosition(cgPoint);//给展示用的僵尸设置位置
            map.addChild(showZombies);//注意：把僵尸加载到地图上而不是图层上
        }
    }

    private void parseMap() {
        zombilesPoints = CommonUtils.getMapPoints(map, "zombies");
    }

    /**
     * 移动地图
     */
    private void moveMap() {
        int x = (int) (winSize.getWidth() - contentSize.getWidth());
        CCMoveBy moveBy = CCMoveBy.action(2, ccp(x, 0));
        CCSequence sequence = CCSequence.actions(CCDelayTime.action(3), moveBy,
                CCDelayTime.action(2), CCCallFunc.action(this, "loadContaier"));
        map.runAction(sequence);
    }

    private void loadMap() {
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\fight\map_day.tmx
        map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
        map.setAnchorPoint(0.5f, 0.5f);
        contentSize = map.getContentSize();
        map.setPosition(contentSize.width/2, contentSize.height/2);
        this.addChild(map);
    }

    public void loadContaier(){
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\fight\chose\fight_chose.png
        chooesd = CCSprite.sprite("image/fight/chose/fight_chose.png");
        chooesd.setAnchorPoint(0, 1);
        chooesd.setPosition(0, winSize.height);
        this.addChild(chooesd);

        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\fight\chose\fight_choose.png
        noChooes = CCSprite.sprite("image/fight/chose/fight_choose.png");
        noChooes.setAnchorPoint(0, 0);
        this.addChild(noChooes);

        loadShowPlant();
    }


    CCSprite showSprite;//获取到展示的精灵

    private void loadShowPlant() {
        for (int i=1; i<=9; i++){
            ShowPlant plant = new ShowPlant(i);//创建了展示的而植物
            CCSprite bgSprite = plant.getBgSprite();
            bgSprite.setPosition(16 + ((i - 1) %4) * 54, 175 - ((i - 1) / 4 * 59));
            noChooes.addChild(bgSprite);

            showSprite = plant.getShowSprite();
            //设置位置
            showSprite.setPosition(16 + ((i - 1) %4) * 54, 175 - ((i - 1) / 4 * 59));
            noChooes.addChild(showSprite);//添加到容器上
        }
    }
}
