import java.awt.Container;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class StairsEx extends JFrame{
	ImageIcon brickImage = new ImageIcon("images/brick.png");
	private JLabel[] brickLabels = new JLabel[100];
	private boolean isLeft = true;
	boolean[] bricks = new boolean [100];
	
	JLabel character = new JLabel();
	ImageIcon redCharSkin = new ImageIcon("images/not_ryo.png");
	ImageIcon blueCharSkin = new ImageIcon("images/blue_nemo.png");
	ImageIcon greenCharSkin = new ImageIcon("images/green_nemo.png");
	
	JLabel Timer = new JLabel();
	
	Container c = getContentPane();
	
	public StairsEx() {
		setTitle("To_the_infinity_and_Beyond!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setLayout(null);
		
		JButton go = new JButton(new ImageIcon("images/start.png"));
		go.setBorderPainted(false);
		go.setFocusPainted(false);
		go.setContentAreaFilled(false);
		go.setLocation(0, 0);
		go.setSize(130, 80);
		ImageIcon rolloverIcon = new ImageIcon("images/start!.png");
		go.setPressedIcon(rolloverIcon);
		//c.add(go);
		
		long currentSecond = System.currentTimeMillis() / 1000 % 86400;
		long hour = (currentSecond /3600 + 33) % 24;
		long min = currentSecond % 3600 / 60;
		long sec = currentSecond % 3600 % 60;
		String currentTime = Long.toString(hour) + ' ' + Long.toString(min) + ' ' + Long.toString(sec);
		Timer.setText(currentTime);
		Timer.setBounds(0, 0, 650, 50);
		Timer.setHorizontalAlignment(JLabel.CENTER);
		Timer.setFont(new Font("Gothic",Font.CENTER_BASELINE, 50));
		c.add(Timer);
		
		
		character.setIcon(redCharSkin);
		character.setBounds(290, 500, 120, 210);
		c.add(character);
		
		
		brickLabels[0] = new JLabel();
		brickLabels[0].setBounds(300, 700, 100, 40);
		brickLabels[0].setIcon(brickImage);
		c.add(brickLabels[0]);
		
		c.addKeyListener(new GameControl());
		c.addFocusListener(new Retry());
		character.addFocusListener(new Retry());
		character.addKeyListener(new SkinControl());
		setSize(650, 800);
		setVisible(true);
		character.setFocusable(true);
		c.requestFocus();
	}
	
	class GameControl extends KeyAdapter{
		int brickCount = 1;
		long startSecond = 0;
		long endSecond = 0;
		public void keyPressed(KeyEvent e) {
			int gameControlKeyCode = e.getKeyCode();
			switch(gameControlKeyCode) {
				case KeyEvent.VK_F:
					if(isLeft == true) {
						isLeft = false;
						break;
					}
					else {
						isLeft = true;
						break;
					}
				case KeyEvent.VK_J:
					if(brickLabels[0].getY() == 700)
						startSecond = System.currentTimeMillis() / 1000;
					
					if(bricks[brickCount] == isLeft) {
						for(int i = 0; i < 100; i++) {
							if(bricks[brickCount] == true)
								brickLabels[i].setLocation(brickLabels[i].getX() + 100, brickLabels[i].getY() + 40);
							else
								brickLabels[i].setLocation(brickLabels[i].getX() - 100, brickLabels[i].getY() + 40);
						}
						brickCount++;
					}
					else 
						JOptionPane.showMessageDialog(null, "조심하세요", "Message", JOptionPane.ERROR_MESSAGE);
					
					if(brickLabels[99].getY() == 700) {
						endSecond = System.currentTimeMillis() / 1000;
						Timer.setText(Long.toString((endSecond - startSecond)) + "sec");
						Timer.requestFocus();
					}
					break;
				case KeyEvent.VK_SPACE:
					character.setIcon(blueCharSkin);
					character.requestFocus();
					break;
			}	
		}
	}
	
	class SkinControl extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int skinControlKeyCode = e.getKeyCode();
			switch(skinControlKeyCode) {
				case KeyEvent.VK_SPACE:
					character.setIcon(greenCharSkin);
					c.requestFocus();
					break;
			}
		}
	}
	
	class Retry extends FocusAdapter{
		public void focusLost(FocusEvent fe) {
			brickLabels[0].setBounds(300, 700, 100, 40);
			Random r = new Random();
			for(int n = 0; n < 100; n++) {
				bricks[n] = r.nextBoolean();
			}
			
			for(int i = 1; i < 100; i++) {
				brickLabels[i] = new JLabel();
				if(bricks[i] == true)
					brickLabels[i].setBounds(brickLabels[i-1].getX() - 100, brickLabels[i-1].getY() - 40, 100, 40);
				else
					brickLabels[i].setBounds(brickLabels[i-1].getX() + 100, brickLabels[i-1].getY() - 40, 100, 40);
				brickLabels[i].setIcon(brickImage);
				c.add(brickLabels[i]);
			}
		}
	}
	
	public static void main(String[] args) {
		new StairsEx();
	}
}
