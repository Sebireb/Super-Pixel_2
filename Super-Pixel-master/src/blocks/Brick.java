package blocks;

import ledControl.BoardController;
import ledProjects.MyFirstBoard;

public class Brick extends Block {

	public Brick(int x, int y, BoardController controller, int[] background) {
		super(x, y, Block.BRICK, controller, background);
		name = "Brick";
	}

	@Override
	public void collide() {
		if (w.getMario().getSize() >= 2) {
			MyFirstBoard.removeDrawable(this);
		}
	}

}
