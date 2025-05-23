package com.yanisdiss.javarras;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public final class GDShapeRenderer implements GameDrawer {
    private static final int circleSegs = 100;

    public void drawGuns(GameEntity entity, ShapeRenderer shapeRenderer) {
        for (Gun gun : entity.getGuns()) {

            float gunWidth = gun.getWidth() * entity.getSize()/20;
            float gunLength = gun.getLength() * 2 * entity.getSize()/20;
            float gunAngle = gun.getAngle();
            float gunX = gun.getX();
            float gunY = gun.getY();
            Color lighter = GameUtils.brightnessShift(gun.getColor(), 100);
            Color gunColor = (entity.isInjured()) ? lighter : gun.getColor();
            Color darker = GameUtils.darker(gunColor);

            float gunAspect = (gun.getAspect() < -1) ? Math.abs(gun.getAspect()) : (-1 < gun.getAspect() && gun.getAspect() <= 0) ? Math.abs(gun.getAspect()) : 1;
            float gunAspect2 = (gun.getAspect() > 1) ? Math.abs(gun.getAspect()) : (0 < gun.getAspect() && gun.getAspect() <= 1) ? gun.getAspect() : 1;

            double angle = entity.getAngle() + gunAngle;
            double offsetX = gunX * Math.cos(angle) - gunY * Math.sin(angle);
            double offsetY = gunX * Math.sin(angle) + gunY * Math.cos(angle);
            double baseX = entity.getX() + offsetX * 2;
            double baseY = entity.getY() + offsetY * 2;


            double p1x = baseX + gunWidth * Math.sin(angle) * gunAspect;
            double p1y = baseY - gunWidth * Math.cos(angle) * gunAspect;

            double p2x = baseX - gunWidth * Math.sin(angle) * gunAspect;
            double p2y = baseY + gunWidth * Math.cos(angle) * gunAspect;

            double p3x = baseX + gunLength * Math.cos(angle) - gunWidth * Math.sin(angle) * gunAspect2;
            double p3y = baseY + gunLength * Math.sin(angle) + gunWidth * Math.cos(angle) * gunAspect2;

            double p4x = baseX + gunLength * Math.cos(angle) + gunWidth * Math.sin(angle) * gunAspect2;
            double p4y = baseY + gunLength * Math.sin(angle) - gunWidth * Math.cos(angle) * gunAspect2;

            float[] vertices = {
                (float)p1x, GameUtils.topY((float)p1y),
                (float)p2x, GameUtils.topY((float)p2y),
                (float)p3x, GameUtils.topY((float)p3y),
                (float)p4x, GameUtils.topY((float)p4y)
            };

            // the gun main stroke
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Gdx.gl20.glLineWidth(GameConfig.STROKE_WIDTH * 2);
            shapeRenderer.setColor(darker);
            shapeRenderer.polygon(vertices);
            shapeRenderer.end();

            // to fill the outline gaps
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            for (int i = 0; i < 4; i++) {
                shapeRenderer.circle(vertices[0+2*i], vertices[1+2*i], GameConfig.STROKE_WIDTH, circleSegs);
            }

            shapeRenderer.end();

            // the gun itself
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(gunColor);
            //shapeRenderer.polygon(vertices);
            shapeRenderer.triangle(vertices[0], vertices[1], vertices[2], vertices[3], vertices[4], vertices[5]);
            shapeRenderer.triangle(vertices[4], vertices[5], vertices[6], vertices[7], vertices[0], vertices[1]);
            shapeRenderer.end();

        }

    }

    // draw entities
    public void drawEntity(GameEntity entity, ShapeRenderer shapeRenderer) {


        float x = entity.getX();
        float y = GameUtils.topY(entity.getY());
        float size = entity.getSize();
        Color lighter = GameUtils.brightnessShift(entity.getColor(), 100);
        Color color = (entity.isInjured()) ? lighter : entity.getColor();
        Color darker = GameUtils.darker(color);

        if (entity.CanRender()) {

            if (!entity.getGuns().isEmpty()){
                drawGuns(entity, shapeRenderer);
            }

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(darker);
            shapeRenderer.circle(x, y, size + GameConfig.STROKE_WIDTH, circleSegs);
            shapeRenderer.setColor(color);
            shapeRenderer.circle(x, y, size, circleSegs);
            shapeRenderer.end();

        }
    }

    public void drawHealth(GameEntity entity, ShapeRenderer shapeRenderer) {
        float outer = 2;
        float barHeight =3;
        float offsetY = entity.getSize() + barHeight + 8;
        float barWidth = entity.getSize() * 2 - outer*2 ;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(GameColors.black);

        // round outline edges
        shapeRenderer.circle(
            entity.getX() - barWidth/2,
            GameUtils.topY( entity.getY() + offsetY + outer/2 - (barHeight+outer) / 2),
            (barHeight + outer*2)/2, circleSegs);
        shapeRenderer.circle(
            entity.getX() + barWidth/2,
            GameUtils.topY( entity.getY() + offsetY + outer/2 - (barHeight+outer) / 2),
            (barHeight + outer*2)/2, circleSegs);

        // the black bar outline
        shapeRenderer.rect(
            entity.getX() - barWidth / 2,
            GameUtils.topY( entity.getY() + offsetY + outer),
            barWidth,
            barHeight + outer * 2
        );

        shapeRenderer.setColor(GameColors.green);
        // the bar itself
        shapeRenderer.rect(
            entity.getX() - barWidth / 2,
            GameUtils.topY(entity.getY() + offsetY),
            barWidth * entity.getHealth() / entity.getMaxHealth(),
            barHeight
        );
        // round edges 2
        shapeRenderer.circle(
            entity.getX() - barWidth/2,
            GameUtils.topY( entity.getY() + offsetY + outer/2 - (barHeight+outer) / 2),
            (barHeight)/2, circleSegs);
        shapeRenderer.circle(
            entity.getX() + barWidth * (entity.getHealth() / entity.getMaxHealth()) - barWidth/2,
            GameUtils.topY(entity.getY() + offsetY + outer/2 - (barHeight+outer) / 2),
            (barHeight)/2, circleSegs);

        shapeRenderer.end();
    }

    public void drawGrid(float gridSize , ShapeRenderer shapeRenderer) {
        float x = 0;
        float y = 0;
        float width = 1;

        Gdx.gl.glLineWidth(width);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(GameColors.grid);

        while (x < GameConfig.WINDOW_WIDTH) {
            shapeRenderer.line(x, 0, x, GameConfig.WINDOW_HEIGHT);
            x += gridSize;
        }

        while (y < GameConfig.WINDOW_HEIGHT) {
            shapeRenderer.line(0, y, GameConfig.WINDOW_WIDTH,y);
            y += gridSize;
        }

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1f);
    }
}
