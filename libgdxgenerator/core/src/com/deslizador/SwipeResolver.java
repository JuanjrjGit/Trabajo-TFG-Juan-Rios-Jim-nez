package com.deslizador;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
//Una interfaz es como una clase abstracta pero a lo bestia.
//Es muy util para referirse a un metodo aqui y unir diferentes cosas.
//En este caso control de posiciones entre 2 arrays
public interface SwipeResolver {
    /**
     * Simplifies and smoothes the input.
     *
     * @param input the input of the swipe event
     * @param output the output instance
     */
    void resolve(Array<Vector2> input, Array<Vector2> output);
}
