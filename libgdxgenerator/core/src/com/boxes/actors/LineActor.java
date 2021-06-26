package com.boxes.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LineActor extends Actor {

    private Texture line;
    private float angle;

    public LineActor(Texture line, float x, float y, float angle) {
        this.line = line;
        this.angle = angle;

        setPosition(x,y);
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(line,getX(), getY(), getWidth(), getHeight());
    }

}
