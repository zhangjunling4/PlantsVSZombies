package com.seek.plantsvszombies.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

/**
 * Created by admin on 2017/9/8.
 */

public class BaseLayer extends CCLayer {
    public CGSize winSize;

    public BaseLayer() {
        winSize = CCDirector.sharedDirector().getWinSize();
    }


}
