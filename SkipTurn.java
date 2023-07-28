/* 
Wenzhao Pan and Joshua Choi
June 20, 2022
ICS Culminating
 */

//Power up class for the skip turn power up (green)
//Has a constructor and an activation method which activates it on the main game

import java.awt.*;

public class SkipTurn {
	public int X;
	public int Y;
	
	//Constructor
	public SkipTurn(int x, int y) {
		X = x;
		Y = y;
		//Draws the green colour which represents this specific power up on the grid
		GamePanel.grid[y][x] = Color.green;
	}
	
	//If power activated, skips a turn in GamePanel class
	public void activate() {
		GamePanel.turn++;
	}
}