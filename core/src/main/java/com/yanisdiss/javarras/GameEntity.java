package com.yanisdiss.javarras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameEntity {
    private static int playerId = 0;
    private static int entityId = 0;

    private String name, label;
    private float size;
    private float x,y;
    private float vx,vy;
    private float ax,ay;
    private float speed = 1;
    private Color color;
    private float health, maxHealth;
    private float angle;
    private boolean isAlive;
    private boolean canRender;
    private boolean canCollide;
    private final int id;
    private List<Gun> guns = new ArrayList<>();

    public GameEntity(Color color, float x, float y, float size, float health) {
        this.color = (color != null) ? color : GameColors.grey;
        this.x = x;
        this.y = y;
        this.size = size;
        this.health = health;
        this.maxHealth = health;
        this.isAlive = true;
        this.canRender = true;
        this.canCollide = true;
        this.id = entityId;
        incrementId();
    }

    public void handleInput(float delta) {
        // Direct velocity control
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            this.move(delta,"left");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            this.move(delta,"right");
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            this.move(delta,"up");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            this.move(delta,"down");
        }
    }

    public void step(float delta) {

        if (this.isAlive){
            if (this.id == playerId) {
                int mouseX = GameUtils.getMouseX();
                int mouseY = GameUtils.getMouseY();
                this.angle = (float) Math.atan2(mouseY - this.y, mouseX - this.x);
                GameConfig.CAMERA_X = this.x;
                GameConfig.CAMERA_Y = this.y;
                handleInput(delta);

            }
            this.vx += this.ax * delta;
            this.vy += this.ay * delta;

            this.vx *= 0.95f;
            this.vy *= 0.95f;

            this.x += this.vx * delta;
            this.y += this.vy * delta;

            // todo: add collision below this

        } else
        {
            // todo: add death anim
        }

    }

    public void move(float delta ,String way){
        switch(way) {
            case "left":
                this.vx -= 1000 * this.speed * delta;
                break;
            case "right":
                this.vx += 1000 * this.speed  * delta;
                break;
            case "up":
                this.vy -= 1000 * this.speed * delta;
                break;
            case "down":
                this.vy += 1000 * this.speed * delta;
                break;
            default:
                break;
                // todo: normalize speed when pressing 2 keys at once
        }
    }

    public void kill() {
        this.health = 0;
        this.isAlive = false;
        //GameGlobals.entities.remove(this); todo: fix game crashes
    }

    public void changeHealth(float hlt) {
        this.health = Math.min(this.health + hlt , this.maxHealth);
        if (this.health <= 0) this.kill();
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void addGun(Gun gun) {
        this.guns.add(gun);
    }
}
