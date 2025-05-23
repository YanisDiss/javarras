package com.yanisdiss.javarras;

import com.badlogic.gdx.graphics.Color;

public class Gun {
    private float length, width, aspect,x,y,angle;
    private Color color;
    private GameEntity master;

    public Gun(GameEntity master, Color color, float length, float width, float aspect, float x, float y, float angle)
    {
        this.master = master;
        this.color = (color != null) ? color : GameColors.grey;
        this.length = length;
        this.width = width;
        this.aspect = aspect;
        this.x = x;
        this.y = y;
        this.angle = (float)Math.toRadians(angle);
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getAspect() {
        return aspect;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
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

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
