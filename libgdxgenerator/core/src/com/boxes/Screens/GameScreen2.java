package com.boxes.Screens;

import com.MainGame;
import com.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.boxes.EntityFactory.EntityFactory;
import com.boxes.EntityFactory.LineEntity;
import com.boxes.box2dfactories.BodyDefFactory;
import com.boxes.box2dfactories.FixtureFactory;
import com.deslizador.SwipeHandler;
import com.deslizador.SwipeTriStrip;
import com.badlogic.gdx.physics.box2d.Body;


import java.util.Random;

public class GameScreen2 extends BaseScreen {

    private Stage stage;

    private World world;

    private LineEntity line1;

    private Sound destroySound;
    private Music backgroundMusic;
    private Sound hploss;


    private Vector3 position;

    private Box2DDebugRenderer rendererBox2D;

    private OrthographicCamera cam;
    private SpriteBatch batch;

    private SwipeHandler swipe;

    private Texture tex;
    private ShapeRenderer shapes;

    private SwipeTriStrip tris;

    private Body swiperHitbox;
    private Fixture swiperFixture;

    private Boolean removeLine1 = false;


    private Boolean destroyed1 = false;

    private Image background;

    private Boolean broken = false;

    private Label hpUI;
    private Skin skin;

    private int hp;

    private final Vector2 mouseInWorld2D = new Vector2();
    private final Vector3 mouseInWorld3D = new Vector3();

    private Random r;

    long startTime;

    long step;


    public GameScreen2(MainGame game){

        super(game);

        //Creacion de la pantalla
        stage = new Stage(new FitViewport(2000,1000));
        position = new Vector3(stage.getCamera().position);
        world = new World(new Vector2(0,0), true);
        background = new Image(game.getManager().get("fondomenu.png", Texture.class));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        hpUI = new Label("",skin);


        //Sonidos
        destroySound = game.getManager().get("audio/breakLine.mp3");
        backgroundMusic = game.getManager().get("audio/electroman.mp3");
        hploss = game.getManager().get("audio/losehp.mp3");

        //Camaras
        rendererBox2D = new Box2DDebugRenderer();       //Debug
        cam = new OrthographicCamera(16,9);             //Camara

        //Miscelaneo
        r = new Random();

        hpUI.setFontScale(5f);

        stage.addActor(hpUI);

    }

    @Override
    public void show(){
        tris = new SwipeTriStrip();

        //El numero maximo de puntos que debe tener el swiper para mantenerse vivo
        swipe = new SwipeHandler(10);

        //distancia minima entre 2p untos
        swipe.minDistance = 10;

        //distancia minima entre el primer y segundo punto
        swipe.initialDistance = 10;

        //Vamos a usar esta textura
        tex = game.getManager().get("gradient.png", Texture.class);


        hp = 5;

        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        shapes = new ShapeRenderer();
        batch = new SpriteBatch();

        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //Manejar el input del swiper
        Gdx.input.setInputProcessor(swipe);


        EntityFactory factory = new EntityFactory(game.getManager());                   //Fabrica de entidades
        swiperHitbox = world.createBody(BodyDefFactory.createHitboxSwiper());
        swiperFixture = FixtureFactory.createSwiperHitbox(swiperHitbox);

        swiperFixture.setUserData("swiper");
        backgroundMusic.setVolume(0.75f);
        backgroundMusic.play();
        //No queremos mostrar el swiper cuando lo creamos por eso le ponemos coordenadas negativas.
        //Habia varios metodos para realizar creacion de líneas pero, muy complicados
        background.setSize(2000,1000);
        background.setPosition(0,0);
        line1 = factory.createLine(world, 300, 300, 0);

        startTime = TimeUtils.millis();                 //Control de tiempo

        step = 1000;                                    //Pasos por tiempo.

        Gdx.input.setInputProcessor(swipe);
        world.setContactListener(new GameContactListener());
        stage.addActor(background);
        stage.addActor(hpUI);
        stage.addActor(line1);

        line1.setPosition(-3000000,-3000000);

        hpUI.setPosition(0,900);


    }

