package ClimbStairs;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StairsEx extends JFrame{
	
	ImageIcon brickImage = new ImageIcon("images/brick.png");
	JLabel[] brickLabels = new JLabel[100];
	boolean[] bricks = new boolean [100];
	private boolean isLeft = true;
	JLabel character = new JLabel();
	ImageIcon notRyoSkin = new ImageIcon("images/not_ryo.png");
	
	JLabel climbTime = new JLabel();
	JPanel c = new JPanel();
	RetryScreen retryScreen = new RetryScreen();
	
	long startSecond = 0;
	long endSecond = 0;
	
	public StairsEx() {
		setTitle("To_the_infinity_and_Beyond!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		c.setLayout(null);
		
		character.setIcon(notRyoSkin);
		character.setBounds(290, 500, 120, 210);
		c.add(character);
		
		brickLabels[0] = new JLabel();
		brickLabels[0].setBounds(300, 700, 100, 40);
		brickLabels[0].setIcon(brickImage);
		c.add(brickLabels[0]);
		for(int i = 0; i < 100; i++)
			brickLabels[i] = new JLabel();
		setBricks();
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)(screen.getWidth() / 2);
		setLocation(screenWidth - 325, 0);
		
		c.addKeyListener(new GameControl());
		character.addKeyListener(new SkinControl());
		retryScreen.addKeyListener(new Retry());
		
		setSize(650, 800);
		c.setBounds(0,0,650,800);
		add(c);
		
		retryScreen.setBounds(94, 90, 450, 600);
		retryScreen.setBackground(Color.cyan);
		add(retryScreen);
		retryScreen.setVisible(true);
		
		setVisible(true);
		c.requestFocus();
	}
	
	
	class GameControl extends KeyAdapter{
		int brickCount = 1;
		boolean onGaming = false;
		ImageIcon blueCharSkin = new ImageIcon("images/blue_nemo.png");
		@Override
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
					onGaming = true;
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
						onGaming = false;
						endSecond = System.currentTimeMillis() / 1000;
						climbTime.setText(Long.toString(endSecond - startSecond));
						brickCount = 1;
						c.setVisible(false);
						retryScreen.setVisible(true);
						retryScreen.requestFocus();
					}
					break;
				case KeyEvent.VK_SPACE:
					if(onGaming = false) {
						character.setIcon(blueCharSkin);
						character.requestFocus();
					}
					break;
				}
			}
		}
	
	class SkinControl extends KeyAdapter{
		ImageIcon greenCharSkin = new ImageIcon("images/green_nemo.png");
		@Override
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
	
	void setBricks() {
		brickLabels[0].setBounds(300, 700, 100, 40);
		Random r = new Random();
		for(int n = 0; n < 100; n++) {
			bricks[n] = r.nextBoolean();
		}
		for(int i = 1; i < 100; i++) {
			if(bricks[i] == true)
				brickLabels[i].setBounds(brickLabels[i-1].getX() - 100, brickLabels[i-1].getY() - 40, 100, 40);
			else
				brickLabels[i].setBounds(brickLabels[i-1].getX() + 100, brickLabels[i-1].getY() - 40, 100, 40);
			brickLabels[i].setIcon(brickImage);
			c.add(brickLabels[i]);
		}
	}
	
	class RetryScreen extends JPanel{
		public RetryScreen() {
			setLayout(null);
			JLabel title = new JLabel("TIME");
			add(title);
			title.setHorizontalAlignment(JLabel.CENTER);
			title.setBounds(0, 0, 450, 190);
			title.setFont(new Font("Gothic",Font.CENTER_BASELINE, 150));
			
			add(climbTime);
			climbTime.setHorizontalAlignment(JLabel.CENTER);
			climbTime.setBounds(0, 190, 450, 80);
			climbTime.setFont(new Font("Gothic",Font.CENTER_BASELINE, 80));
			climbTime.setText(Long.toString(endSecond - startSecond));
			long timeScore = endSecond - startSecond;
			
			long ranking1 = 0;
			long ranking2 = 0;
			long ranking3 = 0;
			
			FileInputStream fstream = null;
			try {
				File readFile = new File("files/ranking.txt");
				FileReader filereader = new FileReader(readFile);
				BufferedReader bufReader = new BufferedReader(filereader);
				ranking1 = Long.valueOf(bufReader.readLine());
				ranking2 = Long.valueOf(bufReader.readLine());
				ranking3 = Long.valueOf(bufReader.readLine());
				bufReader.close();
			} catch(IOException e){
				e.printStackTrace();
			}
			
			if(timeScore < ranking1) {
				ranking3 = ranking2;
				ranking2 = ranking1;
				ranking1 = timeScore;
			}
			else if(timeScore < ranking2) {
				ranking3 = ranking2;
				ranking2 = timeScore;
			}
			else if(timeScore < ranking3) {
				ranking3 = timeScore;
			}
			
			JLabel rank1 = new JLabel("1st is " + (ranking1) + "sec");
			add(rank1);
			rank1.setHorizontalAlignment(JLabel.CENTER);
			rank1.setBounds(0, 270, 450, 80);
			rank1.setFont(new Font("Gothic",Font.CENTER_BASELINE, 60));
			
			JLabel rank2 = new JLabel("2nd is " + (ranking2) + "sec");
			add(rank2);
			rank2.setHorizontalAlignment(JLabel.CENTER);
			rank2.setBounds(0, 350, 450, 60);
			rank2.setFont(new Font("Gothic",Font.CENTER_BASELINE, 60));
			
			JLabel rank3 = new JLabel("3rd is " + (ranking3) + "sec");
			add(rank3);
			rank3.setHorizontalAlignment(JLabel.CENTER);
			rank3.setBounds(0, 410, 450, 80);
			rank3.setFont(new Font("Gothic",Font.CENTER_BASELINE, 60));
			
			JButton retry = new JButton("Retry with F");
			add(retry);
			retry.setHorizontalAlignment(JButton.CENTER);
			retry.setBounds(25, 520, 400, 80);
			retry.setFont(new Font("Gothic",Font.CENTER_BASELINE, 60));
			
			try {
				File writeFile = new File("files/ranking.txt");
				if(!writeFile.exists())
					writeFile.createNewFile();
				FileWriter fw = new FileWriter(writeFile);
				BufferedWriter writer = new BufferedWriter(fw);
				writer.write(ranking1 + "\n");
				writer.write(ranking2 + "\n");
				writer.write(ranking3 + "\n");
				writer.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	class Retry extends KeyAdapter{
			@Override
			public void keyPressed(KeyEvent e) {
				int skinControlKeyCode = e.getKeyCode();
				switch(skinControlKeyCode) {
				case KeyEvent.VK_F:
					retryScreen.setVisible(false);
					c.setVisible(true);
					setBricks();
					c.requestFocus();
					break;
				}
			}
		}

	public static void main(String[] args) {
		new StairsEx();
	}
}