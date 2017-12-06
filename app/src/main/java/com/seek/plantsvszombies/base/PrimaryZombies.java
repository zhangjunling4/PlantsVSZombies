package com.seek.plantsvszombies.base;

import com.seek.plantsvszombies.engine.GameController;
import com.seek.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

/**
 * Created by admin on 2017/9/16.
 */

public class PrimaryZombies extends Zombies {
    public PrimaryZombies(CGPoint startPoint, CGPoint endPoint) {
        //D:\AndroidPracticeProjects\PlantsVSZombies\app\src\main\assets\image\zombies\zombies_1\walk\z_1_01.png
        super("image/zombies/zombies_1/walk/z_1_01.png");
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        setPosition(startPoint);

        move();
    }

    @Override
    public void move() {
        CCAction animate = CommonUtils.getAnimate("image/zombies/zombies_1/walk/z_1_%02d.png", 7, true);
        this.runAction(animate);

        float t = CGPointUtil.distance(getPosition(), endPoint) / speed;
        CCMoveTo moveTo = CCMoveTo.action(t, endPoint);
        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "endGame"));
        this.runAction(sequence);

    }

    public void endGame(){
//        GameController.getInstance().endGame();
        this.destroy();
    }

    @Override
    public void attack(BaseElement element) {

    }

    @Override
    public void attacked(int attack) {

    }

    @Override
    public void baseAction() {

    }
}
