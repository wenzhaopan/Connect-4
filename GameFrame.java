
/* GameFrame class establishes the frame (window) for the game
Extends JFrame because JFrame manages frames
Runs the constructor in  GamePanel class
 */ 
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
	//Dimensions for the frame
	public static GamePanel panel;
	public static final int WIDTH = 1225;
	public static final int LENGTH = 630;

	public GameFrame(){
		this.setSize(WIDTH, LENGTH);		
		this.setTitle("Connect 4 (Player vs Player)"); //set title for frame
		this.setBackground(Color.white); //set background colour for frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
		this.setLocationRelativeTo(null);
		this.setPreferredSize(getSize());//makes components fit in window
		panel = new GamePanel(getSize()); //runs GamePanel constructor
		this.add(panel);
		this.pack();
		this.setVisible(true); //makes window visible to user
	}
  
}
