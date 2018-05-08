package Items;

import ledControl.BoardController;
import ledProjects.MyFirstBoard;

public class Coin extends Item{

	public Coin(double x, double y, BoardController controller, int[] background) {
		super(x, y, Item.COIN, controller, background);
		type = "Coin";
	}

	@Override
	public void collide() {
		w.getMario().pickUpCoin(1);
		MyFirstBoard.removeDrawable(this);
	}

}
