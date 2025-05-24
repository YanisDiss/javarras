package com.yanisdiss.javarras;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;

public final class GameUtils {
    public static Color darker(Color color) {
        return new Color(color.r / 2f, color.g / 2f, color.b / 2f, color.a);
    }

 public static Color brightnessShift(Color color, int amount) {
        float shift = amount / 255f;
        return new Color(
            Math.max(Math.min(color.r + shift,255),0),
            Math.max(Math.min(color.g + shift,255),0),
            Math.max(Math.min(color.b + shift,255),0),
            color.a);
    }

    /*public static int getWindowWidth() {
        return Gdx.graphics.getWidth();
    }

    public static int getWindowHeight() {
        return Gdx.graphics.getHeight();
    }*/

    public static int[] getMouse() {
        return new int[]{Gdx.input.getX(), Gdx.input.getY()};
    }


    public static float[] getWorldMouse() {
        float x = (Gdx.input.getX() - GameConfig.WINDOW_WIDTH / 2f) * GameConfig.DEFAULT_FOV + GameConfig.CAMERA_X;
        float y = (Gdx.input.getY() - GameConfig.WINDOW_HEIGHT / 2f) * GameConfig.DEFAULT_FOV + GameConfig.CAMERA_Y;
        return new float[]{x, y};
    }

    public static float topY(float y) {
        return GameConfig.WINDOW_HEIGHT - y;
    }

}
