package blocks;

import ledControl.BoardController;

public class EventBlock extends Block{
	
	String item;

	public EventBlock(int x, int y, BoardController controller, int[] background, String item){
		super(x, y, Block.EVENTBLOCK, controller, background);
		name = "?-Block";
		this.item = item;
	}

	@Override
	public void collide() {
		state = 1;
		switch (item) {
			case "coin" : w.getMario().pickUpCoin(1); break;
		}
		draw();
	}

}
