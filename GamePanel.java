/* 
Wenzhao Pan and Joshua Choi
June 20, 2022
ICS Culminating
 */

//GamePanel is the main game itself
//This is the game panel for the PVP game mode, and uses the respective power ups

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel  implements MouseListener{

	public int X;
	public int Y;
	public static final int CELL_SIZE = 80;
	public static int turn = 2;
	public static int rows = 6;
	public static int cols = 7;
	public String colour = "";
	public boolean winner;
	public String s = "";
	public static boolean[][] filled = new boolean[6][7];
	public static Color[][] grid = new Color[rows][cols];
	public SkipTurn skipTurn;
	public ExtraPieces extraPieces;


	//Constructor for the game 
	public GamePanel(Dimension dimension) {
		//Sets everything to default values so the game can be played multiple times without closing the window
		//Also initializes the array and fills it with white
		turn = 2;
		winner = false;
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				filled[i][j] = false;
				grid[i][j] = Color.white;
			}
		}

		//Generates random numbers for the power up spawn locations
		int x = (int)Math.floor(Math.random()*7);
		int x1 = (int)Math.floor(Math.random()*5+1);
		while(x == x1 || x == x1+1 || x == x1-1) x = (int)Math.floor(Math.random()*5);


		setSize(dimension);
		setPreferredSize(dimension);
		addMouseListener(this);
		//Spawns power ups in
		skipTurn = new SkipTurn(x, (int)Math.floor(Math.random()*6));
		extraPieces = new ExtraPieces(x1, (int)Math.floor(Math.random()*5));
	}

	//Overridden paint function that draws/updates everything shown on the screen
	public void paintComponent(Graphics g) {

		Graphics2D g1 = (Graphics2D)g;
		g1.setColor(Color.black);
		g1.fillRect(333,0,CELL_SIZE*7 - 10, CELL_SIZE*6 - 10);
		X = 333;
		Y = 0;


		//Draws the rectangular "main menu" button
		g.drawRect(10, 10, 75, 50);
		g.drawString("Main Menu", 17, 37);

		//Draws the rectangular "restart" button
		g.drawRect(10, 80, 75, 50);
		g.drawString("Restart", 25, 110);

		// draws grid and sets background to black
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				g1.setColor(grid[row][col]);
				g1.fillOval(X,Y,CELL_SIZE - 10,CELL_SIZE-10);
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
			g1.drawRect(900,  0,  200,  360);
			g1.fillRect(900,  0,  200,  360);
			g1.setColor(Color.blue);
			s = "Winner: " + getTurn(turn-1);
			g1.drawString(s, 900, 20);
		}
		//Writes "Tie!" if the board is completely filled without a winner
		else if(boardFilled()) {
			g1.setColor(Color.white);
			g1.drawRect(900,  0,  200,  360);
			g1.fillRect(900,  0,  200,  360);
			g1.setColor(Color.blue);
			s = "Tie!";
			g1.drawString(s, 900, 20);
		}

		//Writes who's turn it is if there is no winner
		else {
			g1.setColor(Color.white);
			g1.drawRect(900,  0,  200,  360);
			g1.fillRect(900,  0,  200,  360);
			g1.setColor(Color.blue);
			s = "Turn: " + getTurn(turn);
			g1.drawString(s, 900, 20);
		}

	}


	//Tells us who's turn it is
	public String getTurn(int n) {
		if(n%2 == 1) return "Yellow";
		else return "Red";
	}

	//Tells us who's turn it is (which colour)
	public Color getColor(int n){
		if(n%2 == 0) return Color.red;
		else return Color.yellow;
	}

	//Repaints and updates everything after a mouse is clicked inside the board
	public void mousePressed(MouseEvent e) {
		int x = identifyCol(e.getX());
		int y = identifyRow(e.getY());

		//If game hasn't ended, fills in the game piece for the turn
		if(x<7 && y<6 && !winner && !boardFilled() && x >= 0 && y >= 0 && !filled[0][x] && e.getX() >= 333 && e.getX() < 883) {
			fillTurn(x, y, 5);
			turn++;
			repaint();
		}

		//If user clicks the main menu button, disposes the current window and makes a new main menu
		else if(e.getX() > 10 && e.getX() < 85 && e.getY() > 10 && e.getY() < 60) {
			this.setVisible(false);
			Main.gameframe.dispose();
			new Main();
		}

		// if user clicks the restart button, the board resets
		else if(e.getX() > 10 && e.getX() < 85 && e.getY() > 80 && e.getY() < 130) {
			reset();
			repaint();
		}

	}

	// resets the board
	public void reset() {
		turn = 2;
		winner = false;
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				filled[i][j] = false;
				grid[i][j] = Color.white;
			}
		}

		int x = (int)Math.floor(Math.random()*7);
		int x1 = (int)Math.floor(Math.random()*5+1);
		while(x == x1 || x == x1+1 || x == x1-1) x = (int)Math.floor(Math.random()*5);

		skipTurn = new SkipTurn(x, (int)Math.floor(Math.random()*6));
		extraPieces = new ExtraPieces(x1, (int)Math.floor(Math.random()*5));
	}



	//Method to actually do the action of filling in each turn
	public void fillTurn(int x, int y, int cnt) {
		//If mouse click is valid, fills in the game piece for the turn
		if(y < 6 && x < 7 && !filled[0][x]) {
			while(true){
				if(!filled[cnt][x]) break;
				else cnt--;
			}
			grid[cnt][x] = getColor(turn);
			filled[cnt][x] = true;
			checkWinner(cnt, x);


			//Activates SkipTurn power up
			if(x == skipTurn.X && cnt == skipTurn.Y && !winner) skipTurn.activate();

			//Activates ExtraPieces power up
			if(x == extraPieces.X && cnt == extraPieces.Y && !winner) extraPieces.activate();
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

		if(up+down >=3 || left+right >= 3 || rightDown+leftUp >= 3 || rightUp+leftDown >= 3) 
			winner = true;

	}

	//Overridden functions
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
		return (int)((x - 333)/CELL_SIZE);
	}

	//Function to identify which row the mouse clicked in
	public int identifyRow(int y) {
		return (int)(y/CELL_SIZE);
	}

	//Identifies whether or not the entire board is filled
	public boolean boardFilled() {
		for(int i = 0; i < 7; i++) {
			if(!filled[0][i]) return false;
		}
		return true;
	}
}
