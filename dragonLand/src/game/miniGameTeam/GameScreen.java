package game.miniGameTeam;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import guiPractice.components.ClickableGraphic;
import guiPractice.components.Graphic;
import guiPractice.components.PolygonButton;
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
import introScreens.Fog;

/**
 * @author Tamanna Hussain
 *
 */
public class GameScreen extends ClickableScreen implements KeyListener {

	private static Graphic scoreDisplay;
	private Button highScoreButton;
	private Graphic background;
	private int time;
	private int count; //num of stars on screen
	private static ArrayList<Star1> starArray;
	private static ArrayList<PowerUp> powerUpArray;
	private static int score;
	public static GameScreen tGame;
	public static boolean isNotHome;
	private int powerUp = 0;
	private static int speedLeft;
	private static int speedRight;
	
	
	public GameScreen(int width, int height) {
		super(width, height);
		tGame = this;
	}

	@Override
	public void initAllObjects(ArrayList<Visible> view) {
		//initial score is 0 and it should count the number of stars caught
		score = 0;
		count = 0;
		time = 2500;
		powerUp = 0;
		starArray = new ArrayList<Star1>();
		powerUpArray = new ArrayList<PowerUp>();

		background = new Graphic(0,0,DragonLand.WIDTH,DragonLand.HEIGHT,"img/sunsetBackground.jpg");
		viewObjects.add(background);
		setUpFog();
		setUpBackButton();
	
		scoreDisplay = new Graphic(DragonLand.WIDTH-200, 50, 220, 70, "img/StraightOneSign.png");
		view.add(scoreDisplay);
		
		TextLabel scoreText = new TextLabel(DragonLand.WIDTH-150, 30, 100, 100, score + " Points");
		scoreText.setColor(DragonLand.LIGHT_PINK);
		scoreText.setSize(30);
		viewObjects.add(scoreText);
	}

	private void setUpBackButton() {
		Polygon back = new Polygon();
	    back.addPoint(20, 18);
	    back.addPoint(120, 30);
	    back.addPoint(120, 60);
	    back.addPoint(20, 35);
	    back.addPoint(5, 25);
	    back.addPoint(20, 18);
	    Graphic post = new Graphic(0, getHeight()-150, 0.6,"img/backSign.png");
	    addObject(post);
		PolygonButton backBtn = new PolygonButton( 0, DragonLand.HEIGHT-110, 150, 100, back, new Action(){
			@Override
			public void act() {
				DragonLand.game.setScreen(DragonLand.homeScreen);
				stopGame();
			}});
		addObject(backBtn);
		
	}

	protected void stopGame() {
		DragonLand.game.getViolettaGame().setPlaying(false);
		ArrayList<Dragon> dragonArray = DragonLand.game.getViolettaGame().getDragonArray();
		if(dragonArray.size() != 0){
			for(Dragon d: dragonArray){
				remove(d);
			}
			DragonLand.game.getViolettaGame().eraseDragons();
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
		addObject(DragonLand.game.getViolettaGame().addDragon("img/dragon1.png"));
		Thread start = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					fallingStars();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		start.start();
	}

	public static ArrayList<Star1> getStarArray(){
		return starArray;
	}
	
	public void addStar() throws InterruptedException{
		//adds one star object to the screen and the array
		int yPos = 0;
		int starH = 65;
		int starW = 65;
		Star1 starImage = new Star1(randomX(), yPos, starW, starH, this);
		starImage.play();
		count++;
		if (count >= 5){
			Thread.sleep(1000);
			starArray.add(starImage);
			addObject(starImage);
			count = 0;
		}else{
			starArray.add(starImage);
			addObject(starImage);
		}	
	}
	
	public void removeStar(Star1 star){
		starArray.remove(star);
		remove(star);
	}
	
	public int randomX(){
		//80 through getWidth()-175
		int max = DragonLand.WIDTH-225;
		int min = 125;
		int xPos = (int) (Math.random()*(max - min) + min);
		return xPos;
	}
	
	public void setTime(){
		//picked these numbers for demonstration purposes
		if (score >= 5 && score < 10){
			time = 2000;
		}
		if (score >= 10 && score < 15){
			time = 1750;
		}
		if (score >= 15 && score < 20){
			time = 1500;
		}
		if (score >= 20){
			time = 1250;
		}
	}

	public static ArrayList<PowerUp> getPowerUpArray(){
		return powerUpArray;
	}

	public void addPowerUp(){
		int yPos = 0;
		int berryH = 65;
		int berryW = 65;
		PowerUp powerUpImage = new PowerUp(randomX(), yPos, berryH, berryW, this);
		powerUpImage.play();
		
//		int max = 10;
//		int min = 1;
//		int randomNum = (int) (Math.random()*(max - min) + min);
		
		if (starArray.size() == 20){
			powerUpArray.add(powerUpImage);
			addObject(powerUpImage);
		}	
	}
	
	public void removePowerUp(PowerUp powerUp){
		powerUpArray.remove(powerUp);
		remove(powerUp);
	}

	public void fallingStars() throws InterruptedException{
		DragonLand.game.getViolettaGame().setPlaying(true);
		while(DragonLand.game.getViolettaGame().getPlaying()){
			try{
				setTime();
				Thread.sleep(time);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			addStar();
			//addPowerUp();
		}
		HighScoreScreen.updateOnEnter();
		DragonLand.game.setScreen(DragonLand.highscoreScreen);
	}
	
	public void setUpFog(){
		Fog fog; 

		for(int i = -7; i < 7; i++){
			fog = new Fog((i*getWidth() / 10), 200, 500, 300, "img/introFog.png", 100);
			viewObjects.add(fog);
			fog.setY(fog.generateYPos());
			fog.play();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		speedLeft = -10;
		speedRight = 10;
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){ 
			DragonLand.game.getViolettaGame().changeDragonPos(speedLeft);
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			DragonLand.game.getViolettaGame().changeDragonPos(speedRight);
		}
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
	
	public static void setSpeedLeft(int x){
		speedLeft = x;
	}
	
	public static int getSpeedLeft(){
		return speedLeft;
	}
	
	public static void setSpeedRight(int x){
		speedRight = x;
	}
	
	public static int getSpeedRight(){
		return speedRight;
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
		//scoreDisplay.setText("Score: " + score);
	}
	
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
