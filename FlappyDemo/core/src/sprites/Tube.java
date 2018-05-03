package sprites;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tube{
	public static final int TUBE_WIDTH = 52;
	
	private static final int FLUCTUATION = 180;
	private static final int TUBE_GAP = 75;
	private static final int LOWEST_OPENING = 120;
	private Texture topTube, bottomTube, Coin;
	private Vector2 posTopTube, posBotTube, posCoin;
	private Rectangle boundsTop, boundsBot, boundsCoin;
	private Random rand;
	
	
	public Tube(float x){
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		Coin = new Texture("coin.png");
		rand = new Random();
		
		posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
		posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
		posCoin = new Vector2(x + TUBE_WIDTH / 2 - getCoin().getWidth() / 2, posTopTube.y - TUBE_GAP / 2 - getCoin().getHeight() / 1.5f);
		
		boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
		boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
		boundsCoin = new Rectangle(posCoin.x, posCoin.y, Coin.getWidth(), Coin.getHeight());
		
	}

	public Texture getBottomTube(){
		return bottomTube;
	}
	
	public Texture getTopTube(){
		return topTube;
	}
	
	public Vector2 getPosTopTube(){
		return posTopTube;
	}
	
	public Vector2 getPosBotTube(){
		return posBotTube;
	}
	
	public Texture getCoin(){
		return Coin;
	}
	
	public Vector2 getPosCoin(){
		return posCoin;
	}
	
	public void reposition(float x){
		posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
		posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
		posCoin.set(x + TUBE_WIDTH / 2 - getCoin().getWidth() / 2, posTopTube.y - TUBE_GAP / 2 - getCoin().getHeight() / 1.5f);
		boundsTop.setPosition(posTopTube.x, posTopTube.y);
		boundsBot.setPosition(posBotTube.x, posBotTube.y);
		boundsCoin.setPosition(posCoin.x, posCoin.y);
	}
	
	public void moveCoin(float x){
		posCoin.set(x + TUBE_WIDTH / 2 - getCoin().getWidth() / 2, getPosTopTube().y - TUBE_GAP / 2 - getCoin().getHeight() / 1.5f);
		boundsCoin.setPosition(posCoin.x, posCoin.y);
	}
	
	public boolean collides(Rectangle player){
		return player.overlaps(boundsTop) || player.overlaps(boundsBot);
	}
	
	public boolean collects(Rectangle player){
		return player.overlaps(boundsCoin);
	}
	
	public void dispose(){
		topTube.dispose();
		bottomTube.dispose();
		Coin.dispose();
	}
}
