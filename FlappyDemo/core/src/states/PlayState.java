package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jakemanning.game.FlappyDemo;

import sprites.Bird;
import sprites.Tube;

public class PlayState extends State{
	private static final int TUBE_SPACING = 125;
	private static final int TUBE_COUNT = 4;
	private static final int GROUND_Y_OFFSET = -40;
	
	private Bird bird;
	private Texture bg;
	private Texture ground;
	private Vector2 groundPos1, groundPos2;
	
	//static int WIDTH = 600;
	public static int score = 0;
	BitmapFont yourBitmapFontName;

    //String highScore = FlappyDemo.getHighScore() + "";

	private Array<Tube> tubes;

	protected PlayState(GameStateManager gsm) {
		super(gsm);
		bird = new Bird(35, 300);
		cam.setToOrtho(false, FlappyDemo.WIDTH /2, FlappyDemo.HEIGHT / 2);
		bg = new Texture("bg.png");
		ground = new Texture("ground.png");
		groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
		groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

	
		yourBitmapFontName = new BitmapFont();

		
		tubes = new Array<Tube>();

		
		for (int i = 1; i <= TUBE_COUNT; i++){
			tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
		}
	}

	@Override
	protected void handleInput() {
		if(Gdx.input.justTouched()){
			bird.jump();
		}
	}
	
	public static void addScore(){
		score += 1;
	}
	
	public int getScore(){
		return score;
	}
	
	@Override
	public void update(float dt) {
		handleInput();
		updateGround();
		bird.update(dt);
		cam.position.x = bird.getPosition().x + 80;

		
		for(int i = 0; i < tubes.size; i++){
			Tube tube = tubes.get(i);
			
			if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
				tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
			}
			
			
			if(tube.collides(bird.getBounds())){
				gsm.set(new MenuState(gsm));
				if (score > FlappyDemo.getHighScore()){
					FlappyDemo.setHighScore(score);
				}

				score = 0;
			}
			
			if(tube.collects(bird.getBounds())){
				addScore();
				tube.moveCoin(tube.getPosCoin().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
			}
		}
		
		if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
			gsm.set(new MenuState(gsm));
			if (score > FlappyDemo.getHighScore()){
				FlappyDemo.setHighScore(score);
			}
			score = 0;
		}
		cam.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
		sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
		for(Tube tube : tubes){
			sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
			sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
			sb.draw(tube.getCoin(), tube.getPosCoin().x, tube.getPosCoin().y);
		}
		
		sb.draw(ground, groundPos1.x, groundPos1.y);
		sb.draw(ground, groundPos2.x, groundPos2.y);
		sb.end();

        Matrix4 uiMatrix = cam.combined.cpy();
		uiMatrix.setToOrtho2D(0, 0, 480, 800);
		sb.setProjectionMatrix(uiMatrix);
		sb.begin();

		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		yourBitmapFontName.getData().setScale(2,2);
		yourBitmapFontName.draw(sb, "Score: " + getScore(), 190, 800);
		//Draws highscore under Score
		// yourBitmapFontName.draw(sb, "High Score: " + highScore, 160, 775);
		sb.end();
	}

	@Override
	public void dispose() {
		bg.dispose();
		bird.dispose();
		ground.dispose();
		yourBitmapFontName.dispose();
		for(Tube tube : tubes){
			tube.dispose();
		}
		System.out.println("Play State Disposed.");
	}

	private void updateGround(){
		
		if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()){
			groundPos1.add(ground.getWidth() * 2, 0);
		}
		
		if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()){
			groundPos2.add(ground.getWidth() * 2, 0);
		}
	}
}
