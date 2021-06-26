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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Credits extends BaseScreen {
    private Stage stage;

    private Image background;

    private Skin skin;

    private Label credits;

    public Credits(final MainGame game) {
        super(game);
        stage = new Stage(new FitViewport(720,370));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        credits = new Label("Creado por Juan Rios Jimenez\n" +
                "Musica: Electroman Adventures Remix de Music Legends\n" +
                "Arte: Por Juan Ríos Jiménez\n"+
                "Toca la pantalla para volver", skin);

        background = new Image(game.getManager().get("fondomenu.png", Texture.class));

        background.setSize(720,380);
        background.setPosition(0,0);
        credits.setPosition(50, 0);

        stage.addActor(background);
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
        if (Gdx.input.isTouched()) {
            game.setScreen(game.MenuScreen);
        }
        stage.act();
        stage.draw();

    }

}
