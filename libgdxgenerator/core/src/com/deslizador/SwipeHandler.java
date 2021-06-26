package com.deslizador;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.deslizador.simplificador.ResolverRadialChaikin;

//La  clase que tiene todos los listeners del swiper
public class SwipeHandler extends InputAdapter {

    private FixedList<Vector2> inputPoints;

    /** The pointer associated with this swipe event. */
    private int inputPointer = 0;

    /** The minimum distance between the first and second point in a drawn line. */
    public int initialDistance = 10;

    /** The minimum distance between two points in a drawn line (starting at the second point). */
    public int minDistance = 20;

    private Vector2 lastPoint = new Vector2();

    private boolean isDrawing = false;

    private SwipeResolver simplifier = new ResolverRadialChaikin();
    private Array<Vector2> simplified;

    public SwipeHandler(int maxInputPoints) {
        this.inputPoints = new FixedList<Vector2>(maxInputPoints, Vector2.class);
        simplified = new Array<Vector2>(true, maxInputPoints, Vector2.class);
        resolve(); //copy initial empty list
    }

    /**
     * Returns the fixed list of input points (not simplified).
     * @return the list of input points
     */
    public Array<Vector2> input() {
        return this.inputPoints;
    }

    /**
     * Returns the simplified list of points representing this swipe.
     * @return
     */
    public Array<Vector2> path() {
        return simplified;
    }

    /**
     * If the points are dirty, the line is simplified.
     */
    public void resolve() {
        System.out.print("funciona");
        simplifier.resolve(inputPoints, simplified);
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer!=inputPointer)
            return false;
        isDrawing = true;
        System.out.print("Touch down");
        //limpiar puntos
        inputPoints.clear();

        //Punto en el que empieza
        lastPoint = new Vector2(screenX, Gdx.graphics.getHeight()-screenY);
        inputPoints.insert(lastPoint);

        resolve();
        return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //Al soltar la línea se "simplifica" realmente no funcionaba.
        //Yo le añadi un touchdown una vez suelta, eso hace que desaparezca.
        //Ya que cuando pulsas y solo pulsar rapidamente, no vale draggeo.
        //Desaparece
        System.out.print("Dejado de pulsar");
        resolve();
        isDrawing = false;
        touchDown( 0, 0, 0, 0);
        return false;

    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if (pointer!=inputPointer)
            return false;
        isDrawing = true;
        //Te hace un vector.
        Vector2 v = new Vector2(screenX, Gdx.graphics.getHeight()-screenY);

        //Calcula la distancia entre los puntos
        float dx = v.x - lastPoint.x;
        float dy = v.y - lastPoint.y;
        float len = (float)Math.sqrt(dx*dx + dy*dy);

        //Comprobamos si estamos dentro de la distancia minima , es un parametro que le poneoms
        if (len < minDistance && (inputPoints.size>1 || len<initialDistance))
            return false;

        //Añadimos un punto nuevo
        inputPoints.insert(v);

        lastPoint = v;

        //Simplificamos nuestra nueva linea
        resolve();
        return true;
    }
}
