package com;

import com.badlogic.gdx.Screen;
import com.breakit.game.breakit;


public abstract class BaseScreen implements Screen {

    protected MainGame game;

    public BaseScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        //Metodo de las pantallas que se llama cuando se pone la pantalla en cuestión en primer plano
    }

    @Override
    public void render(float delta) {
        //Se llama a este método todo el rato

    }

    @Override
    public void resize(int width, int height) {
        //Cambia el tamaño de la pantalla
    }

    @Override
    public void pause() {
        //Cuando se pausa el juego se llama a este metodo
    }

    @Override
    public void resume() {
        //Cuando se reanuda el juego se llama a este metodo
    }

    @Override
    public void hide() {
        //Cuando deja de mostrarse la pantalla se llama a este metodo
    }

    @Override
    public void dispose() {
        //Cuando se destruye la pantalla se llama a este metodo
    }
}
