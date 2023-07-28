
//Main menu of game with 3 options 
//PVP, PVE, quit
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Main extends JFrame implements MouseListener{

	private Image backgroundImage;
	public static GameFrame gameframe;
	public static GameFrameAI gameframeai;

	//constructor creates the frame and sets up all the text and text boxes
	public Main(){
		//setup frame
		this.setTitle("Connect 4");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(1225, 630);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		addMouseListener(this);
		
		//Sets the background image
		try {
			backgroundImage = ImageIO.read(new File("BackgroundImage.jpg"));
		}
		catch(IOException e) {
			System.out.println("error.");
		}

	}
	
	//Paint function to display everything
	public void paint(Graphics g) {

		//background image
		g.drawImage(backgroundImage,  0, 0, GameFrame.WIDTH, GameFrame.LENGTH, this);
		
		Font font1 = new Font("arial", Font.BOLD, 50);
		Font font2 = new Font("arial", Font.BOLD, 15);
		g.setFont(font1);
		g.setColor(Color.black);
		g.drawString("Connect Four", GameFrame.WIDTH/2-170, 75);

		
		//Draws all of the rectangular buttons and the text fields inside of them
		g.setFont(font2);
		g.setColor(Color.black);
		g.drawString("Player vs Player", GameFrame.WIDTH/2 - 60, 155);
		g.drawRect(GameFrame.WIDTH/2 - 75, 125, 150, 50);
		g.drawString("Player vs Computer", GameFrame.WIDTH/2 - 69, 255);
		g.drawRect(GameFrame.WIDTH/2 - 75, 225, 150, 50);
		g.drawString("Quit", GameFrame.WIDTH/2-20, 355);
		g.drawRect(GameFrame.WIDTH/2 - 75, 325, 150, 50);

	}
	//method from ActionListener interface
	//actionPerformed is automatically called whenever it needs to be by actionListener - never call it yourself
	public void mousePressed(MouseEvent e){

	}

	@Override
	//Checks to see where the mouse clicked, and which option the mouse has clicked
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(x >= GameFrame.WIDTH/2-50 && x <= GameFrame.WIDTH/2+150 && y >= 125 && y <= 175) {
			dispose();
			gameframe = new GameFrame();
		}else if(x >= GameFrame.WIDTH/2-50 && x <= GameFrame.WIDTH/2+150 && y >= 225 && y <= 275) {
			dispose();
			gameframeai = new GameFrameAI();
		}else if(x >= GameFrame.WIDTH/2-50 && x <= GameFrame.WIDTH/2+150 && y >= 325 && y <= 375) {
			dispose();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	//main method runs the constructor, does nothing else
	public static void main(String[] args) {
		Main main = new Main(); //runs the constructor
	}
}
