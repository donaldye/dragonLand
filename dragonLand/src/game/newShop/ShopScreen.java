package game.newShop;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

import game.DragonLand;
import game.mainScreenTeam.Dragon;
import game.mainScreenTeam.HomeKat;
import game.miniGameTeam.GameScreen;
import guiPractice.ClickableScreen;
import guiPractice.components.Action;
import guiPractice.components.Button;
import guiPractice.components.ClickableGraphic;
import guiPractice.components.Graphic;
import guiPractice.components.PolygonButton;
import guiPractice.components.TextLabel;
import guiPractice.components.Visible;
import introScreens.Fog;

public class ShopScreen extends ClickableScreen {
	
	private Action action;
	private Graphic background;

	
	private int currentPage;
	private int totalPages;
	
	//make this one final
	private int dragonsPerPage;
	
	private ArrayList<Dragon> dragonsToBuy;
	private ArrayList<Dragon> myDragons;
	private ArrayList<Object> dragonsOnDisplay;
	
	public ShopScreen(int width, int height) {
		super(width, height);
		update();
	}
	
	//what does this do
	public ShopScreen(int width, int height, /*ArrayList<Dragon> dl,*/ Action act) {
		super(width, height);
		//dragonList = dl;
		action = act;
		update();
	}

	@Override
	public void initAllObjects(ArrayList<Visible> viewObjects) {
		currentPage = 1; 
		background = new Graphic(0,0,getWidth(),getHeight(),"img/sunsetBackground.jpg");
		viewObjects.add(background);
		
		setUpFog();
		Graphic post = new ClickableGraphic(0, getHeight()-150, 0.6,"img/backSign.png");
		viewObjects.add(post);
		
		Graphic nextButton = new ClickableGraphic(getWidth() - 150, getHeight()-120, 0.6, "img/nextPreviousSign.png");
		viewObjects.add(nextButton);
		
		addPostButtons();
		dragonsPerPage = 6;
		dragonsToBuy = HomeKat.getDragons();
		myDragons = new ArrayList<Dragon>();
		dragonsOnDisplay = new ArrayList<Object>();
		//generateInitialDragons();
		totalPages = dragonsToBuy.size() / dragonsPerPage;
		if((dragonsToBuy.size() % dragonsPerPage) > 0){
			totalPages++;
		}
		//drawDragons();
	}
	
	private void addPostButtons() {
		Polygon forward = new Polygon();
		forward.addPoint(20, 18);
		forward.addPoint(130, 45);
		forward.addPoint(135, 50);
		forward.addPoint(120, 62);
		forward.addPoint(12, 40);
		forward.addPoint(20, 18);

		PolygonButton forwardBtn = new PolygonButton(DragonLand.WIDTH - 150, DragonLand.HEIGHT-120, 150, 100, forward, new Action(){
			@Override
			public void act() {
				if(currentPage < totalPages){
					currentPage++;
					removeDisplayDragon();
					drawDragons();
				}
			}});
	    
	    viewObjects.add(forwardBtn);
	    
	    Polygon previous = new Polygon();
	    previous.addPoint(20, 18);
	    previous.addPoint(120, 30);
	    previous.addPoint(120, 60);
	    previous.addPoint(20, 35);
	    previous.addPoint(5, 25);
	    previous.addPoint(20, 18);

	    PolygonButton previousBtn = new PolygonButton(DragonLand.WIDTH - 150, DragonLand.HEIGHT-80, 150, 100, previous, new Action(){
			@Override
			public void act() {
				if(currentPage > 1){
					currentPage--;
					removeDisplayDragon();
					drawDragons();
				}
			}});
	    
	    viewObjects.add(previousBtn);
	    
	    Polygon back = new Polygon();
	    back.addPoint(20, 18);
	    back.addPoint(120, 30);
	    back.addPoint(120, 60);
	    back.addPoint(20, 35);
	    back.addPoint(5, 25);
	    back.addPoint(20, 18);

	    PolygonButton backBtn = new PolygonButton( 0, DragonLand.HEIGHT-110, 150, 100, back, new Action(){
			@Override
			public void act() {
				DragonLand.game.setScreen(DragonLand.shopMain);
			}});
	    
	    viewObjects.add(backBtn);
	    
	    
	}

//	public void generateInitialDragons() {
//		String[] names = new String[] {"Rowdy","Thorn","Mushu","Falcor","Elliot","Puff","Spyro","Sandy",
//				"Scaly","Nessie","Nymph","Sparky","Flambi","Drago","Viper","Moon","Saphira","Scorch","Toothless","Stormfly"};
//		int price=50;
//		for(int i = 0; i < names.length; i++){
//			Dragon d = new Dragon(0,0,50,50, names[i], price + (i*50), "img/dragon"+i+".png");
//			dragonsToBuy.add(d);
//		}
//	}

