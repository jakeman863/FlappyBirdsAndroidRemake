package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jakemanning.game.FlappyDemo;

import sprites.ButtonManager;

public class MenuState extends State {

	private Texture background;
	private Texture playBtn;
	private Texture bgLogo;
	BitmapFont ThatFont;
	String highScore = FlappyDemo.getHighScore() + "";

	public MenuState(GameStateManager gsm) {
		super(gsm);
		cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
		background = new Texture ("bg.png");
		playBtn = new Texture ("playBtn.png");
		bgLogo = new Texture ("bg-name.png");
		ThatFont = new BitmapFont();

	}

	@Override
	public void handleInput() {
		if(Gdx.input.justTouched()){
			gsm.set(new PlayState(gsm));
		}

	}

	@Override
	public void update(float dt) {
		handleInput();
		
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, 0, 480, 800);
		sb.draw(bgLogo, cam.position.x - bgLogo.getWidth() / 2, 0);
		sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);
		ThatFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		ThatFont.draw(sb, "High Score: " + highScore, cam.position.x - 45, cam.position.y + 40);
		sb.end();
	}

	@Override
	public void dispose(){
		background.dispose();
		playBtn.dispose();
        bgLogo.dispose();
		System.out.println("Menu State Disposed.");
	}
}
