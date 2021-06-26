package com.boxes.EntityFactory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boxes.Constants;

public class SwiperEntity extends Actor {
    private Texture texture;

    private World world;

    private Body body;

    private Fixture fixture;

    public SwiperEntity(World world, Texture texture, float x, float y) {
        this.world = world;
        this.texture = texture;

        //Create body
        BodyDef def = new BodyDef();
        def.position.set(x, y);
        body = world.createBody(def);

        PolygonShape swiperHitboxShape = new PolygonShape();
        swiperHitboxShape.setAsBox(0.30f, 0.30f);
        fixture = body.createFixture(swiperHitboxShape, 1);
        fixture.setUserData("swiper");
        swiperHitboxShape.dispose();

        setPosition((x) * Constants.PIXELS_IN_METER, y * Constants.PIXELS_IN_METER);
        setSize(Constants.PIXELS_IN_METER, Constants.PIXELS_IN_METER);


    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