    @Override
    public void hide(){

        //stage.clear();
        swiperHitbox.destroyFixture(swiperFixture);
        line1.detach();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        //cam.update();
        //Gdx.input.setInputProcessor(inputProcessor);
        batch.setProjectionMatrix(cam.combined);
        mouseInWorld3D.x = Gdx.input.getX();
        mouseInWorld3D.y = Gdx.input.getY();
        mouseInWorld3D.z = 0;
        cam.unproject(mouseInWorld3D);
        mouseInWorld2D.x = mouseInWorld3D.x;
        mouseInWorld2D.y = mouseInWorld3D.y;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        tex.bind();

        tris.thickness = 40f;

        tris.update(swipe.path());

        tris.color = Color.WHITE;



        if (Gdx.input.isTouched()) {
            //Hay que quitar la proyeccion de la camara para sacar correctamente las coordenadas y no basarse en las coordenadas relativas de la camara.
            //Vector3 tmp = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            swiperHitbox.setTransform(mouseInWorld2D.x, mouseInWorld2D.y, 0);
            //lineHitbox.setTransform(tmp.x,tmp.y,1);
            //System.out.println(""+swiperHitbox.getPosition()+"");
        }else{
            swiperHitbox.setTransform(-10,0,0);
        }

        //cam.update();
        //batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        //the endcap scale
		//tris.endcap = 5f;

        stage.act();


        System.out.print("^^^"+ (System.currentTimeMillis() - startTime)+ "^^^");
        System.out.println(startTime);
        world.step(delta, 6, 2);

        if(removeLine1){
            line1.move(-100,-100, 0);
            destroySound.play();
            broken = true;
            removeLine1=false;
            if(hp<7) {
                hp++;
            }
        }

        //Esta parte de abajo es el generador de lineas
        //Tenemos en cuenta el tiempo que ha pasado con System.currentTimeMills que se guarda en startTime
        //step es la variable que tiene en cuenta el numero de "pasos" que se han dado.
        //Por eso lo incrementamos en 1500 cada vez que se cumple la condicional , para que se cumpla en un tiempo más tarde
        //y si step es mayor de cierto numero (segun el if) no cumpla eso y deje de generar las lineas.

        //Originalmente las lineas se iban a generar siempre igual cada vez que iniciabas el nivel pero, en libgdx
        //suponía crear una estructura de datos, un archivo en el que almacenabas las lineas su generacion y caracteristicas.
        //Y escribirlas a codigo una por una. Y luego el juego leyera estos datos y lo interpretara
        //Esto es bastante dificil de realizar sin herramientas.
        //Libgdx tiene menos soporte en Android , tiene editores y herramientas que facilitan el uso de
        //estos problemas pero es para sobremesa.


        if((System.currentTimeMillis() - startTime) > step && step<30000 ){
            step = step + 1500;
            System.out.println("Generado parte1");
            line1.move(r.nextInt(1800-400), r.nextInt(800-200), r.nextInt(350-0));
            if(broken != true){
                hploss.play();
                hp--;
            }
            broken = false;
        }
        if((System.currentTimeMillis() - startTime) > step && step<61000 ){
            step = step + 750;
            System.out.println("Generado parte2");
            line1.move(r.nextInt(1800-400), r.nextInt(800-200), r.nextInt(350-0));
            if(broken != true){
                hploss.play();
                hp--;
            }
            broken = false;
        }
        if((System.currentTimeMillis() - startTime) > step && step<91000 ){
            step = step + 650;
            System.out.println("Generado parte3");
            line1.move(r.nextInt(2000-200), r.nextInt(800-200), r.nextInt(350-0));
            if(broken != true){
                hploss.play();
                hp--;
            }
            broken = false;
        }
        if((System.currentTimeMillis() - startTime) > step && step<97000 ){
            step = step + 650;
            System.out.println("Generado parte4 y fin");
            line1.move(-100,-100, 0);
        }
        if((System.currentTimeMillis() - startTime) > step && step>97000 ){
            game.setScreen(game.win);
        }

        if(hp <= 0){
            backgroundMusic.stop();
            game.setScreen(game.Gameover);
        }
        System.out.println("Vida:"+hp+"");
        hpUI.setText("Vida: "+hp+" ");
        hpUI.draw(batch, 1);
        stage.draw();
        hpUI.draw(batch, 1);
        tris.draw(cam);
        hpUI.draw(batch, 1);
        batch.end();
        rendererBox2D.render(world, cam.combined); //Modo debug, muestra las colisiones.

    }

    @Override
    public void dispose(){
        //We dispose the stage to remove the Batch references in the graphic card.
        stage.dispose();
        world.dispose();
    }

    private class GameContactListener implements ContactListener {
        //We need the userData that we created, to have a reference to the
        // Objects we created in the show method.
        // When we send parameters , this userData is an Object variable
        private boolean areCollided(Contact contact, Object userA, Object userB) {
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();
            if (userDataA == null || userDataB == null) {
                return false;
            }

            return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                    (userDataA.equals(userB) && userDataB.equals(userA));

        }

        //We check contact between 2 users. in this game we only aim for
        //The "swipe" hitbox ,in truth it is just a box that follows our touch
        //Or cursor, and the "line" that we're going to break as we play through the game.

        @Override
        public void beginContact(Contact contact) {
            if (areCollided(contact, "swiper", "line")) {
                //lineList.get(i);
                //destroyLines = true;
                removeLine1 = true;
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();

            }

        }
        @Override
        public void endContact(Contact contact) {

        }

        //We won't use this one
        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        //We won't use this one
        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }
}

