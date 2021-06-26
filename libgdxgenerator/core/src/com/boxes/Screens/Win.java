package com.boxes.Screens;

import com.BaseScreen;
import com.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Win extends BaseScreen {
    private Stage stage;

    private Skin skin;

    private Image background;

    private TextButton boton1;
    private Label credits;

    public Win(final MainGame game) {
        super(game);
        stage = new Stage(new FitViewport(720,360));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        credits = new Label("¡¡Has ganado!! \n", skin);

        background = new Image(game.getManager().get("fondomenu.png", Texture.class));

        boton1 = new TextButton("¿Volver al menu?", skin);

        boton1.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.MenuScreen);
            }
        });

        background.setSize(720,380);
        boton1.setSize(150,40);
        background.setPosition(0,0);
        boton1.setPosition(270,80);
        credits.setPosition(270, 120);

        stage.addActor(background);
        stage.addActor(boton1);
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

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }
}
