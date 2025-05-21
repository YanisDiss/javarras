package com.yanisdiss.javarras;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class GameEntity {
    private static int playerId = 0;
    private static int entityId = 0;

    private String name, label;
    private float size;
    private float x,y;
    private Color color;
    private int health;
    private float angle;
    private boolean isAlive;
    private boolean canRender;
    private final int id;
    private List<Gun> guns = new ArrayList<>();

    public GameEntity(Color color, float x, float y, float size, int health) {
        this.color = (color != null) ? color : GameColors.grey;
        this.x = x;
        this.y = y;
        this.size = size;
        this.health = health;
        this.isAlive = true;
        this.canRender = true;
        this.id = entityId;
        incrementId();
    }

    public void step() {
        if (this.id == playerId) {
            int mouseX = GameUtils.getMouseX();
            int mouseY = GameUtils.getMouseY();
            this.angle = (float)Math.atan2(mouseY - this.y,mouseX - this.x);
        }

    }

    public void incrementId() {
       entityId++;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int health) {
        this.health += health;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean CanRender() {
        return canRender;
    }

    public void setCanRender(boolean canRender) {
        this.canRender = canRender;
    }

    public int getId() {
        return id;
    }

    public List<Gun> getGuns() {
        return guns;
    }

    public void addGun(Gun gun) {
        this.guns.add(gun);
    }
}
