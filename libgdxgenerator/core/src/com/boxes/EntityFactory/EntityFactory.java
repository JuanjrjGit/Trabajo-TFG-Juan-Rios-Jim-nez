package com.boxes.EntityFactory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class EntityFactory {
    private AssetManager manager;


    public EntityFactory(AssetManager manager) { this.manager = manager;}


    public LineEntity createLine(World world, float x, float y, float angle){
        Texture lineTexture = manager.get("empty.png");        //Le ponemos la imagen
        return new LineEntity(world, lineTexture, x, y, angle); //Y la creamos basándonos en LineEntity.
                                                                //Y los parametros que le hemos dado.
    }

    public SwiperEntity createSwiper(World world, float x, float y) {   //Está entity al final no se ha usado.
        //La dejo para tema debuggeo
        Texture swiperTexture = manager.get("badlogic.jpg");
        return new SwiperEntity(world, swiperTexture ,x, y);
    }
}
