package com.boxes.Screens;

import com.BaseScreen;
import com.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends BaseScreen {

    private Stage stage;

    private Skin skin,skin2;

    private Image background;

    private TextureAtlas textures;

    private TextButton credits;

    private ImageButton boton1,boton2;

    private Music menuMusic;

    public MenuScreen(final MainGame game) {
        super(game);
        stage = new Stage(new FitViewport(720,370));

        textures = new TextureAtlas("textures.pack");

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        skin2 = new Skin(game.getManager().get("textures.pack",TextureAtlas.class));

        credits = new TextButton("Creditos", skin);

        background = new Image(game.getManager().get("fondomenu.png", Texture.class));

        boton1 = new ImageButton(skin2.getDrawable("botonjugar"));
        boton2 = new ImageButton(skin2.getDrawable("botoncreditos"));
        menuMusic = game.getManager().get("audio/menu.mp3");
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.75f);
        menuMusic.play();

        //logo = new Image(game.getManager().get("logo.png", Texture.class));

        boton1.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Take me to the game screen!
                System.out.println("Me lleva");
                menuMusic.stop();
                game.setScreen(game.gameScreen2);
            }
        });

        boton2.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.Credits);
                System.out.println("Creditos");
            }
        });

        background.setSize(720,380);
        boton1.setSize(200,80);
        boton2.setSize(200,80);
        background.setPosition(0,0);
        boton1.setPosition(270,180);
        boton2.setPosition(270,80);

        stage.addActor(background);
        stage.addActor(boton1);
        stage.addActor(boton2);


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
