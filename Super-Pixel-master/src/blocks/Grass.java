package blocks;

import ledControl.BoardController;

public class Grass extends Block{

	public Grass(int x, int y, BoardController controller, int[] background){
		super(x, y, Block.GRASS, controller, background);
		name = "Grass";
	}

	@Override
	public void collide() {
		
	}

	
}
