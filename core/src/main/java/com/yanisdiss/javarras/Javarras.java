package com.yanisdiss.javarras;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Javarras extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private GameEntity player;
    private final GameDrawer gameRenderer = new GDShapeRenderer();

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

        Gun basicGun = new Gun(player,null, 14, 10, 1f, 0, 0, 0);

        player = new GameEntity(GameColors.blue, GameConfig.SPAWN_POINT[0], GameConfig.SPAWN_POINT[1], 20, 100,0);

        Gun[] boosterGuns = {
            new Gun(player, null, 18, 8, 1, 0, 0, 0),
            new Gun(player,null, 14, 8, 1, 0, -1, 140),
            new Gun(player,null, 14, 8, 1, 0, 1, -140),
            new Gun(player,null, 16, 8, 1, 0, 0, 150),
            new Gun(player,null, 16, 8, 1, 0, 0, -150)
        };

        for (int i = 0; i < 5; i++) {
            player.addGun(boosterGuns[i]);
        }

        GameGlobals.entities.add(player);
        GameEntity testEntity = new GameEntity(GameColors.pink, GameConfig.SPAWN_POINT[0] + 200, GameConfig.SPAWN_POINT[1] + 200, 20, 100, 69);
        testEntity.addGun(basicGun);
        GameGlobals.entities.add(testEntity);

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(GameColors.outerbg);
        gameRenderer.drawArena(shapeRenderer);
        InputManager.handleKeyCommands(null);
        if (GameGlobals.entities != null && !GameGlobals.entities.isEmpty()) {
            for (GameEntity entity : new ArrayList<>(GameGlobals.entities)) {
                // handle inputs for each entity
                InputManager.handleKeyCommands(entity);
                // draw entities
                if (entity.isAlive()) {
                    gameRenderer.drawEntity(entity, shapeRenderer);
                }
                entity.step(delta);
            }
            // draw hp bars above all entities
            for (GameEntity entity : new ArrayList<>(GameGlobals.entities)) {
                if (entity.isAlive() && entity.getHealth() < entity.getMaxHealth() ) {
                    gameRenderer.drawHealth(entity, shapeRenderer);
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
