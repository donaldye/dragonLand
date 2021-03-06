package game.miniGameTeam;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import guiPractice.components.ClickableGraphic;
import guiPractice.components.Graphic;
import guiPractice.components.TextLabel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;


import game.DragonLand;
import game.mainScreenTeam.Dragon;
import guiPractice.ClickableScreen;
import guiPractice.Screen;
import guiPractice.components.Action;
import guiPractice.components.Button;
import guiPractice.components.Visible;
//import miniGames.GameDragon;
//import miniGames.GameVioletta;

/**
 * @author Tamanna Hussain
 *
 */
public class GameScreen extends ClickableScreen implements KeyListener {

	private Button exit;
	private static Button scoreDisplay;
	private Button highScoreButton;
	private Graphic background;
	private int time;
	private static ArrayList<Star1> starArray;
	private static int score;
	public static GameScreen tGame;
	public static boolean isNotHome;
	public GameScreen(int width, int height) {
		super(width, height);
		tGame = this;
	}
	private int powerUp;

	@Override
	public void initAllObjects(ArrayList<Visible> view) {
		//initial score is 0 and it should count the number of stars caught
		score = 0;
		time = 2500;
		starArray = new ArrayList<Star1>();
		powerUp = 0; 

		background = new Graphic(0,0,getWidth(),getHeight(),"img/forest.jpg");
		viewObjects.add(background);

		exit = new Button(30, 50, 40, 40, "X", DragonLand.DARKER_NUDE, new Action() {
			@Override
			public void act() {
				HighScoreScreen.setRoundScore(score);
				HighScoreScreen.updateOnEnter();
				DragonLand.game.setScreen(DragonLand.highscoreScreen);
				stopGame();
			}
		});

		scoreDisplay = new Button(getWidth()-150, 50, 120, 50, "Score: " + score, DragonLand.DARKER_NUDE, null);

		view.add(exit);
		view.add(scoreDisplay);

		GameVioletta vGameObject = new GameVioletta();
	}

	protected void stopGame() {
		GameVioletta.vGame.setPlaying(false);
		ArrayList<Dragon> dragonArray = GameVioletta.vGame.getDragonArray();
		if(dragonArray.size() != 0){
			for(Dragon d: dragonArray){
				remove(d);
			}
			GameVioletta.vGame.eraseDragons();
			System.out.println("A dragon is being erased - end game");
		}

		if(starArray.size() != 0){
			for(Star1 s: starArray){
				remove(s);
			}
		}
		starArray.clear();
	}

	public void startGame(){
		score = 0;
		time = 2500;
		setScoreDisplay();
		//starArray.clear();
		addObject(GameVioletta.vGame.addDragon("img/dragon1.png"));
		Thread start = new Thread(new Runnable() {
			@Override
			public void run() {
				fallingStars();
			}
		});
		start.start();
	}

	public static ArrayList<Star1> getStarArray(){
		return starArray;
	}

	public void addStar(){
		//adds one star object to the screen and the array
		int yPos = 0;
		int starH = 65;
		int starW = 65;
		Star1 starImage = new Star1(randomX(), yPos, starW, starH, this);
		starImage.play();
		starArray.add(starImage);
		addObject(starImage);
	}

	public void removeStar(Star1 star){
		starArray.remove(star);
		remove(star);
	}

	public void fallingStars(){
		GameVioletta.vGame.setPlaying(true);
		while(GameVioletta.vGame.getPlaying()){
			try{
				setTime();
				Thread.sleep(time);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			addStar();
		}
		HighScoreScreen.updateOnEnter();
		DragonLand.game.setScreen(DragonLand.highscoreScreen);
	}

	public int randomX(){
		//80 through getWidth()-175
		int max = getWidth()-175;
		int min = 80;
		int xPos = (int) (Math.random()*(max - min) + min);
		return xPos;
	}
	
	public void setTime(){
		//picked these numbers for demonstration purposes
		if (score >= 5 && score < 10){
			time = 2000;
		}
		if (score >= 10 && score < 15){
			time = 1500;
		}
		if (score >= 15 && score < 20){
			time = 1000;
		}
		if (score >= 20){
			time = 1000;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){ 
			GameVioletta.vGame.changeDragonPos(-18);
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			GameVioletta.vGame.changeDragonPos(18);
		}
//		else if(e.getKeyCode() == KeyEvent.VK_UP){
//			addObject(GameVioletta.vGame.addDragon("img/dragon1.png"));
//		}
//		else if(e.getKeyCode() == KeyEvent.VK_DOWN){
//			remove(GameVioletta.vGame.removeDragon());
//		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e){	
	}

	@Override
	public KeyListener getKeyListener(){
		return this;
	}

	/*
	 * Getter and setter for score
	 */

	public static void setScore(int x){
		score = x;
	}

	public static int getScore(){
		return score; 
	}

	public static void setScoreDisplay(){
		scoreDisplay.setText("Score: " + score);
	}

//	public void initGame(String imgSrc){
//		score = 0;
//		addObject(GameVioletta.vGame.addDragon(imgSrc));
//	}

	public void removeDragonToScreen(Dragon d){
		remove(d);
	}

	public void addDragonToScreen(Dragon d){
		addObject(d);
	}
	
	public int getPowerUp(){
		return powerUp;
	}
	
	public void setPowerUp(int x){
		powerUp = x; 
	}
}
