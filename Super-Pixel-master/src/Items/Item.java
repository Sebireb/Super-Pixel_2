package Items;

import ledControl.BoardController;
import ledProjects.Drawable;
import ledProjects.DrawableType;
import worlds.World;

public abstract class Item implements Drawable {
	
	public final static int[] COIN = new int[]{90, 90, 0};
	public final static int[] MUSHROOM = new int[]{90, 0, 0};
	
	public final static Enum drawableType = DrawableType.ITEM;

	BoardController controller;
	String type;
	double x, y;
	int color[];
	int background[];
	World w;

	public Item(double x, double y, int color[], BoardController controller, int[] background) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.controller = controller;
		this.background = background;
	}
	
	@Override
	public Enum getDrawableType() {
		return drawableType;
	}
	
	@Override
	public void draw() {
		controller.setColor((int) Math.round(x) + World.getXOffset(), (int)Math.round(y), color);
	}

	@Override
	public void clear() {
		controller.setColor((int) Math.round(x) + World.getXOffset(), (int)Math.round(y), background);
	}
	
	@Override
	public abstract void collide();
	
	public void move(double dx, double dy){
		x += dx;		
		y += dy;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int[] getColor() {
		return color;
	}

	public void setColor(int[] color) {
		this.color = color;
	}

	public void setWorld(World w) {
		this.w = w;
	}

}
