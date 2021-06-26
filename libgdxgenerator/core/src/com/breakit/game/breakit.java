package com.breakit.game;

import com.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.boxes.box2dfactories.BodyDefFactory;
import com.boxes.box2dfactories.FixtureFactory;
import com.boxes.swipeHitboxHandler;
import com.deslizador.simplificador.SwiperImproved;

public class breakit extends BaseScreen {
	//Esto era para el testing por separado de las cosas
	public breakit(com.MainGame game) {
		super(game);
	}

	private World world;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	private SwiperImproved swiper;
	private Body swiperHitbox, lineHitbox;
	private Fixture swiperFixture, lineFixture;
	private swipeHitboxHandler inputProcessor;
	private Body[] line = new Body[10];
	private Fixture[] lineF = new Fixture[10];
	private int countLine = 0;
	private final Vector2 mouseInWorld2D = new Vector2();
	private final Vector3 mouseInWorld3D = new Vector3();
	boolean createnewbody = false;
	boolean funciona = true;


	/** Debug renderer. It renders worlds to the screen to make it possible to see them. */


	/**
	 * Camera. We have to create a camera to tell the renderer how to draw the world.
	 */


	@Override
	public void show() {


		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new GameContactListener());
		System.out.println("Llega a hacer show");
		// Create a renderer and a camera to make it possible for us to see what is in the world.
		renderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(16, 9);
		camera.translate(0, 1);

		swiperHitbox = world.createBody(BodyDefFactory.createHitboxSwiper());
		lineHitbox = world.createBody(BodyDefFactory.createLine(0,0));
		//lineHitbox = world.createBody(BodyDefFactory.createLine(1,1));
		//lineHitbox = world.createBody(BodyDefFactory.createLine(0,0));
		//line[countLine] = world.createBody(BodyDefFactory.createLine(0, 0));

		swiperFixture = FixtureFactory.createSwiperHitbox(swiperHitbox);
		lineFixture = FixtureFactory.createLineHitbox(lineHitbox);
		//lineF[countLine] = FixtureFactory.createLineHitbox(line[0]);


		//Have a way to reference our hitbox in case we have to modify it ingame.
		swiperFixture.setUserData("hitboxSwipe");
		lineFixture.setUserData("hitboxLine");

		//lineF[countLine].setUserData("hitboxLine");

		inputProcessor = new swipeHitboxHandler();
		System.out.print("show funciona");
		Gdx.input.setInputProcessor(inputProcessor);

		//Todavia no tengo los listeners
		//world.setContactListener(new Box2DScreenContactListener());

	}

	/*public void create () {
		//box = new swiperHitbox(this);
		swiper = new SwiperImproved(this);
	}*/

	//


	public void dispose() {
		world.destroyBody(swiperHitbox);
		world.destroyBody(lineHitbox);
		//swiper.dispose();
		//box.dispose();
		System.out.println("dispose");
	}

	@Override
	public void render(float delta) {
		/*int x = Gdx.input.getX();
		int y = Gdx.input.getY();*/
		mouseInWorld3D.x = Gdx.input.getX();
		mouseInWorld3D.y = Gdx.input.getY();
		mouseInWorld3D.z = 0;
		camera.unproject(mouseInWorld3D);
		mouseInWorld2D.x = mouseInWorld3D.x;
		mouseInWorld2D.y = mouseInWorld3D.y;
		if (Gdx.input.isTouched()) {
			//Hay que quitar la proyeccion de la camara para sacar correctamente las coordenadas y no basarse en las de la camara.
			Vector3 tmp = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			swiperHitbox.setTransform(mouseInWorld2D.x, mouseInWorld2D.y, 0);
			//lineHitbox.setTransform(tmp.x,tmp.y,1);
		}else{
			swiperHitbox.setTransform(0,-5,0);
		}



		/*if(inputProcessor.touchDown(x,y,0,0)==true){
			swiperHitbox.setTransform(x,y,1);
			System.out.println("pisa");
		}else{

		}
		if(inputProcessor.touchDragged(x,y,0)==true){
			swiperHitbox.setTransform(x,y,1);
		}else{

		}
		if(inputProcessor.touchUp(x,y,0,0)==true){
			swiperHitbox.setTransform(x,y,1);
		}else{

		}*/

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(delta, 6, 2);
		if (createnewbody){
			//

			lineHitbox = world.createBody(BodyDefFactory.createLine(2,2));
				lineFixture = FixtureFactory.createLineHitbox(lineHitbox);

				//lineHitbox.setTransform(2,2,0);
				//lineHitbox.setActive(true);
				//createNewBody();
				createnewbody = false;
				//funciona = false;
			/*else{
				lineHitbox.setTransform(0,0,0);
				lineHitbox.setActive(true);
				createnewbody = false;
				funciona = true;
			}*/
		}
		camera.update();
		renderer.render(world, camera.combined);
	}
	/*public void createNewBody(){
		System.out.println("wtf");
		/*Random rand = new Random();
		int n = rand.nextInt(5);
		world.createBody(BodyDefFactory.createLine(0.1f,0.1f));
		createnewbody = false;
	}*/

	//This method is necessary, we need it to detect collisions within the game.
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
			if (areCollided(contact, "hitboxSwipe", "hitboxLine")) {

				System.out.println("que pasaa");
			/*//line[countLine].setUserData(null);
			line[countLine].setActive(false);
			System.out.println("Algo pasa?");
			world.destroyBody(line[countLine]);
			System.out.println("Algo pasa?");
			line[countLine].destroyFixture(lineF[countLine]);
			System.out.println("Algo pasa?");*/
				//world.destroyBody(line[countLine]);
				//countLine++;

				//line[countLine] = world.createBody(BodyDefFactory.createLine(countLine, countLine));

				//lineF[countLine] = FixtureFactory.createLineHitbox(line[countLine]);

				//lineF[countLine].setUserData("a");
				//createnewbody = false;

				lineHitbox.setActive(false);
				world.destroyBody(lineHitbox);
				lineHitbox.destroyFixture(lineFixture);
				//System.out.println("contacto");
				createnewbody = true;
				System.out.println("ColisionInicial");

			}
		}

		@Override
		public void endContact(Contact contact) {
			//world.destroyBody(lineHitbox);
			//lineHitbox.destroyFixture(lineFixture);
			//lineHitbox.setActive(false);
			//world.destroyBody(lineHitbox);
			//System.out.println("ColisionTerminada");
			//createnewbody = true;
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

