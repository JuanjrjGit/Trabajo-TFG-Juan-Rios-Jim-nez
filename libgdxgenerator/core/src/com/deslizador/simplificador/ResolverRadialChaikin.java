package com.deslizador.simplificador;

import com.deslizador.SwipeResolver;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



//Aqui tiene en cuenta las iteraciones , el numero de puntos y se comporta acorde a las condiciones
 public class ResolverRadialChaikin implements SwipeResolver {

    private Array<Vector2> tmp = new Array<Vector2>(Vector2.class);

    public static int iterations = 2;
    public static float simplifyTolerance = 35f;

    public void resolve(Array<Vector2> input, Array<Vector2> output) {
        output.clear();
        if (input.size<=2) { //simple copy
            output.addAll(input);
            return;
        }

        //Aquí simplifica la línea basandose en la tolerancia que tenga
        if (simplifyTolerance>0 && input.size>3) {
            simplify(input, simplifyTolerance * simplifyTolerance, tmp);
            input = tmp;
        }

        //Realiza suavizado, esto es para que la línea se vea más curva y no haga movimientos raros
        if (iterations<=0) { //Copia el input a output
            output.addAll(input);
        } else if (iterations==1) { //1 iteration para suavizarla al output
            smooth(input, output);
        } else { //Aqui lo hace con multiples iteraciones hace un efecto "ping pong" entre arrays
            int iters = iterations;
            //Siguientes iteraciones.
            do {
                smooth(input, output);
                tmp.clear();
                tmp.addAll(output);
                Array<Vector2> old = output;
                input = tmp;
                output = old;
            } while (--iters > 0);
        }
    }

    public static void smooth(Array<Vector2> input, Array<Vector2> output) {
        //Tamaño esperado
        output.clear();
        output.ensureCapacity(input.size*2);

        //El primer elemento
        output.add(input.get(0));
        //Media de elementos
        for (int i=0; i<input.size-1; i++) {
            Vector2 p0 = input.get(i);
            Vector2 p1 = input.get(i+1);

            Vector2 Q = new Vector2(0.75f * p0.x + 0.25f * p1.x, 0.75f * p0.y + 0.25f * p1.y);
            Vector2 R = new Vector2(0.25f * p0.x + 0.75f * p1.x, 0.25f * p0.y + 0.75f * p1.y);
            output.add(Q);
            output.add(R);
        }

        //Ultimo elemento
        output.add(input.get(input.size-1));
    }

    //Simplificacion por distancia
    //Esta parte viene adaptada de javascript.
    public static void simplify(Array<Vector2> points, float sqTolerance, Array<Vector2> out) {
        int len = points.size;

        Vector2 point = new Vector2();
        Vector2 prevPoint = points.get(0);

        out.clear();
        out.add(prevPoint);

        for (int i = 1; i < len; i++) {
            point = points.get(i);
            if (distSq(point, prevPoint) > sqTolerance) {
                out.add(point);
                prevPoint = point;
            }
        }
        if (!prevPoint.equals(point)) {
            out.add(point);
        }
    }

    public static float distSq(Vector2 p1, Vector2 p2) {
        float dx = p1.x - p2.x, dy = p1.y - p2.y;
        return dx * dx + dy * dy;
    }

}

