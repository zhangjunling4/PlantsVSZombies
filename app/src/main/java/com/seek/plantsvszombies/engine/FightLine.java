package com.seek.plantsvszombies.engine;

import com.seek.plantsvszombies.base.Zombies;

import java.util.ArrayList;
import java.util.List;

/**
 * 把每一行的操作  抽取出来
 * 每一行都可以添加僵尸、安放植物、僵尸攻击植物。植物攻击僵尸
 */
public class FightLine {
    private int lineNum;

    public FightLine( int lineNum) {
        this.lineNum = lineNum;
    }

    private List<Zombies> zombiesList  = new ArrayList<>();

    public void addZombies(Zombies zombies){
        zombiesList.add(zombies);
    }
}
