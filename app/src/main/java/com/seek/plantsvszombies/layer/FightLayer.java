package com.seek.plantsvszombies.layer;

import android.view.MotionEvent;

import com.seek.plantsvszombies.bean.ShowPlant;
import com.seek.plantsvszombies.bean.ShowZombies;
import com.seek.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by admin on 2017/9/9.
 */

public class FightLayer extends BaseLayer {
    private static final String TAG = FightLayer.class.getSimpleName();
    private CCTMXTiledMap map;
    private CGSize contentSize;

    CCSprite chooesd;
    CCSprite noChooes;

    private List<ShowPlant> showPlants;
    private List<ShowPlant> selectPlants = new CopyOnWriteArrayList<>();

    private boolean isClick;//进行加锁，防止点击一个展示植物时，被多次点击
    private boolean isDelete;//对否删除了植物

    private CCSprite startButton;

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

        loadStartButton();
    }

    private void loadStartButton() {
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\fight\chose\fight_start.png
        startButton = CCSprite.sprite("image/fight/chose/fight_start.png");
        startButton.setPosition(noChooes.getContentSize().width /2, 30);
        noChooes.addChild(startButton);
    }


    CCSprite showSprite;//获取到展示的精灵

    /**
     * 加载展示用的植物
     */
    private void loadShowPlant() {
        showPlants = new ArrayList<>();
        for (int i=1; i<=9; i++){
            ShowPlant plant = new ShowPlant(i);//创建了展示的而植物
            CCSprite bgSprite = plant.getBgSprite();
            bgSprite.setPosition(16 + ((i - 1) %4) * 54, 175 - ((i - 1) / 4 * 59));
            noChooes.addChild(bgSprite);

            showSprite = plant.getShowSprite();
            //设置位置
            showSprite.setPosition(16 + ((i - 1) %4) * 54, 175 - ((i - 1) / 4 * 59));
            noChooes.addChild(showSprite);//添加到容器上

            showPlants.add(plant);
        }

        setIsTouchEnabled(true);//设置触摸事件开关按钮
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        CGPoint point = this.convertTouchToNodeSpace(event);
        CGRect noChooseBox = noChooes.getBoundingBox();
        CGRect choosed = chooesd.getBoundingBox();

        if (CGRect.containsPoint(choosed, point)){
            isDelete = false;
            for (ShowPlant chooesdPlant : selectPlants){
                CGRect selectPlantRect = chooesdPlant.getShowSprite().getBoundingBox();
                if (CGRect.containsPoint(selectPlantRect, point)){
                    CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, chooesdPlant.getBgSprite().getPosition());
                    chooesdPlant.getShowSprite().runAction(ccMoveTo);
                    selectPlants.remove(chooesdPlant);//反选植物
                    isDelete = true;
                    continue;
                }
                if (isDelete){
                    CCMoveBy ccMoveBy = CCMoveBy.action(0.5f, ccp(-53, 0));
                    chooesdPlant.getShowSprite().runAction(ccMoveBy);
                }
            }
        } else if (CGRect.containsPoint(noChooseBox, point)){
            if (CGRect.containsPoint(startButton.getBoundingBox(), point)){
                //点击了开始游戏的按钮

                readyFight();
            }else if (selectPlants.size() < 5 && !isClick ){//如果选择完成了 就不能再进行选择了
                //有可能选择的植物
                for (ShowPlant plant : showPlants ){
                    //如果点恰好落在植物展示的精灵的矩形中
                    if (CGRect.containsPoint(plant.getShowSprite().getBoundingBox(), point)){
                        //当前植物被选中了
                        isClick = true;
                        CCMoveTo moveTo = CCMoveTo.action(1, ccp(75 + selectPlants.size() * 53, 255));
                        //移动完成之后 解锁。
                        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "unLock"));
                        plant.getShowSprite().runAction(sequence);
                        selectPlants.add(plant);
                    }
                }
            }
        }
        return super.ccTouchesBegan(event);
    }

    /**
     * 点击了“一起来摇滚”
     */
    private void readyFight() {
        //把选中的植物重新添加到存在的容器上
        for (ShowPlant plant:selectPlants){
            plant.getShowSprite().setScale(0.65f);
            plant.getShowSprite().setPosition(plant.getShowSprite().getPosition().x*0.65f,
                    plant.getShowSprite().getPosition().y + (CCDirector.sharedDirector().getWinSize().height -
                            plant.getShowSprite().getPosition().y) * 0.35f);
            this.addChild(plant.getShowSprite());
        }

        noChooes.removeSelf();
        map.runAction(CCMoveBy.action(1, ccp((int)(map.getContentSize().width - winSize.width), 0)));

        chooesd.setScale(0.65f);

    }

    public void unLock(){
        isClick = false;
    }
}
