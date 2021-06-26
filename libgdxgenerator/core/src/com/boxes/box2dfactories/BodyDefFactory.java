package com.boxes.box2dfactories;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class BodyDefFactory {
    //Lo llamamos una fabrica porque es de donde se fabrican todos los cuerpos
    public static BodyDef createHitboxSwiper(){ //Creamos swiper
        BodyDef def = new BodyDef();
        def.position.set(10, 0);

        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    public static BodyDef createLine(float x, float y){ //Creamos linea

        BodyDef def = new BodyDef();
        def.position.set(x, y);

        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

}
