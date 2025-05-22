package com.yanisdiss.javarras;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Javarras extends ApplicationAdapter {
    ShapeRenderer shapeRenderer;
    GameEntity player;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

        float x = GameConfig.SPAWN_POINT[0];
        float y = GameConfig.SPAWN_POINT[1];
        float size = 20;
        int health = 100;

        Gun basicGun = new Gun(null, 18, 8, 1, 0, 0, 90);

        Gun[] boosterGuns = {
            new Gun(null, 18, 8, 1, 0, 0, 0),
            new Gun(null, 14, 8, 1, 0, -1, 140),
            new Gun(null, 14, 8, 1, 0, 1, -140),
            new Gun(null, 16, 8, 1, 0, 0, 150),
            new Gun(null, 16, 8, 1, 0, 0, -150)
        };

        player = new GameEntity(GameColors.blue,x,y,size,health);

        for (int i = 0; i < 5; i++) {
            player.addGun(boosterGuns[i]);
        }

        GameGlobals.entities.add(player);
        GameEntity testEntity = new GameEntity(GameColors.pink,x+200,y+200,size,health);
        GameGlobals.entities.add(testEntity);

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(GameColors.bg);
        GameDraw.drawGrid(20,shapeRenderer);
if (GameGlobals.entities != null && !GameGlobals.entities.isEmpty())
        {
            // draw entities
            for (GameEntity entity : GameGlobals.entities) {
                if (entity.isAlive()) {
                    GameDraw.drawEntity(entity, shapeRenderer);
                }

                entity.step(delta);
            }
            // draw hp bars above all entities
            for (GameEntity entity : GameGlobals.entities) {
                if (entity.isAlive()) {
                    GameDraw.drawHealth(entity, shapeRenderer);
                }

                entity.step(delta);
            }
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
