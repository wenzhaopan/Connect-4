
/* GameFrame class establishes the frame (window) for the game
Extends JFrame because JFrame manages frames
Runs the constructor in  GamePanelAI class
 */ 

import java.awt.*;
import javax.swing.*;

public class GameFrameAI extends JFrame{

	public static GamePanelAI panel;

	public GameFrameAI(){
		this.setSize(1225, 630);
		this.setTitle("Connect 4 (Player vs AI)"); //set title for frame
		this.setBackground(Color.white); //set background colour for frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
		this.setPreferredSize(getSize());//makes components fit in window
    this.setLocationRelativeTo(null);
		panel = new GamePanelAI(getSize()); //runs GamePanelAI constructor
		this.add(panel);
		this.pack();
		this.setVisible(true); //makes window visible to user
	} 
}
