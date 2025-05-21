package com.yanisdiss.javarras;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public final class gameDraw {
    private static final int circleSegs = 100;
    public static void drawEntity(gameEntity entity, ShapeRenderer shapeRenderer) {

        float x = entity.getX();
        float y = gameUtils.topY(entity.getY());
        float size = entity.getSize();
        Color color = entity.getColor();
        Color darker = gameUtils.darker(entity.getColor());

        if (entity.CanRender()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // or ShapeType.Line for outline
            shapeRenderer.setColor(darker);

            shapeRenderer.circle(x, y, size + gameConfig.STROKE_WIDTH, circleSegs);

            shapeRenderer.setColor(color);
            shapeRenderer.circle(x, y, size, circleSegs);

            shapeRenderer.end();
        }
    }
}
