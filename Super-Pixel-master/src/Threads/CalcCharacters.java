package Threads;

import java.util.ArrayList;
import java.util.List;

import worlds.World;
import characters.Character;

public class CalcCharacters implements Runnable{
	
	final double SPEED = 0.1; 
	
	World w;
	List<Character> c = new ArrayList<Character>();

	public CalcCharacters(World w) {
		this.w = w;
		for (Character character : w.getCharacters()) {
			if (character != w.getMario()) {
				c.add (character);
			}		
		}
	}

	@Override
	public void run() {
		while (true) {
			
			for (Character character : c) {
				character.move(character.getSpeedX(), character.getSpeedY());
			}
			
			w.recalcPositions();
			
			try {
				Thread.sleep((long) (100 * SPEED));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeCharacter(Character character) {
		c.remove(character);
	}

}