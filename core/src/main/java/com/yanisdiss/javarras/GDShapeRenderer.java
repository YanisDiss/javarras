package com.yanisdiss.javarras;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public final class GDShapeRenderer implements GameDrawer {
    private static final int circleSegs = 100;


private boolean isEntityOutsideCamera(GameEntity entity)
    {
        float cameraLeft = GameConfig.CAMERA_X - (GameConfig.WINDOW_WIDTH * GameConfig.DEFAULT_FOV) / 2;
        float cameraRight = GameConfig.CAMERA_X + (GameConfig.WINDOW_WIDTH * GameConfig.DEFAULT_FOV) / 2;
        float cameraBottom = GameConfig.CAMERA_Y - (GameConfig.WINDOW_HEIGHT * GameConfig.DEFAULT_FOV) / 2;
        float cameraTop = GameConfig.CAMERA_Y + (GameConfig.WINDOW_HEIGHT * GameConfig.DEFAULT_FOV) / 2;

        boolean isOutside = entity.getX() + entity.getSize() < cameraLeft ||
            entity.getX() - entity.getSize() > cameraRight ||
            entity.getY() + entity.getSize() < cameraBottom ||
            entity.getY() - entity.getSize() > cameraTop;

        return isOutside;
    }


    public void drawGuns(GameEntity entity, ShapeRenderer shapeRenderer) {

        for (Gun gun : entity.getGuns()) {
            float renderX = (entity.getX() - GameConfig.CAMERA_X + (GameConfig.WINDOW_WIDTH * GameConfig.DEFAULT_FOV) / 2) / GameConfig.DEFAULT_FOV;
            float renderY = (entity.getY() - GameConfig.CAMERA_Y + (GameConfig.WINDOW_HEIGHT * GameConfig.DEFAULT_FOV) / 2) / GameConfig.DEFAULT_FOV;
            float gunWidth = gun.getWidth() * entity.getSize()/20 / GameConfig.DEFAULT_FOV;
            float gunLength = gun.getLength() * 2 * entity.getSize()/20 / GameConfig.DEFAULT_FOV;
            float gunAngle = gun.getAngle();
            float gunX = gun.getX() / GameConfig.DEFAULT_FOV;
            float gunY = gun.getY() / GameConfig.DEFAULT_FOV;

            Color lighter = GameUtils.brightnessShift(gun.getColor(), 100);
            Color gunColor = (entity.isInjured()) ? lighter : gun.getColor();
            Color darker = GameUtils.darker(gunColor);

            float gunAspect = (gun.getAspect() < -1) ? Math.abs(gun.getAspect()) : (-1 < gun.getAspect() && gun.getAspect() <= 0) ? Math.abs(gun.getAspect()) : 1;
            float gunAspect2 = (gun.getAspect() > 1) ? Math.abs(gun.getAspect()) : (0 < gun.getAspect() && gun.getAspect() <= 1) ? gun.getAspect() : 1;
            double angle = entity.getAngle() + gunAngle;
            double offsetX = gunX * Math.cos(angle) - gunY * Math.sin(angle);
            double offsetY = gunX * Math.sin(angle) + gunY * Math.cos(angle);
            double baseX = renderX + offsetX * 2;
            double baseY = renderY + offsetY * 2;
            float renderStrokeWidth = GameConfig.STROKE_WIDTH / GameConfig.DEFAULT_FOV;


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
            Gdx.gl20.glLineWidth(renderStrokeWidth * 2);
            shapeRenderer.setColor(darker);
            shapeRenderer.polygon(vertices);
            shapeRenderer.end();

            // to fill the outline gaps
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            for (int i = 0; i < 4; i++) {
                shapeRenderer.circle(vertices[0+2*i], vertices[1+2*i], renderStrokeWidth, circleSegs);
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
    if (!isEntityOutsideCamera(entity))
        {
            float renderStrokeWidth = GameConfig.STROKE_WIDTH / GameConfig.DEFAULT_FOV;
            float renderX = (entity.getX() - GameConfig.CAMERA_X + (GameConfig.WINDOW_WIDTH * GameConfig.DEFAULT_FOV) / 2) / GameConfig.DEFAULT_FOV;
            float renderY = (entity.getY() - GameConfig.CAMERA_Y + (GameConfig.WINDOW_HEIGHT * GameConfig.DEFAULT_FOV) / 2) / GameConfig.DEFAULT_FOV;
            float size = entity.getSize() / GameConfig.DEFAULT_FOV;

            Color lighter = GameUtils.brightnessShift(entity.getColor(), 100);
            Color color = (entity.isInjured()) ? lighter : entity.getColor();
            Color darker = GameUtils.darker(color);

            if (!entity.getGuns().isEmpty()) {
                drawGuns(entity, shapeRenderer);
            }

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(darker);
            shapeRenderer.circle(renderX, GameUtils.topY(renderY), size + renderStrokeWidth, circleSegs);
            shapeRenderer.setColor(color);
            shapeRenderer.circle(renderX, GameUtils.topY(renderY), size, circleSegs);
            shapeRenderer.end();
        }


    }

    public void drawHealth(GameEntity entity, ShapeRenderer shapeRenderer) {
        float outer = 2 / GameConfig.DEFAULT_FOV;
        float barHeight =3 / GameConfig.DEFAULT_FOV;
        float barWidth = (entity.getSize() * 2) / GameConfig.DEFAULT_FOV ;

        float renderX = (entity.getX() - GameConfig.CAMERA_X + GameConfig.WINDOW_WIDTH * GameConfig.DEFAULT_FOV / 2) / GameConfig.DEFAULT_FOV;
        float renderY = (entity.getY() - GameConfig.CAMERA_Y + GameConfig.WINDOW_HEIGHT * GameConfig.DEFAULT_FOV / 2) / GameConfig.DEFAULT_FOV;

        float renderOffsetX = -(entity.getSize() / GameConfig.DEFAULT_FOV ) + renderX;
        float renderOffsetY = (entity.getSize() + barHeight + 8) / GameConfig.DEFAULT_FOV + renderY;




        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(GameColors.black);

        // round outline edges
        shapeRenderer.circle(
            renderOffsetX,
            GameUtils.topY( renderOffsetY + outer/2 - (barHeight+outer) / 2),
            (barHeight + outer*2)/2, circleSegs);

        shapeRenderer.circle(
            renderOffsetX + barWidth,
            GameUtils.topY( renderOffsetY + outer/2 - (barHeight+outer) / 2),
            (barHeight + outer*2)/2, circleSegs);


        // the black bar outline
        shapeRenderer.rect(
            renderOffsetX,
            GameUtils.topY( renderOffsetY + outer),
            barWidth,
            (barHeight + outer * 2)
        );

        shapeRenderer.setColor(GameColors.green);
        // the bar itself
        shapeRenderer.rect(
            renderOffsetX,
            GameUtils.topY(renderOffsetY),
            barWidth * entity.getHealth() / entity.getMaxHealth(),
            barHeight
        );
        // round edges 2
        shapeRenderer.circle(
            renderOffsetX,
            GameUtils.topY(renderOffsetY - barHeight/2),
            (barHeight)/2, circleSegs);
        shapeRenderer.circle(
            renderOffsetX + barWidth * entity.getHealth() / entity.getMaxHealth(),
            GameUtils.topY(renderOffsetY - barHeight/2),
            (barHeight)/2, circleSegs);

        shapeRenderer.end();
    }

    public void drawGrid(float gridSize , ShapeRenderer shapeRenderer) {
        float x = (GameConfig.WINDOW_WIDTH / 2f - GameConfig.CAMERA_X / GameConfig.DEFAULT_FOV) % gridSize;
        float y = (GameConfig.WINDOW_HEIGHT / 2f - GameConfig.CAMERA_Y / GameConfig.DEFAULT_FOV) % gridSize;
        float width = 1 / GameConfig.DEFAULT_FOV;

        Gdx.gl.glLineWidth(width);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(GameColors.grid);

        while (x < GameConfig.WINDOW_WIDTH) {
            shapeRenderer.line(x, GameUtils.topY(0), x, GameUtils.topY(GameConfig.WINDOW_HEIGHT));
            x += gridSize;
        }

        while (y < GameConfig.WINDOW_HEIGHT) {
            shapeRenderer.line(0, GameUtils.topY(y), GameConfig.WINDOW_WIDTH,GameUtils.topY(y));
            y += gridSize;
        }

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1f);
    }

    public void drawArena(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(GameColors.bg);
        shapeRenderer.rect(
            -(GameConfig.CAMERA_X / GameConfig.DEFAULT_FOV) + GameConfig.WINDOW_WIDTH / 2f,
            GameUtils.topY(-(GameConfig.CAMERA_Y / GameConfig.DEFAULT_FOV) + GameConfig.WINDOW_HEIGHT / 2f),
            GameConfig.ARENA_DIMENSIONS[0] / GameConfig.DEFAULT_FOV,
            -GameConfig.ARENA_DIMENSIONS[1] / GameConfig.DEFAULT_FOV
        );
        shapeRenderer.end();
        drawGrid(20/GameConfig.DEFAULT_FOV, shapeRenderer);
    }
}
