
//Power up class for the skip turn power up (green)
//Has a constructor and an activation method which activates it on the main game

import java.awt.Color;

public class SkipTurnAI {
	public int X;
	public int Y;
	
	//Constructor
	public SkipTurnAI(int x, int y) {
		X = x;
		Y = y;
		//Draws the green colour which represents this specific power up on the grid
		GamePanelAI.grid[y][x] = Color.green;
	}
	
	//If power activated, skips a turn in GamePanelAI class
	public void activate() {
		GamePanelAI.turn++;
	}
}
