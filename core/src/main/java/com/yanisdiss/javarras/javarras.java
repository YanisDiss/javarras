package com.yanisdiss.javarras;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class javarras extends ApplicationAdapter {
    ShapeRenderer shapeRenderer;
    gameEntity player;
    gameEntity testEntity;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

        float x = gameConfig.SPAWN_POINT[0];
        float y = gameConfig.SPAWN_POINT[1];
        float size = 20;
        int health = 100;

        player = new gameEntity(gameColors.blue,x,y,size,health);
        gameGlobals.entities.add(player);
        testEntity = new gameEntity(gameColors.pink,x+50,y+50,size,health);
        gameGlobals.entities.add(testEntity);

    }

    @Override
    public void render() {

        ScreenUtils.clear(gameColors.bg);
        for (gameEntity entity : gameGlobals.entities) {
            gameDraw.drawEntity(entity, shapeRenderer);
        }






    }

    @Override
    public void resize(int width, int height) {
       // hi
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
