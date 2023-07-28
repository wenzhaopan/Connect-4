/* 
Wenzhao Pan and Joshua Choi
June 20, 2022
ICS Culminating
 */

//Power up class for the extra pieces power up (blue)
//Has a constructor and an activation method which activates it on the main game

import java.awt.*;

public class ExtraPiecesAI {
	public int X;
	public int Y;
	
	//Constructor
	public ExtraPiecesAI(int x, int y) {
		X = x;
		Y = y;
		//Draws the blue colour which represents this specific powerup on the grid
		GamePanelAI.grid[y][x] = Color.blue;
	}
	
	//If power activated, puts an extra piece on both adjacent column
	//Never has to worry about going out of bounds because this power up never spawns on the edges of the grid
	public void activate() {
		if(!GamePanelAI.filled[0][X-1]) {
			GameFrameAI.panel.fillTurn(X-1, 5, 5);
		}
		if(!GamePanelAI.filled[0][X+1]) {
			GameFrameAI.panel.fillTurn(X+1, 5, 5);
		}
	}
}