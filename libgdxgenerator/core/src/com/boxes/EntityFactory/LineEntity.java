package com.boxes.EntityFactory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boxes.Constants;



//La entidad es la mezcla de un objeto de Box2d y otro de Scene2D
//Es la construccion completa
public class LineEntity extends Actor{

    private Texture texture;

    private World world;

    private Body body;

    private Fixture fixture;

    public LineEntity(World world, Texture texture, float x, float y, float angle) {
        this.world = world;
        this.texture = texture;

        //Creamos body
        BodyDef def = new BodyDef();
        def.position.set(x, y);                         //Posicion
        def.type = BodyDef.BodyType.DynamicBody;        //Que tipo de cuerpo
        body = world.createBody(def);


        PolygonShape lineHitboxShape = new PolygonShape();      //Figura poligonal (temporal)
        lineHitboxShape.setAsBox(90.00f, 45.00f);       //Tamaño caja
        fixture = body.createFixture(lineHitboxShape, 3);
        fixture.setUserData("line");

        lineHitboxShape.dispose();


        setSize(Constants.PIXELS_IN_METER, Constants.PIXELS_IN_METER/2);
        System.out.print("Coordenadas Scene: X"+ x +"  Y: "+ y);
        body.setTransform(x,y , angle);
        setRotation(angle);


    }
    @Override
    public void draw(Batch batch, float parentAlpha) {  //Pintamos
        setPosition((body.getPosition().x) / Constants.PIXELS_IN_METER,
                (body.getPosition().y) / Constants.PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach(){       //Destruye todo.
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public void move(int x , int y, int angle){
        body.setTransform(x,y,angle);

    }
}
