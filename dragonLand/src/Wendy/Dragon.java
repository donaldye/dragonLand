package Wendy;

import java.awt.Graphics2D;

import guiPractice.components.AnimatedComponent;

public class Dragon extends AnimatedComponent {

	private String name;
	private int price;
	private String imgSrc;
	int direction;
	
	int UP=1;
	int SIDE=2;
	

	public Dragon(int x, int y, int w, int h,  String name, int price,String imgSrc) {
		super(x, y, w, h);
		
		this.name=name;
		this.price=price;
		this.imgSrc=imgSrc;

	}

	@Override
	public void checkBehaviors() {
//		if(true){
//			if(currentFrame==2)
//				currentFrame=0;
//		}
	}

	@Override
	public void drawImage(Graphics2D g) {
		super.drawImage(g);
	}
	public void animationUp(){
		
	
	}
	public void animationDown(){
		
	}
	public void animationLeft(){
	}
	public void animationRight(){
	}

	public String getName() {
		return name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getImgSrc() {
		return imgSrc;
	}
}