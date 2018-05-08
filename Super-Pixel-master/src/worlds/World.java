package worlds;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import Items.Item;
import blocks.Block;
import characters.Character;
import characters.Mario;
import ledControl.BoardController;
import ledProjects.Drawable;
import ledProjects.DrawableType;

public class World {
	private static final double MAX_FALL_SPEED = 0.3;
	
	BoardController controller;
	List<Block> blocks = new ArrayList<Block>();
	List<Character> characters = new ArrayList<Character>();
	List<Item> items = new ArrayList<Item>();
	Mario mario;
	private static boolean movement;
	private Drawable[][] drawables;
	private static double xOffset;
	
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
	private final Lock writeLock = lock.writeLock(),
						readLock = lock.readLock();

	public World(List<Block> blocks, List<Character> characters, List<Item> items, BoardController controller, int sizeX, int sizeY) {
		this.controller = controller;
		this.blocks = blocks;
		this.characters = characters;
		this.items = items;
		drawables = new Drawable[sizeX][sizeY];
		for(Block b : blocks) {
			drawables[(int)b.getX()][(int)b.getY()] = b;
		}
		for(Item i : items) {
			drawables[(int)i.getX()][(int)i.getY()] = i;
		}
		for(Character c : characters) {
			drawables[(int)c.getX()][(int)c.getY()] = c;
		}
		mario = (Mario) characters.get(0);
		allowMovement(true);
		xOffset = 0;
		controller.updateLedStripe();
	}
	
	public void update(boolean up, boolean left, boolean right) {
		for (Block b : blocks) {
			b.draw();
		}
		for (Item i : items) {
			i.draw();
		}
		for (Character c : characters) {
			if (!c.getName().equals("Mario")) {
				//c.move(c.getSpeedX(), c.getSpeedY());
				c.draw();
			}
				
		}
		updateMario(up, left, right);
		mario.draw();
	}
	
	private void updateMario(boolean up, boolean left, boolean right) {
		if (drawableBelowMario() != null && drawableBelowMario().getDrawableType() == DrawableType.BLOCK) {
			//TODO diagonales fallen
			mario.stopFall();
			if (up) {
				// TODO Jump
			}
		}else
			mario.fall();
		if (left) {
			mario.setSpeedX(-1);
		}
		if (right) {
			mario.setSpeedX(1);
		}
		if (!right && !left) {
			mario.setSpeedX(0);
		}
		mario.move();
	}
	
//	public void move(int dx, int dy){
//		for(Block b : blocks){
//			b.move(dx, dy);	
//		}
//		for(Character c : characters){
//			if(c != mario){
//				c.move(dx, dy);
//			}
//		}
//		for (Item i : items){
//			i.move(dx, dy);
//		}
//	}

	public Drawable getCollideable(double x, double y) {
		try {
			return drawables[(int) Math.round(x - xOffset)][(int) Math.round(y)];
		} catch (IndexOutOfBoundsException e) { // Outside the map
			return null;
		}
	}
	
	public Drawable drawableBelowMario(){
		Drawable d = getCollideable(mario.getX(), mario.getY() + 1);
		return d;
	}
	
	public Drawable drawableAboveMario() {
		Drawable d = getCollideable((int)Math.round(mario.getX()), (int)Math.round(mario.getY() - 1.5));
		return d;
	}
	
	public void fall() {
		// || drawableBelowMario() == mario <-- eine etage tiefer
		if(drawableBelowMario() == null ) {
			if(mario.getSpeedY() > MAX_FALL_SPEED){
				return;
			}
			mario.addSpeedY(0.1);
		}else if(mario.getSpeedY() > 0){
			mario.setSpeedY(0);
		}
	}
	
	public void removeDrawable(Drawable d) {
		writeLock.lock();
		int x = (int) Math.round(d.getX());
		int y = (int) Math.round(d.getY());
		drawables[x][y] = null;	
		if (d instanceof Block) {
			blocks.remove(d);
		}
		if (d instanceof Item) {
			items.remove(d);
		}
		if (d instanceof Character) {
			characters.remove(d);
		}
		writeLock.unlock();
	}
	
	public void recalcPositions() {
		writeLock.lock();
		for (int x = 0; x < drawables.length; x++) {
			for (int y = 0; y < drawables[0].length; y++) {
				if (drawables[x][y] != null && ( drawables[x][y] instanceof Character || drawables[x][y] instanceof Item)) {
					drawables[x][y] = null;
				}
			}
		}
		for(Item i : items) {
			drawables[(int)i.getX()][(int)i.getY()] = i;
		}
		for(Character c : characters) {
			try {
				drawables[(int)c.getX()][(int)c.getY()] = c;
			} catch (ArrayIndexOutOfBoundsException e) {
				drawables[(int)c.getX()][0] = c;
			}
		}
		for(Block b : blocks) {
			drawables[(int)b.getX()][(int)b.getY()] = b;
		}
		writeLock.unlock();
	}

	public Mario getMario() {
		return mario;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public List<Character> getCharacters() {
		return characters;
	}
	
	public List<Item> getItems(){
		return items;
	}

	public static boolean isMovementAllowed() {
		return movement;
	}

	public static void allowMovement(boolean movement) {
		World.movement = movement;
	}
	
	public void addXOffset(double xOffset) {
		World.xOffset += xOffset;
	}
	
	public void setXOffset(double xOffset){
		World.xOffset = xOffset;
	}
	
	public Drawable[][] getDrawables(){
		return drawables;
	}
	
	public static int getXOffset() {
		return (int) Math.round(xOffset);
	}

}
