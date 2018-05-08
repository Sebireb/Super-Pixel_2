package characters;

import ledControl.BoardController;
import ledProjects.MyFirstBoard;

public class Gumba extends Character {
	
	private static final double DEFAULT_SPEED = 1;
	
	//TODO update, laufen

	public Gumba(double x, double y, int size, BoardController controller, int[] background) {
		super(x, y, GUMBA, size, controller, background);
		name = "Gumba";
		speedy = 0;
		speedx = DEFAULT_SPEED;
	}

	@Override
	public void collide() {
		MyFirstBoard.removeDrawable(this);
	}

}