	public void drawDragons(){
		int startDragon;
		if(currentPage == 1){
			startDragon = 0; 
		}else{
			startDragon = ((currentPage - 1) * 6); 
		}
		
		for(int i = startDragon; (i < startDragon + 6) && (i < dragonsToBuy.size()); i++){
			ShopDragon d = new ShopDragon(getDisplayX(startDragon, i), getDisplayY(startDragon, i), getWidth(), getHeight(), dragonsToBuy.get(i));
			
			//ShopDragon d = new ShopDragon(0.5, 0.5, getWidth(), getHeight(), dragonsToBuy.get(i));
			dragonsOnDisplay.add(dragonsToBuy.get(i));
			
			//some dragon setups
				Dragon disD = d.getDragon();
				disD.setBounce(false);
				disD.setConstantVY(0.05);
				disD.play();
				disD.setDirection(0);
				disD.setCurrentFrame(0);
			
			//button setups
				//ClickableGraphic button = d.getBackdrop();
				d.getBackdrop().setAction(new Action(){
					public void act() {
						System.out.println("I am acting");
						buyDragon(disD);
						drawDragons();
					}
				});
			
			viewObjects.add(d.getBackdrop());
			viewObjects.add(disD);
			dragonsOnDisplay.add(d.getBackdrop());
			dragonsOnDisplay.add(disD);
		}
	}

	
	public double getDisplayX(int start, int i){
		int pos = i - start;
		if(pos == 0 || pos == 3){
			return 0.25;
		}
		if(pos == 1 || pos == 4){
			return 0.5;
		}
		if(pos == 2 || pos == 5){
			return 0.75;
		}
		else return 0.333;
	}
	
	public double getDisplayY(int start, int i){
		int pos = i - start;
		if(pos < 3){
			return 0.333333333;
		}
		else return 0.66666666;
	}
	
	public void removeDisplayDragon(){
		for(Object o: dragonsOnDisplay){
			viewObjects.remove(o);
		}
		dragonsOnDisplay.clear();
	}
		
	public void enterShop(){
		//should update the shop and return to the first page
		currentPage = 1;
		removeDisplayDragon();
		drawDragons();
		
	}
	
	public void setUpFog(){
		Fog fog; 
		for(int i = -10; i < 10; i++){
			fog = new Fog((i*getWidth() / 10), 200, 500, 300, "img/introFog.png", 100);
			viewObjects.add(fog);
			fog.setY(fog.generateYPos());
			fog.play();
		}
	}
	
	/*
	 * getters and setters for dragon arrays
	 */
		public ArrayList<Dragon> getMyDragons(){
			return myDragons;
		}
	
		public void buyDragon(Dragon d){
			dragonsToBuy.remove(d);
			myDragons.add(d);
		}
		
		public void addToMyDragons(Dragon d){
			myDragons.add(d);
		}
	
		public void removeFromBuyDragons(Dragon d){
			dragonsToBuy.remove(d);
		}
	
		public void removeFromMyDragons(Dragon d){
			myDragons.remove(d);
			dragonsToBuy.add(d);
		}
	
		public String[] getNamesOfPurchased(){
			String[] output = new String[myDragons.size()];
			for(int i = 0; i < myDragons.size(); i++){
				output[i] = myDragons.get(i).getName();
			}
			return output;
		}
	
}