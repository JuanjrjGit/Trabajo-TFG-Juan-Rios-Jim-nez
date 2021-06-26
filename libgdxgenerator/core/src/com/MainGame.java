package com;


import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.boxes.Screens.Credits;
import com.boxes.Screens.GameOver;
import com.boxes.Screens.GameScreen2;
import com.boxes.Screens.LoadingScreen;
import com.boxes.Screens.MenuScreen;
import com.boxes.Screens.Win;


public class MainGame extends Game {
    //Esta es la clase que prepara el juego
    //Con esto centralizamos la mayoría de los elementos que tenemos que cargar en el juego
    // Sounds, music, and images.
    private AssetManager manager;

    public BaseScreen gameScreen2, loadingScreen, MenuScreen, Gameover,win, Credits;

    public void create() {
        System.out.println("Cargando");

        //atlas = new TextureAtlas("textures.pack");  ---> Aquí estaba haciendo pruebas con un Atlas , pero
        // Realmente aquí no se utiliza., pero si lo cargamos para otras cosas.

        manager = new AssetManager();   //El AssetManager es el que carga imagenes y sonidos.
        //Para juegos mas grandes no es recomendable cargar uno por uno los elementos del juego.
        //Se recomienda uso de uno o varios Atlas (un unico archivo con varias imagenes), ya que
        //Reduce el codigo que hay que escribir y solo hay que cargar una imagen en vez de multiples.

        manager.load("fondomenu.png", Texture.class);           //Fondo pantalla menu
        manager.load("gradient.png", Texture.class);            //Parte grafica del cursor
        manager.load("textures.pack", TextureAtlas.class);      //Atlas
        manager.load("audio/menu.mp3", Music.class);            //Musica menu
        manager.load("audio/electroman.mp3", Music.class);      //Musica nivel 1
        manager.load("audio/breakLine.mp3", Sound.class);       //Sonido romper lineas
        manager.load("audio/losehp.mp3", Sound.class);          //Sonido perder vida
        manager.load("data/line.png", Texture.class);           //Textura de las lineas que no se consigue implementar bien
        manager.load("empty.png", Texture.class);               //Textura de debuggeo es transparente.


        loadingScreen = new LoadingScreen(this); //Creamos la pantalla de carga
        /*
        * Hay que tener en cuenta un factor bastante interesante a la hora de cargar las cosas
        * Libgdx carga los elementos de forma asincrona y cuando realmente instanciamos lo que es la textura
        * para ponerla en lo que es la pantalla en sí, supone que si el assetmanager no ha terminado de cargar
        * la textura y la instanciamos da un error de memoria, porque no la encuentra y el juego
        * directamente no va a iniciar. Así que le ponemos una pantalla de carga que se quite cuando haya cargado
        * , que lo bueno es que queda profesional.
        * Y evita que se crashee el juego nada mas arrancarlo.
        */

        setScreen(loadingScreen);

    }
    public void finishLoading(){                        //Este método es llamado cuando loadingScreen termina
        gameScreen2 = new GameScreen2(this);            //Instanciamos pantalla juego
        MenuScreen = new MenuScreen(this);       //Instanciamos Menu
        Credits = new Credits(this);             //Instanciamos pantalla de creditos
        Gameover = new GameOver(this);                  //Instanciamos pantalla de perder.
        win = new Win(this);                     //Instanciamos pantalla de victoria
        setScreen(MenuScreen);                          //Ponemos el menu como la pantalla principal

    }
    public AssetManager getManager() {
        return manager;
    }
}
