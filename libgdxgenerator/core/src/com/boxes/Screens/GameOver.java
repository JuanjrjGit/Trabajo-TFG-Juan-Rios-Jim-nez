package com.boxes.Screens;

import com.BaseScreen;
import com.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameOver extends BaseScreen {
    private Stage stage;

    private Skin skin,skin2;

    private Image background;

    private TextureAtlas textures;

    private TextButton boton1,boton2;

    private Label credits;

    public GameOver(final MainGame game) {
        super(game);
        stage = new Stage(new FitViewport(720,360));

        textures = new TextureAtlas("textures.pack");

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        skin2 = new Skin(game.getManager().get("textures.pack",TextureAtlas.class));

        credits = new Label("¡¡Has perdido todas las vidas!! \n"+
                "¿Quieres volver al menu o seguir jugando?", skin);


        background = new Image(game.getManager().get("fondomenu.png", Texture.class));

        boton1 = new TextButton("Volver a jugar", skin);
        boton2 = new TextButton("Volver al menu", skin);

        boton1.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Take me to the game screen!
                System.out.println("Me lleva");
                game.setScreen(game.gameScreen2);
            }
        });

        boton2.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.MenuScreen);
                System.out.println("Creditos");
            }
        });

        background.setSize(720,380);
        boton1.setSize(150,40);
        boton2.setSize(150,40);
        background.setPosition(0,0);
        boton1.setPosition(270,80);
        boton2.setPosition(270,40);
        credits.setPosition(270, 120);

        stage.addActor(background);
        stage.addActor(boton1);
        stage.addActor(boton2);
        stage.addActor(credits);

    }

    @Override
    public void show(){

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose(){
        stage.dispose();
        skin.dispose();
        skin2.dispose();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();



    }


}
