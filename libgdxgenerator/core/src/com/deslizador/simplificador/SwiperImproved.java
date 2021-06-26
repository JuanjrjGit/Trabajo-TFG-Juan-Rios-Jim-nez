package com.deslizador.simplificador;



import com.BaseScreen;
import com.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.deslizador.SwipeHandler;
import com.deslizador.SwipeTriStrip;

public class SwiperImproved extends BaseScreen {

    OrthographicCamera cam;
    SpriteBatch batch;

    SwipeHandler swipe;

    Texture tex;
    ShapeRenderer shapes;

    SwipeTriStrip tris;

    public SwiperImproved(MainGame game) {
        super(game);
        System.out.println("Funciona swiper");
        //Es el renderizador del swiper
        tris = new SwipeTriStrip();

        //maximo de puntos para mantener el swiper
        swipe = new SwipeHandler(10);

        //Minima distancia entre puntos
        swipe.minDistance = 10;

        //minima distancia entre el primer y segundo punto
        swipe.initialDistance = 10;

        //Usamos texturas , esta textura la pongo yo con el assetmanager
        tex = game.getManager().get("gradient.png");
        tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        shapes = new ShapeRenderer();
        batch = new SpriteBatch();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Manejar el input del swiper
        Gdx.input.setInputProcessor(swipe);
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }



    @Override
    public void render(float delta) {
        System.out.println("Renderiza el desliz"); //
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        tex.bind();



        //Lo gorda que será la linea
        tris.thickness = 30f;

        //Generar la línea por nuestra trayectoria (del cursor/dedo)
        tris.update(swipe.path());

        //Color de nuestro cursor
        tris.color = Color.VIOLET;
        System.out.println("Pinta color");
        //Renderizar los triangulos
        tris.draw(cam);

        //Modo debug
        //drawDebug();
    }

    //Más debug que no he utilizado
    void drawDebug() {
        Array<Vector2> input = swipe.input();

        //draw the raw input
        shapes.begin(ShapeType.Line);
        shapes.setColor(Color.GRAY);
        for (int i=0; i<input.size-1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i+1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();

        //draw the smoothed and simplified path
        shapes.begin(ShapeType.Line);
        shapes.setColor(Color.RED);
        Array<Vector2> out = swipe.path();
        for (int i=0; i<out.size-1; i++) {
            Vector2 p = out.get(i);
            Vector2 p2 = out.get(i+1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();


        //render our perpendiculars
        shapes.begin(ShapeType.Line);
        Vector2 perp = new Vector2();

        for (int i=1; i<input.size-1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i+1);

            shapes.setColor(Color.LIGHT_GRAY);
            perp.set(p).sub(p2).nor();
            perp.set(perp.y, -perp.x);
            perp.scl(10f);
            shapes.line(p.x, p.y, p.x+perp.x, p.y+perp.y);
            perp.scl(-1f);
            shapes.setColor(Color.BLUE);
            shapes.line(p.x, p.y, p.x+perp.x, p.y+perp.y);
        }
        shapes.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        tex.dispose();
    }

}