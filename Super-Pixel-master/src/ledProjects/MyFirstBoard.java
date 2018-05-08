package ledProjects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import Items.Coin;
import Items.Item;
import Items.Mushroom;
import Threads.CalcCharacters;
import Threads.Update;
import Threads.xMovement;
import Threads.yMovement;
import blocks.Block;
import blocks.Brick;
import blocks.Grass;
import characters.Character;
import characters.Gumba;
import characters.Mario;
import ledControl.BoardController;
import ledControl.gui.KeyBuffer;
import worlds.World;

public class MyFirstBoard {
	
	static BoardController controller = BoardController.getBoardController();
	public final static int[] background = new int[]{0, 0, 0};
	final static int[][] brick = new int[][]{{127,80,30}};
	final static int[][] eventBlock = new int[][]{{127,127,0},{127, 40, 0}};
	private static World w;
	private static KeyBuffer input;
//	private static Thread t, t2, t3, t4;
	private static yMovement yMovement;
	private static Update update;
	private static CalcCharacters calc;
	private static boolean left, right, up;
	
	//final static int[][] gameover = new int[][] {};

	public static void main(String[] args) {
		
		input = controller.getKeyBuffer();
		
		for(int x = 0; x < 12; x++) {
			for(int y = 0; y < 12; y++) {
				controller.setColor(x, y, background);
			}
		}
		controller.updateLedStripe();
		
		//TESTCODE
		
		List<Block> b = new ArrayList<Block>();
		for(int x = 0; x < 30; x++) {
			b.add(new Grass(x, 11, controller, background));
		}
		for(int x = 0; x < 10; x++) {
			b.add(new Brick(x, 5, controller, background));
		}
		
		b.add(new Grass(10, 9, controller, background));
		b.add(new Grass(10, 10,  controller, background));
		
		List<Character> c = new ArrayList<Character>();
		c.add(new Mario(6, 0, 0, controller, background));
		c.add(1, new Gumba(1, 10, 0, controller, background));
		
		List<Item> i = new ArrayList<Item>();
		for(int x = 0; x < 10; x++) {
			i.add(new Coin(x, 6, controller, background));
		}
		i.add(new Mushroom(20, 10, controller, background));
		i.add(new Mushroom(22, 10, controller, background));
		
		w = new World(b, c, i, controller, 100, 12);
		
		for(Block block : b) {
			block.setWorld(w);
		}
		for(Character character : c) {
			character.setWorld(w);
		}
		for(Item item : i) {
			item.setWorld(w);
		}
		
		//-----------
		
//		update = new Update(controller, b, c, i, background);
//		
//		t = new Thread(update);
//		t.start();
//		
//		t2 = new Thread(new xMovement(w, w.getMario()));
//		t2.start();
//		
//		yMovement = new yMovement(w);
//		
//		t3 = new Thread(yMovement);
//		t3.start();
//		
//		calc = new CalcCharacters(w);
//		
//		t4 = new Thread(calc);
//		t4.start();
//		
//		while(true){
//			processInput(input.pop());
//			input.clear();
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		gameLoop();
		
	}
	
	private static void gameLoop() {
		
		while (true) {
			controller.resetColors();
			checkInput();
			w.update(up, left,right);
			controller.updateLedStripe();
		}
		
	}
	
	private static void checkInput() {
		KeyEvent[] inputs = input.popAll();
		for (KeyEvent e : inputs) {
			processInput(e);
		}
	}
	
//	public static void goRight(){
//		w.getMario().setSpeedX(-1);
//	}
//	
//	public static void goLeft(){
//		w.getMario().setSpeedX(1);
//	}
//	
//	public static void stopXMovement() {
//		w.getMario().setSpeedX(0);
//	}
//	
//	public static void jump() {
//		if(w.drawableBelowMario() instanceof Block){
//			yMovement.jump();
//		}
//	}
	
	public static void removeDrawable(Drawable d) {
		w.removeDrawable(d);
		d.clear();
	}
	
	public static void processInput(KeyEvent e){
		if(e == null){
			return;
		}
		if(e.getID() == KeyEvent.KEY_PRESSED){
			if(e.getKeyCode() == KeyEvent.VK_A){
				left = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				right = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				up = true;
			}
		}
		if(e.getID() == KeyEvent.KEY_RELEASED){
			if(e.getKeyCode() == KeyEvent.VK_A){
				left = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				right = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				up = false;
			}
		}
	}
}
