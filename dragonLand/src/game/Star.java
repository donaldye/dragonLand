/**
 * 
 */
package game;

import java.awt.Graphics2D;
import java.util.ArrayList;

import guiPractice.components.Graphic;
import guiPractice.components.MovingComponent;
import guiPractice.components.Visible;

/**
 * @author Tamanna Hussain
 *
 */
public class Star extends Graphic {

	private double vy;
	private ArrayList<Star> starArray;
	private Graphic starImage;
	
	public Star(int x, int y, int w, int h) {
		super(x, y, w, h, "img/star.png");
		System.out.println("star");
		setX(getRandomX());
		setY(y);
		vy = 0;
	}
	
	private int getRandomX() {
		int randomX = (int) (Math.random()*GameScreen.getWidth()); 
		System.out.println(randomX);
		return randomX;		
	}

//	public void initAllObjects(ArrayList<Visible> viewObjects) {
//		starArray = new ArrayList<Star>();
//		
//		starImage = new Graphic(10, 10, getWidth(), getHeight(), "img/star.png");
//		viewObjects.add(starImage);
//		
//	}
	
//	@Override
//	public void checkBehaviors() {
//		//If the stars, fall after a certain point (y = 100), they disappear
//		if(getY() > 100){
//			setY(100);
//			starSpeed *= -5;
//		}
//	}
//
//	@Override
//	public void drawImage(Graphics2D g) {
//		// TODO Auto-generated method stub
//
//	}
	
// 	public void setStarSpeed(int x){
// 		starSpeed = x;
// 	}
	
// 	public int getStarSpeed(){
// 		return starSpeed;
// 	}
	
// 	public void run() {
// 		posx = getX();
// 		posy = getY();
// 		running = true;
// 		moveTime = System.currentTimeMillis();
// 		while(running){
// 			try {
// 				Thread.sleep(REFRESH_RATE);
// 				checkBehaviors();
// 				update();
// 			} catch (InterruptedException e) {
// 				e.printStackTrace();
// 			}
// 		}
// 	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

// 	public boolean isRunning() {
// 		return running;
// 	}

// 	public void setRunning(boolean running) {
// 		this.running = running;
// 	}

// 	public void play() {
// 		if(!running){
// 			Thread go = new Thread(this);
// 			go.start();
// 		}
// 	}
}
