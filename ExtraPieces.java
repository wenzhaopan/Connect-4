/* 
Wenzhao Pan and Joshua Choi
June 20, 2022
ICS Culminating
 */

//Power up class for the extra pieces power up (blue)
//Has a constructor and an activation method which activates it on the main game

import java.awt.Color;

public class ExtraPieces {
	public int X;
	public int Y;
	
	//Constructor
	public ExtraPieces(int x, int y) {
		X = x;
		Y = y;
		//Draws the blue colour which represents this specific power up on the grid
		GamePanel.grid[y][x] = Color.blue;
	}
	
	//If power activate, adds two extra pieces on each side (never worries about index out of bounds because it can never spawn on the edges of the grid
	public void activate() {
		if(!GamePanel.filled[0][X-1]) {
			GameFrame.panel.fillTurn(X-1, 5, 5);
		}
		if(!GamePanel.filled[0][X+1]) {
			GameFrame.panel.fillTurn(X+1, 5, 5);
		}
	}
}
