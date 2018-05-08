package characters;

import blocks.Block;
import ledControl.BoardController;
import ledProjects.Drawable;
import ledProjects.DrawableType;
import worlds.World;

public abstract class Character implements Drawable{
	
	public final static int[][] MARIO = new int[][] {{127,0,0}};
	public final static int[][] GUMBA = new int[][] {{127, 80, 0}};
	
	public final static Enum drawableType = DrawableType.CHARACTER;
	
	BoardController controller;
	String name;
	double x;
	double y;
	double speedx;
	double speedy;
	int color[][];
	int size;
	int state;
	int[] background;
	protected boolean player = false;
	World w;

	public Character(double x, double y, int color[][], int size, BoardController controller, int[] background) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = size;
		this.controller = controller;
		this.background = background;
		state = 0;
		draw();
	}
	
	@Override
	public Enum getDrawableType() {
		return drawableType;
	}

	@Override
	public void draw() {
		for(double y = this.y-size; y < this.y; y++){
			controller.setColor((int) Math.round(x) + World.getXOffset(), (int)Math.round(y+0.5), color[state]);
		}	
	}

	@Override
	public void clear() {
		for(double y = this.y-size; y < this.y; y++){
			controller.setColor((int) Math.round(x) + World.getXOffset(), (int)Math.round(y), background);
		}	
	}
	
	@Override
	public abstract void collide();
	
	public void move(double dx, double dy){
		Drawable d = w.getCollideable(x + dx + World.getXOffset(), y);
		
		if (d == null) {
			x += dx;	
			y += dy;
		} else {
			if (d.getDrawableType() == DrawableType.BLOCK) {
				speedx = -speedx;
			}
			if (d.getDrawableType() == DrawableType.CHARACTER && ((Character)d).isPlayer()) {
				d.collide();
			}
		}		
			
	}
	
	public void addSpeedX(double speed){
		speedx += speed;
	}
	
	public void addSpeedY(double speed){
		speedy += speed;
	}

	public int[][] getColor() {
		return color;
	}

	public void setColor(int[][] color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
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

	public double getSpeedX() {
		return speedx;
	}

	public void setSpeedX(double speedx) {
		this.speedx = speedx;
	}

	public double getSpeedY() {
		return speedy;
	}

	public void setSpeedY(double speedy) {
		this.speedy = speedy;
	}

	public boolean isPlayer() {
		return player;
	}
	
	public void setWorld(World w) {
		this.w = w;
	}
	
	public World getWorld() {
		return w;
	}

}
