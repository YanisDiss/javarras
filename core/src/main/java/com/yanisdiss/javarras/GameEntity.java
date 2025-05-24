package com.yanisdiss.javarras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
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
    private float regen = 2;
    private Color color;
    private float health, maxHealth;
    private float angle;
    private boolean isAlive;
    private boolean canRender;
    private boolean canCollide;
    private int team;
    private float bodyDamage;
    private float density;
    private boolean canGoOutsideRoom;
    private GameEntity master;
    private final int id;
    private List<Gun> guns = new ArrayList<>();
    private boolean injured;

    public GameEntity(Color color, float x, float y, float size, float health, int team) {
        this.color = (color != null) ? color : GameColors.grey;
        this.x = x;
        this.y = y;
        this.size = size;
        this.health = health;
        this.maxHealth = health;
        this.isAlive = true;
        this.canRender = true;
        this.canCollide = true;
        this.team = team;
        this.master = this;
        this.id = entityId;
        this.bodyDamage = 0.1f;
        this.density = 1;
        this.canGoOutsideRoom = false;
        this.injured = false;

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

    public void collide(GameEntity other) {
        float selfAngle = (float)Math.atan2(this.y - other.y, this.x - other.x);
        this.vx += (float) (Math.cos(selfAngle) * this.density);
        this.vy += (float) (Math.sin(selfAngle) * this.density);

        float otherAngle = (float)Math.atan2(other.y - this.y, other.x - this.x);
        other.vx += Math.cos(otherAngle) * other.density;
        other.vy += Math.sin(otherAngle) * other.density;
    }

    public void step(float delta) {

        if (this.isAlive){
            if (this.id == playerId) {
                float[] mousePos = GameUtils.getWorldMouse();
                this.angle = (float) Math.atan2(mousePos[1] - this.y, mousePos[0] - this.x);
                GameConfig.CAMERA_X = this.x;
                GameConfig.CAMERA_Y = this.y;
                handleInput(delta);

            }
            this.vx += this.ax * delta;
            this.vy += this.ay * delta;

            this.vx *= 0.9f;
            this.vy *= 0.9f;

            this.x += this.vx * delta;
            this.y += this.vy * delta;

            for (GameEntity other : new ArrayList<>(GameGlobals.entities)) {
                if (this.id != other.id && other.isAlive) {
                    float dist = (float)Math.hypot(this.x - other.x, this.y - other.y);
                    if (dist <= this.size + other.size) {
                        if (this.canCollide && other.canCollide) {
                           this.collide(other);
                        }
                        if (this.team != other.team) {
                        this.changeHealth(-other.bodyDamage);
                        other.changeHealth(-this.bodyDamage);
                    }
                    }
                }
            }

            if (!this.canGoOutsideRoom){
                // arena boundaries
                if (this.x > GameConfig.ARENA_DIMENSIONS[0]) {
                    this.vx -= (this.x - GameConfig.ARENA_DIMENSIONS[0]) * GameConfig.ARENA_BOUND_FORCE * delta;
                }
                if (this.x < 0) {
                    this.vx -= this.x * GameConfig.ARENA_BOUND_FORCE * delta;
                }
                if (this.y > GameConfig.ARENA_DIMENSIONS[1]) {
                    this.vy -= (this.y - GameConfig.ARENA_DIMENSIONS[1]) * GameConfig.ARENA_BOUND_FORCE * delta;
                }
                if (this.y < 0) {
                    this.vy -= this.y * GameConfig.ARENA_BOUND_FORCE * delta;
                }
            }
            // regen
            this.changeHealth(this.regen * delta);

            // damage anim (flicker will be gameDrawer-sided)
            if (this.injured) {
                this.injured = false;
            }
        } else
        {
            // death animation will be client-sided (gameDrawer) do not add it here
            GameGlobals.entities.remove(this);
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
    }

    public void changeHealth(float hlt) {
        this.health = Math.min(this.health + hlt , this.maxHealth);
        if (this.health <= 0) this.kill();
        if (hlt < 0) {
            this.injured = true;
        }
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

    public boolean canRender() {
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

    public boolean isInjured() {
        return injured;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public GameEntity getMaster() {
        return master;
    }

    public void setMaster(GameEntity master) {
        this.master = master;
    }
}
