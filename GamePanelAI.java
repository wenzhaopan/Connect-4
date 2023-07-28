/* 
Wenzhao Pan and Joshua Choi
June 20, 2022
ICS Culminating
 */

//GamePanelAI is the main game for the AI version
//Has functions to make the computer play for itself and identify if it can win, or if it can block a win, or to randomly play a piece
//Uses the AI versions of the power up classes

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class GamePanelAI extends JPanel  implements MouseListener{

	public int X = 10;
	public int Y = 10;
	public static final int CELL_SIZE = 80;
	public static int turn = 0;
	public static int rows = 6;
	public static int cols = 7;
	public String colour = "";
	public static boolean winner;
	public String s = "";
	public static boolean[][] filled = new boolean[6][7];
	public static Color[][] grid = new Color[rows][cols];
	public SkipTurnAI skipTurn;
	public ExtraPiecesAI extraPieces;
	public int randR = 0;
	public static int[] legalMoves = {5, 5, 5, 5, 5, 5, 5};
	public boolean alrMoved = false;


	//Constructor for the game 
	public GamePanelAI(Dimension dimension) {
		//Restores the original values so that the game may be played multiple times
		//Also initalizes the array and fills it with white
		turn = 2;
		alrMoved = false;
		winner = false;
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				filled[i][j] = false;
				grid[i][j] = Color.white;
			}
		}
		for(int i = 0; i < 7; i++) legalMoves[i] = 5;
		
		//generates random values for the spawning of power ups
		int x = (int)Math.floor(Math.random()*7);
		int x1 = (int)Math.floor(Math.random()*5+1);
		while(x == x1 || x == x1+1 || x == x1-1) x = (int)Math.floor(Math.random()*5);
		setSize(dimension);
		setPreferredSize(dimension);
		addMouseListener(this);
		//spawns power ups
		skipTurn = new SkipTurnAI(x, (int)Math.floor(Math.random()*6));
		extraPieces = new ExtraPiecesAI(x1, (int)Math.floor(Math.random()*5));
	}

	//Overridden paint function that draws/updates everything shown on the screen
	public void paintComponent(Graphics g) {

		Graphics2D g1 = (Graphics2D)g;
		g1.setColor(Color.black);
		g1.fillRect(333,0,CELL_SIZE*7-10, CELL_SIZE*6-10);
		X = 333;
		Y = 0;

		//Draws the rectangular "button" that is used to go back to the main menu
		g.drawRect(10, 10, 75, 50);
		g.drawString("Main Menu", 17, 37);

		//Draws the rectangular "restart" button
		g.drawRect(10, 80, 75, 50);
		g.drawString("Restart", 25, 110);

		// draws grid and sets background to black
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				g1.setColor(grid[row][col]);
				g1.fillOval(X,Y,CELL_SIZE - 10,CELL_SIZE - 10);
				g1.setColor(Color.black);
				g1.drawOval(X,Y,CELL_SIZE -10,CELL_SIZE-10);
				X += CELL_SIZE;
			}
			Y += CELL_SIZE;
			X = 333;
		}
		g1.setColor(Color.blue);

		//Writes who the winner is if there is one
		if(winner) {
			g1.setColor(Color.white);
			g1.drawRect(900,  0,  300,  360);
			g1.fillRect(900,  0,  300,  360);
			g1.setColor(Color.blue);
			s = "Winner: " + getWinner(turn-1);
			g1.drawString(s, 900, 20);
		}
		//Writes "Tie!" if the board is completely filled without a winner
		else if(boardFilled()) {
			g1.setColor(Color.white);
			g1.drawRect(900,  0,  300,  360);
			g1.fillRect(900,  0,  300,  360);
			g1.setColor(Color.blue);
			s = "Tie!";
			g1.drawString(s, 900, 20);
		}

		//Writes who's turn it is if there is no winner
		else {
			g1.setColor(Color.white);
			g1.drawRect(900,  0,  300,  360);
			g1.fillRect(900,  0,  300,  360);
			g1.setColor(Color.blue);
			s = "Turn: " + getTurn(turn);
			g1.drawString(s, 900, 20);
		}
	}

	//Tells us who the winner is, and what colour they are playing
	public String getWinner(int n) {
		if(n%2 == 1) return "AI (Yellow)";
		else return "Player (Red)";
	}

	//Tells us who's turn it is
	public String getTurn(int n) {
		if(n%2 == 1) return "AI (Yellow) - Click on the board for AI to move";
		else return "Player (Red)";
	}

	//Tells us who's turn it is (colour)
	public Color getColor(int n){
		if(n%2 == 0) return Color.red;
		else return Color.yellow;
	}

	//Repaints and updates everything after a mouse is clicked inside the board
	public void mousePressed(MouseEvent e) {
		int x = identifyCol(e.getX());
		int y = identifyRow(e.getY());
		//If game hasn't ended, fills in the game piece for the player
		if(x<7 && y<6 && !winner && !boardFilled() && x >= 0 && y >= 0 && !filled[0][x] && e.getX() >= 333  && e.getX() < 883 && turn%2 == 0) {
			fillTurn(x, y, 5);
			turn++;
			repaint();
		}

		//If game hasn't ended, fills in the game piece for the AI
		else if(x<7 && y<6 && !winner && !boardFilled() && x >= 0 && y >= 0 && e.getX() >= 333 && turn%2 == 1) {
			
			//Variable so the AI only moves once per click
			alrMoved = false;
			AImove();
			repaint();
		}

		//If main menu button is pressed, disposes the window and creates a new main menu object
		if(e.getX() > 10 && e.getX() < 85 && e.getY() > 10 && e.getY() < 60) {
			this.setVisible(false);
			Main.gameframeai.dispose();
			new Main();
		}
		// If restart button is pressed, the game restarts by resetting the board
		else if(e.getX() > 10 && e.getX() < 85 && e.getY() > 80 && e.getY() < 130) {
			reset();
			repaint();
		}
	}

	// Resets the board if user wants to restart
	public void reset() {
		turn = 2;
		winner = false;
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				filled[i][j] = false;
				grid[i][j] = Color.white;
			}
		}
		for(int i =0; i < 7; i++) legalMoves[i] = 5;

		int x = (int)Math.floor(Math.random()*7);
		int x1 = (int)Math.floor(Math.random()*5+1);
		while(x == x1 || x == x1+1 || x == x1-1) x = (int)Math.floor(Math.random()*5);

		skipTurn = new SkipTurnAI(x, (int)Math.floor(Math.random()*6));
		extraPieces = new ExtraPiecesAI(x1, (int)Math.floor(Math.random()*5));
	}


	//Function for the AI to move
	public void AImove() {

		//Checks to see if it can win
		if(!alrMoved && !winner && !boardFilled()) {
			for(int i = 0; i < 7; i++) {
				if(!winner && !filled[0][i] && checkWin(legalMoves[i], i)) {
					if(turn%2 == 1) {
						fillTurn(i, 1, 5);
						turn++;
						alrMoved = true;
					}
				}
			}
		}

		//Checks to see if it can block a win
		if(!alrMoved && !winner && !boardFilled()) {
			for(int i = 0; i < 7; i++) {
				if(!winner && !filled[0][i] && blockWin(legalMoves[i], i)) {
					if(turn%2 == 1) {
						fillTurn(i, 1, 5);
						turn++;
						alrMoved = true;
					}
				}
			}
		}

		//Moves randomly if it can't block a win, or win 
		if(!alrMoved && !winner && !boardFilled()) {
			randR = (int)(Math.random()*7);
			while(filled[0][randR] || randR > 6 || randR < 0) {
				randR = (int)(Math.random()*7);
			}
			if(turn%2 == 1) {
				fillTurn(randR, 1, 5);
				turn++;
				alrMoved = true;
			}
		}
	}

	//Checks to see (in all 4 directions) if it can win this turn
	public static boolean checkWin(int r, int c) {
		int left = 0;
		int right = 0;
		int up = 0;
		int down = 0;
		int leftDown = 0;
		int rightDown = 0;
		int leftUp = 0;
		int rightUp = 0;

		int i = 1;
		while(c+i < 7 && r>=0 && r<6) {
			if(grid[r][c+i] == Color.yellow) right++;
			else break;
			i++;
		}

		i=1;
		while(c-i >= 0 && r>=0 && r<6) {
			if(grid[r][c-i] == Color.yellow) left++;
			else break;
			i++;
		}

		i=1;
		while(r+i<6 && c>=0 && c<7) {
			if(grid[r+i][c] == Color.yellow) down++;
			else break;
			i++;
		}

		i=1;
		while(r-i>=0 && c>=0 && c<7) {
			if(grid[r-i][c] == Color.yellow) up++;
			else break;
			i++;
		}

		i=1;
		while(r+i < 6 && c+i < 7) {
			if(grid[r+i][c+i] == Color.yellow) rightDown++;
			else break;
			i++;
		}

		i=1;
		while(r+i < 6  && c-i >= 0) {
			if(grid[r+i][c-i] == Color.yellow) leftDown++;
			else break;
			i++;
		}

		i=1;
		while(r-i >= 0 && c+i < 7) {
			if(grid[r-i][c+i] == Color.yellow) rightUp++;
			else break;
			i++;
		}

		i=1;
		while(r-i >= 0 && c-i >= 0) {
			if(grid[r-i][c-i] == Color.yellow) leftUp++;
			else break;
			i++;
		}

		if(up+down >=3 || left+right >= 3 || rightDown+leftUp >= 3 || rightUp+leftDown >= 3) 
			return true;
		else return false;
	}

	//Checks to see if it can block the opponent from winning this turn
	public static boolean blockWin(int r, int c) {
		int left = 0;
		int right = 0;
		int up = 0;
		int down = 0;
		int leftDown = 0;
		int rightDown = 0;
		int leftUp = 0;
		int rightUp = 0;

		int i = 1;
		while(c+i < 7 && r>=0 && r<6) {
			if(grid[r][c+i] == Color.red) right++;
			else break;
			i++;
		}

		i=1;
		while(c-i >= 0 && r>=0 && r<6) {
			if(grid[r][c-i] == Color.red) left++;
			else break;
			i++;
		}

		i=1;
		while(r+i<6 && c>=0 && c<7) {
			if(grid[r+i][c] == Color.red) down++;
			else break;
			i++;
		}

		i=1;
		while(r-i>=0 && c>=0 && c<7) {
			if(grid[r-i][c] == Color.red) up++;
			else break;
			i++;
		}
		i=1;
		while(r+i < 6 && c+i < 7) {
			if(grid[r+i][c+i] == Color.red) rightDown++;
			else break;
			i++;
		}

		i=1;
		while(r+i < 6  && c-i >= 0) {
			if(grid[r+i][c-i] == Color.red) leftDown++;
			else break;
			i++;
		}

		i=1;
		while(r-i >= 0 && c+i < 7) {
			if(grid[r-i][c+i] == Color.red) rightUp++;
			else break;
			i++;
		}

		i=1;
		while(r-i >= 0 && c-i >= 0) {
			if(grid[r-i][c-i] == Color.red) leftUp++;
			else break;
			i++;
		}

		if(up+down >=3 || left+right >= 3 || rightDown+leftUp >= 3 || rightUp+leftDown >= 3) 
			return true;
		else return false;
	}

	//Function to actually make a move and fill in the grid, then repaints to make changes visible
	public void fillTurn(int x, int y, int cnt) {
		if(!winner && !boardFilled()) {
			//If mouse click is valid, fills in the game piece for the turn
			if(y < 6 && x < 7 && !filled[0][x]) {
				while(true){
					if(!filled[cnt][x]) break;
					else cnt--;
				}
				if(!winner)grid[cnt][x] = getColor(turn);
				filled[cnt][x] = true;
				checkWinner(cnt, x);

				//Activates SkipTurn power up
				if(x == skipTurn.X && cnt == skipTurn.Y && !winner) skipTurn.activate();

				//Activates ExtraPieces power up
				if(x == extraPieces.X && cnt == extraPieces.Y && !winner) extraPieces.activate();
			}
			//Updates the array for which all of the legal moves for the AI is listed
			if(legalMoves[x]!=0) legalMoves[x]--;
		}
	}

	//Checks to see if there is a winner every turn
	public void checkWinner(int r, int c) {
		int left = 0;
		int right = 0;
		int up = 0;
		int down = 0;
		int leftDown = 0;
		int rightDown = 0;
		int leftUp = 0;
		int rightUp = 0;

		int i = 1;
		while(c+i < 7) {
			if(grid[r][c+i] == getColor(turn)) right++;
			else break;
			i++;
		}

		i=1;
		while(c-i >= 0) {
			if(grid[r][c-i] == getColor(turn)) left++;
			else break;
			i++;
		}

		i=1;
		while(r+i<6) {
			if(grid[r+i][c] == getColor(turn)) down++;
			else break;
			i++;
		}

		i=1;
		while(r-i>=0) {
			if(grid[r-i][c] == getColor(turn)) up++;
			else break;
			i++;
		}

		i=1;
		while(r+i < 6 && c+i < 7) {
			if(grid[r+i][c+i] == getColor(turn)) rightDown++;
			else break;
			i++;
		}

		i=1;
		while(r+i < 6  && c-i >= 0) {
			if(grid[r+i][c-i] == getColor(turn)) leftDown++;
			else break;
			i++;
		}

		i=1;
		while(r-i >= 0 && c+i < 7) {
			if(grid[r-i][c+i] == getColor(turn)) rightUp++;
			else break;
			i++;
		}

		i=1;
		while(r-i >= 0 && c-i >= 0) {
			if(grid[r-i][c-i] == getColor(turn)) leftUp++;
			else break;
			i++;
		}

		if(up+down >=3 || left+right >= 3 || rightDown+leftUp >= 3 || rightUp+leftDown >= 3) {
			winner = true;
		}

	}

	//Overridden methods
	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	//Function to identify which column the mouse clicked in
	public int identifyCol(int x) {
		return (int)((x-333)/CELL_SIZE);
	}

	//Function to identify which row the mouse clicked in
	public int identifyRow(int y) {
		return (int)(y/CELL_SIZE);
	}

	//Function to check if the entire board is filled in
	public boolean boardFilled() {
		for(int i = 0; i < 7; i++) {
			if(!filled[0][i]) return false;
		}
		return true;
	}
}