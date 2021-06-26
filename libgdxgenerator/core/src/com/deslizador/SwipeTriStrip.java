package com.deslizador;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
//Esta es la parte de codigo que mas me cuesta comprender
public class SwipeTriStrip {

    Array<Vector2> texcoord = new Array<Vector2>();
    Array<Vector2> tristrip = new Array<Vector2>();
    int batchSize;
    Vector2 perp = new Vector2();
    public float thickness = 30f;
    public float endcap = 8.5f;
    public Color color = new Color(Color.WHITE);
    ImmediateModeRenderer20 gl20;

    public SwipeTriStrip() {
        gl20 = new ImmediateModeRenderer20(false, true, 1);
    }
    //Aquí se pinta el swiper
    public void draw(Camera cam) {
        if (tristrip.size<=0)
            return;

        gl20.begin(cam.combined, GL20.GL_TRIANGLE_STRIP);   //Comienza
        for (int i=0; i<tristrip.size; i++) {   //Recorre el Array
            if (i==batchSize) {
                gl20.end();
                gl20.begin(cam.combined, GL20.GL_TRIANGLE_STRIP);
            }
            Vector2 point = tristrip.get(i);
            Vector2 tc = texcoord.get(i);
            gl20.color(color.r, color.g, color.b, color.a);
            gl20.texCoord(tc.x, 0f);
            gl20.vertex(point.x, point.y, 0f);
        }
        gl20.end();
    }

    private int generate(Array<Vector2> input, int mult) {
        int c = tristrip.size;
        if (endcap<=0) {
            tristrip.add(input.get(0));
        } else {
            Vector2 p = input.get(0);
            Vector2 p2 = input.get(1);
            perp.set(p).sub(p2).scl(endcap);
            tristrip.add(new Vector2(p.x+perp.x, p.y+perp.y));
        }
        texcoord.add(new Vector2(0f, 0f));

        for (int i=1; i<input.size-1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i+1);

            //Coge la direccion
            perp.set(p).sub(p2).nor();

            //Saca la perpendicular de la dirección, para darle despues el grosor
            perp.set(-perp.y, perp.x);

            float thick = thickness * (1f-((i)/(float)(input.size)));

            //Le da como tal el grosor
            perp.scl(thick/2f);

            //Decide en que lado se utiliza
            perp.scl(mult);

            //Esto es para la parte perpendicular de la punta
            tristrip.add(new Vector2(p.x+perp.x, p.y+perp.y));
            //0 y 0 = a que sea transparente
            texcoord.add(new Vector2(0f, 0f));

            //Añade el punto central (el punto de mayor tamaño del swiper)
            tristrip.add(new Vector2(p.x, p.y));
            //1 y 0 = opaco
            texcoord.add(new Vector2(1f, 0f));
        }

        //Punto final.
        if (endcap<=0) {
            tristrip.add(input.get(input.size-1));
        } else {
            Vector2 p = input.get(input.size-2);
            Vector2 p2 = input.get(input.size-1);
            perp.set(p2).sub(p).scl(endcap);
            tristrip.add(new Vector2(p2.x+perp.x, p2.y+perp.y));
        }
        //El final es transparente
        texcoord.add(new Vector2(0f, 0f));
        return tristrip.size-c;
    }

    public void update(Array<Vector2> input) {
        tristrip.clear();
        texcoord.clear();

        if (input.size<2)
            return;
        batchSize = generate(input, 1);
        int b = generate(input, -1);
    }

}
