package com.yanisdiss.javarras;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.yanisdiss.javarras.GameEntity;

public interface GameDrawer {
    void drawGuns(GameEntity entity, ShapeRenderer shapeRenderer);
    void drawEntity(GameEntity entity, ShapeRenderer shapeRenderer);
    void drawHealth(GameEntity entity, ShapeRenderer shapeRenderer);
    void drawGrid(float gridSize, ShapeRenderer shapeRenderer);
    void drawArena(ShapeRenderer shapeRenderer);
}
