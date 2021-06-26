package com.boxes;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

//Debug
public class swipeHitboxHandler implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;

    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
