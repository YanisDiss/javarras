package com.yanisdiss.javarras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class utils {
    public static Color darker(Color color) {
        return new Color(color.r / 2f, color.g / 2f, color.b / 2f, color.a);
    }

    public static int[] getWindowDimensions() {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        return new int[] {w,h};

    }

}
