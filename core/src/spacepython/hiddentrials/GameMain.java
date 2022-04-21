package spacepython.hiddentrials;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Game;

import spacepython.hiddentrials.input.InputManager;
import spacepython.hiddentrials.physics.Physics;
import spacepython.hiddentrials.render.Renderer;
import spacepython.hiddentrials.render.Text;
import spacepython.hiddentrials.render.TitleScreen;
import spacepython.hiddentrials.world.*;

public class GameMain extends Game {
	private static GameMain instance;
	public TitleScreen titleScreen;
	public InputManager inputManager;
	public boolean inGame, rPressed = false;
	public Renderer renderer;
	public Physics physics;
	public Player player;
	public Text debugText;

	public void create () {
		GameMain.instance = this;
		this.renderer = new Renderer();
		this.physics = new Physics();
		this.inputManager = new InputManager();
		this.titleScreen = new TitleScreen();
		this.player = new Player(new Vector2(0, 0), new Vector2(0, 0));
		// this.debugText = new Text(new Vector2(5, this.renderer.height-5), "");
		this.setupUpdateThread();
	}

	public void update() {
		this.renderer.update();
		// this.debugText.text = "FPS: " + Gdx.graphics.getFramesPerSecond() + "\nPlayer position: " + Math.round(this.player.pos.x) + " " + Math.round(this.player.pos.y);
		// this.debugText.pos = this.renderer.getScreenCoords(5, this.renderer.height-5);
		boolean newPress = Gdx.input.isKeyPressed(Input.Keys.R);
		if (!this.rPressed && newPress) {
			if (this.getScreen() == null) {
				this.setScreen(titleScreen);
			} else {
				this.setScreen(null);
			}
		}
		this.rPressed = newPress;
	}

	public void render () {
		super.render();
		this.renderer.renderFrame();
	}

	public void resize(int width, int height) {
		super.resize(width, height);
		this.renderer.onResize();
	}
	
	public void dispose () {
		super.dispose();
		this.renderer.dispose();
		this.physics.dispose();
		Gdx.app.exit();
	}

	public static GameMain getInstance() {
		return GameMain.instance;
	}

	public void setupUpdateThread() {
		Thread updateThread = new Thread("Update Thread") {
			public void run() {
				physics.start();
			}
		};
		updateThread.start();
	}
}
