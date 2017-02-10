package dragonComponents;

import java.awt.Graphics2D;

import guiPractice.components.Component;

public class ShopBackdrop extends Component{


	public ShopBackdrop(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Graphics2D g) {
		// TODO Auto-generated method stub
		
		g.drawRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
		g.setColor(new Color());
	}

}
