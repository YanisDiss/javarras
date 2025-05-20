package com.yanisdiss.javarras;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

import com.yanisdiss.javarras.utils;
import com.yanisdiss.javarras.colors;
import com.yanisdiss.javarras.gameConfig;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class javarras extends ApplicationAdapter {
    ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void render() {

        ScreenUtils.clear(colors.bg);

        float x = utils.getWindowDimensions()[0] / 2f;
        float y = utils.getWindowDimensions()[1] / 2f;
        float radius = 20;

        Color myColor = colors.red;
        Color darker = utils.darker(myColor);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // or ShapeType.Line for outline
        shapeRenderer.setColor(darker);

        shapeRenderer.circle(x, y, radius + gameConfig.STROKE_WIDTH,100);

        shapeRenderer.setColor(myColor);
        shapeRenderer.circle(x, y, radius,100);

        shapeRenderer.end();

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
