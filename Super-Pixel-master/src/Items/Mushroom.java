package Items;

import ledControl.BoardController;
import ledProjects.MyFirstBoard;

public class Mushroom extends Item {

	public Mushroom(double x, double y, BoardController controller, int[] background) {
		super(x, y, MUSHROOM, controller, background);
	}

	@Override
	public void collide() {
		w.getMario().grow();
		MyFirstBoard.removeDrawable(this);
	}

}
