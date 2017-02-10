package game;

import java.awt.Color;
import java.util.ArrayList;

import dragonComponents.ShopActionButton;
import dragonComponents.ShopLabel;
import guiPractice.ClickableScreen;
import guiPractice.components.Action;
import guiPractice.components.Button;
import guiPractice.components.TextLabel;
import guiPractice.components.Visible;

public class HomeShopScreen extends ClickableScreen {
	
	public Button myButton;
	/*
	 * Color of title (224, 102, 102)
	 * Color of buttons (230,195,147)
	 * 
	 * Color of title,catalog in buy screen and sell screen (222,195,166)
	 * Color of buy/sell button (244,215,183)
	 */
	

	public HomeShopScreen(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initAllObjects(ArrayList<Visible> visible) {
//		Button exit = new Button(getWidth() - 60,  10, 50, 50, "X", Color.RED, new Action(){
//			public void Act()
//			{
//				
//			}
//		});
		int shopTopMargin = 50;
		int titleWidth = 300;
		int titleHeight = 50;
		
		//textLabel background = new TextLabel(x, y, w, h, "")
		

		ShopLabel shopTitle = new ShopLabel(getWidth()/2 - titleWidth/2, shopTopMargin + getHeight()/10, titleWidth, titleHeight, "Dragon Shop");

		int buttonWidth = 120;
		int buttonHeight = 55;
		int topMargin = 40;
		ShopActionButton buy = new ShopActionButton(getWidth()/2 - buttonWidth/2, shopTopMargin + getHeight()/4 + topMargin, buttonWidth, buttonHeight, "BUY", new Color(230,195,147), new Action(){
			public void act()
			{
				DragonLand.game.setScreen(DragonLand.buyScreen);

			}
		});
		ShopActionButton sell = new ShopActionButton(getWidth()/2 - buttonWidth/2, shopTopMargin + getHeight()/2 + topMargin, buttonWidth, buttonHeight, "SELL", new Color(230,195,147), new Action(){
			public void act()
			{

				DragonLand.game.setScreen(DragonLand.sellScreen);

			}
		});

		visible.add(shopTitle);
		visible.add(buy);
		visible.add(sell);
}
}