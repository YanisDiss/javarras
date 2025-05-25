package com.yanisdiss.javarras;


public final class GameConfig {

    // graphics related stuff
    public static float STROKE_WIDTH = 3f;
    final public static short WINDOW_WIDTH = 1280;
    final public static short WINDOW_HEIGHT = 720;
    final public static short FRAMES_PER_SECOND = 60;
    final public static boolean RESIZABLE_WINDOW = false;
    public static boolean LOW_GRAPHICS = false;
    public static float CLIENT_FOV = 1f; // will be replaced
    public static float CAMERA_X = 0;
    public static float CAMERA_Y = 0;
    public static boolean ROUND_BORDERS = true;

    // in-game related stuff
    public static int[] ARENA_DIMENSIONS = {2000,2000};
    public static float ARENA_BOUND_FORCE = 5f;
    public static float[] SPAWN_POINT = {ARENA_DIMENSIONS[0] / 2f, ARENA_DIMENSIONS[1] / 2f};

}
