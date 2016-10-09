package com.langley.matrix.display;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Display extends JFrame implements MouseMotionListener, KeyListener{
	public static Display display;
	public static JPanel totalGUI;
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static boolean running = false;
	public static Random random = new Random();
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static boolean closeable = false;

	public static ArrayList<int[]> nums = new ArrayList<int[]>();

	// each num: x,y,vel,color

	public static void main(String[] args) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, WIDTH, HEIGHT);
		Color[] colors = new Color[] { Color.BLACK, Color.GREEN };
		display = new Display();
		start();
		// gameloop
		int count = 0;
		while (running) {
			display.setLocation(0, 0);
			for (int i = 0; i < random.nextInt(1) + 1; i++) {
				nums.add(new int[] { nearest(random.nextInt(WIDTH), 12), 0, random.nextInt(4) + 2, colors[random.nextInt(2)].getRGB() });
			}

			for (int[] num : nums) {
				num[1] += num[2];
			}
			ArrayList<int[]> temp = new ArrayList<int[]>();
			for (int[] num : nums) {
				if (!(num[1] >= HEIGHT)) {
					temp.add(num);
				}
			}
			nums = temp;

			if (count % 3 == 0) {
				// drawing
				g2d.setFont(new Font("Courier New", Font.BOLD, 20));
				for (int[] num : Display.nums) {
					g2d.setColor(new Color(num[3]));
					g2d.drawString("" + random.nextInt(10), num[0], num[1]);
				}
				display.repaint();
			}

			try {
				Thread.sleep(15);
				count++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static void start() {
		if (!running) {
			running = true;
		}
	}

	public static void stop() {
		if (running) {
			running = false;
		}
	}

	public Display() {
		super("Matrix");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocation(0, 0);
		setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setUndecorated(true);
		setVisible(true);
		setAlwaysOnTop(true);
		addMouseMotionListener(this);
		addKeyListener(this);

		totalGUI = new JPanel();
		totalGUI.setLocation(0, 0);
		totalGUI.setSize(WIDTH, HEIGHT);
		totalGUI.setOpaque(false);
		totalGUI.setVisible(true);
		totalGUI.setLayout(null);
		add(totalGUI);

		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(new ImageIcon(image));
		imageLabel.setSize(WIDTH, HEIGHT);
		imageLabel.setLocation(0, 0);
		imageLabel.setOpaque(true);
		totalGUI.add(imageLabel);

		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		getContentPane().setCursor(blankCursor);

	}

	public static int nearest(int original, int toWhat) {
		return Math.round(original / toWhat) * toWhat;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (closeable) {
			display.dispose();
			stop();
		} else {
			closeable = true;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		display.dispose();
		stop();
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
