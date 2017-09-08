package com.seek.plantsvszombies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity {

    private CCDirector director;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
        setContentView(surfaceView);

        director = CCDirector.sharedDirector();
        director.attachInView(surfaceView);//开启线程

        director.setDisplayFPS(true);//显示帧率;
        director.setScreenSize(480, 320);
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);

        CCScene scene = CCScene.node();



        director.runWithScene(scene);
    }

    @Override
    protected void onResume() {
        super.onResume();
        director.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        director.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        director.end();
    }
}