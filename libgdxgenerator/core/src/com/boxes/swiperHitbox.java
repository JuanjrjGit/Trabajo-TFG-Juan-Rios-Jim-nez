package com.boxes;


import com.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.BaseScreen;

public class swiperHitbox extends BaseScreen {

private World world;

private Box2DDebugRenderer renderer;

private OrthographicCamera camera;

private Body bodyBox;

private Fixture boxFixture;
private BodyDef def;
private swipeHitboxHandler hitbox;

    public swiperHitbox(MainGame game) {
            super(game);
            world=new World(new Vector2(0,-10),true);
            renderer=new Box2DDebugRenderer();
            camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

            hitbox = new swipeHitboxHandler();
            BodyDef boxDef=createBoxDef();
            bodyBox = world.createBody(boxDef);

            PolygonShape boxShape = new PolygonShape();
            boxShape.setAsBox(10, 10);
            boxFixture = bodyBox.createFixture(boxShape, 1);
            boxShape.dispose();
    }



    private BodyDef createBoxDef(){
        def = new BodyDef();
        def.position.set(0, 10);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    private BodyDef changePosition(int x, int y){
            def.position.set(x,y);
            return null;
    }
    public void dispose() {
        world.destroyBody(bodyBox);
        world.dispose();
        renderer.dispose();
    }
    public void render(float delta) {
        System.out.print("Renderiza caja");
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(delta, 6, 2);

        camera.update();
        renderer.render(world, camera.combined);
    }
}
