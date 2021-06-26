package com.boxes.box2dfactories;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Body;

public class FixtureFactory {
    //La FixtureFactory se encarga de instanciar el modelo abstracto de los cuerpos
    public static Fixture createSwiperHitbox(Body hitbox){
        PolygonShape swiperHitboxShape = new PolygonShape();        //Creamos nuestro modelo de poligono
        swiperHitboxShape.setAsBox(20.30f, 20.30f);         //Hacemos una caja
        Fixture fixture = hitbox.createFixture(swiperHitboxShape, 3);
        //La densidad es para saber
        // como se tiene que comportar al chocar con otros objetos recomiendan poner densidad aunque
        // no se utilice el choque más que para evaluar colisiones

        swiperHitboxShape.dispose();    //Dispose es para deshacer el polygonshape , ya que es solo para crear la caja
        //el modelo ya esta hecho con la fixtura asi que no nos hace falta.
        return fixture;

    }

    public static Fixture createLineHitbox(Body line){
        PolygonShape lineHitboxShape = new PolygonShape();
        lineHitboxShape.setAsBox(0.15f, 0.60f); //Hace rectangulos
        Fixture fixture = line.createFixture(lineHitboxShape, 3);
        lineHitboxShape.dispose();
        return fixture;
    }

}
