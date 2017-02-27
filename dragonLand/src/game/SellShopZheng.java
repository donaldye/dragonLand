package game;

import java.util.ArrayList;

import dragonComponents.Dragon;
import dragonComponents.DragonLabel;
import dragonComponents.DragonLabel2;
import dragonComponents.StoreSellInterfaceK;
import dragonComponents.SellScreenInterface;
import guiPractice.components.Action;
import guiPractice.components.Clickable;
import guiPractice.components.Visible;

public class SellShopZheng extends ShopScreen implements SellScreenInterface, StoreSellInterfaceK{
	
	private ArrayList<Dragon> dragonsInSellShop;
	private ArrayList<Dragon> dragonsSold;
	private int pageNumber;
	
	public SellShopZheng(int width, int height) {
		super(width, height);
	}

	@Override
	public void addDragonLabels(ArrayList<Visible> viewObjects) {
		setArrowAction();
		inLists();
		pageNumber = 1;
		setPageDisplay();
		
		drawDragons();
	}

	private void setArrowAction() {
		getArrowLeft().setAction(new Action(){

			@Override
			public void act() {
				if(pageNumber == 2)
				{
					pageNumber --;
					//update();
					
					removeDragons();
					drawDragons();
					getPage().setText("Page " + pageNumber + " of 2");
				}
			}
			
		});
		
		getArrowRight().setAction(new Action(){

			@Override
			public void act() {
				if(pageNumber == 1 && dragonsInSellShop.size() > 3)
				{
					pageNumber ++;
					//update();
					
					removeDragons();
					drawDragons();
					getPage().setText("Page " + pageNumber + " of 2");
				}
			}
			
		});
		
	}

	@Override
	public Dragon getSold() {
		
		if(dragonsSold.size() > 0)
			return dragonsSold.get(dragonsSold.size() - 1);
		
		return null;
	}

	@Override
	public String[] getNamesOfPurchased() {
		String[] names = new String[dragonsSold.size()];
		for(int i = 0; i < dragonsSold.size(); i++)
			names[i] = dragonsSold.get(i).getName();
			
		return names;
	}
	
	public void inLists()
	{
		dragonsInSellShop = new ArrayList<Dragon>();
		dragonsSold = new ArrayList<Dragon>();
		for(int i = 0; i < HomeKat.getDragonsOnScreen().size(); i++)
		{
			dragonsInSellShop.add(HomeKat.getDragonsOnScreen().get(i));
		}
		
		getDragonAmount().setText(dragonsInSellShop.size() + "/6 Dragons");
	}
	
	public void setPageDisplay()
	{
		if(dragonsInSellShop.size() > 3)
			getPage().setText("Page " + pageNumber + " of 2");
		else getPage().setText("Page " + pageNumber + " of 1");
	}
	
	public void drawDragons()
	{
		int labelY = 0;
		int endi;
		if((pageNumber) * 3 > dragonsInSellShop.size())
			endi = dragonsInSellShop.size();
		else endi = (pageNumber)* 3;
		
		for(int i = (pageNumber - 1)* 3; i < endi; i++)
		{
			Dragon dragon = dragonsInSellShop.get(i);
			if(pageNumber == 1)
				labelY = DragonLabel2.LABEL_TOP_MARGIN  + DragonLabel2.getLabelHeight() * i + (i * 10);
			else
				labelY = DragonLabel2.LABEL_TOP_MARGIN  + DragonLabel2.getLabelHeight() * (i - 3) + ((i - 3) * 10);
			
			DragonLabel2 label = new  DragonLabel2(DragonLabel2.LABEL_LEFT_MARGIN, labelY, dragon, "SELL");
			label.setAction(new Action(){
				public void act()
				{
					dragonsSold.add(dragon);
					
					dragonsInSellShop.remove(dragon);
					removeDragons();
					drawDragons();
					
//					System.out.println(dragon.getName());
//					System.out.println(dragonsInSellShop.toString());
					
					DragonLand.coins += label.getDragonPrice().getPrice();
					getCoins().setCoins();
					
					getDragonAmount().setText(dragonsInSellShop.size() + "/6 Dragons");
					
					setPageDisplay();
				}
			});
			addObject(label);
		}
	}
	
	public void removeDragons()
	{
		for(int i = 0; i  < viewObjects.size(); i++)
		{
			Visible v = viewObjects.get(i);
			if(v instanceof DragonLabel2)
			{
				remove(v);
				i--;
			}
		}
			
	}

	@Override
	public void removeFlownAwayDragon(Dragon d) {
		// TODO Auto-generated method stub
		
	}
}
