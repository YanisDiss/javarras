package com.yanisdiss.javarras;


public final class GameConfig {

    public static float STROKE_WIDTH = 3f;

    // window stuff
    final public static short WINDOW_WIDTH = 1280;
    final public static short WINDOW_HEIGHT = 720;
    final public static boolean RESIZABLE_WINDOW = false;
    public static boolean LOW_GRAPHICS = false;



    public static int[] ARENA_DIMENSIONS = {3000,3000};
    public static float DEFAULT_FOV = 1; // will be replaced
    public static float CAMERA_X = ARENA_DIMENSIONS[0] / 2f;
    public static float CAMERA_Y = ARENA_DIMENSIONS[1] / 2f;

    public static float[] SPAWN_POINT = {WINDOW_WIDTH / 2f , WINDOW_HEIGHT / 2f};
    //public static float[] SPAWN_POINT = {ARENA_DIMENSIONS[0] / 2f, ARENA_DIMENSIONS[1] / 2f};

    // static float[] SPAWN_POINT = {utils.getWindowWidth() / 2f, utils.getWindowHeight() / 2f};

}
