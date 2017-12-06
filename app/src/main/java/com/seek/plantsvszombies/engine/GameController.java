package com.seek.plantsvszombies.engine;

import com.seek.plantsvszombies.base.PrimaryZombies;
import com.seek.plantsvszombies.bean.ShowPlant;
import com.seek.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 处理游戏开始后的操作
 */

public class GameController {

    private static GameController controller = new GameController();
    private CCTMXTiledMap map;
    private List<ShowPlant> selectPlants;

    private List<CGPoint> roadPoints;

    private GameController(){
    }

    public static GameController getInstance(){
        return controller;
    }

    public boolean isStart;//游戏是否开始

    private static List<FightLine> lines;
    static {
        lines = new ArrayList<>();
        for (int i=0; i<5; i++){
            FightLine fightLine = new FightLine(i);
            lines.add(fightLine);
        }
    }
//    private
    /**
     * 开始游戏
     * @param map  游戏地图
     * @param selectPlants 玩家已选择植物集合
     */
    public void startGame(CCTMXTiledMap map, List<ShowPlant> selectPlants){
        isStart = true;

        this.map = map;
        this.selectPlants = selectPlants;

        loadMap();
        //添加僵尸
        //参数1：方法名（方法带float类型参数）；参数2：调用方法的对象  参数3：间隔时间  参数4：是否暂停
        //定时器
        CCScheduler.sharedScheduler().schedule("addZombies", this, 3, false);

        //安放植物


        //僵尸攻击植物


        //植物攻击僵尸
    }

    private void loadMap() {
        roadPoints = CommonUtils.getMapPoints(map, "road");
    }

    /**
     * 添加僵尸
     * @param t
     */
    public void addZombies(float t) {
        Random random = new Random();
        int lineNum = random.nextInt(5); //[0, 5)

        PrimaryZombies primaryZombies = new PrimaryZombies(roadPoints.get(lineNum * 2), roadPoints.get(lineNum * 2+1));
        map.addChild(primaryZombies);

        lines.get(lineNum).addZombies(primaryZombies);//把僵尸记录在行战场中

    }

    public void endGame(){
        isStart = false;
    }




}
