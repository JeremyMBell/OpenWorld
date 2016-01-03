package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {
	private SmartFrame container;
	private Graphics g;
	private static Dimension WINDOW_SIZE = new Dimension(800, 800);
	private Dimension resolution;
	public static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public Game(String title) {
		this.container = new SmartFrame(title, new Dimension(50,50));
		//If goes beyond bounds, resize.
		if (SCREEN_SIZE.getHeight() < WINDOW_SIZE.getHeight() + 60 || SCREEN_SIZE.getWidth() < WINDOW_SIZE.getWidth()) {
			double ratio =  1;
			
			//Resize according to height
			if (SCREEN_SIZE.getHeight() < SCREEN_SIZE.getWidth()) {
				ratio = (SCREEN_SIZE.getHeight() - 60)/ ((double) WINDOW_SIZE.getHeight());
			} 
			//Resize according to width
			else {
				ratio = SCREEN_SIZE.getWidth()/((double) WINDOW_SIZE.getWidth());
			}
			this.resolution = new Dimension((int)Math.round(WINDOW_SIZE.getWidth()*ratio), (int)Math.round(WINDOW_SIZE.getHeight()*ratio));
			this.container.setPreferredSize(this.resolution);
		}
		//Most cases
		else {
			this.resolution = WINDOW_SIZE;
			this.container.setPreferredSize(WINDOW_SIZE);
		}
		this.container.setResizable(false);
		this.container.pack();
		this.container.setVisible(true);
	}
	public Graphics getGraphics() {
		return this.container.getGraphics();
	}
	public Game() {
		this("Game");
	}
	public void setResolution(Dimension newRes) {
		resolution = newRes;
	}
	public static void main(String[] args) {
		Game hi = new Game();
		Color t = new Color(0,0,0,0);
		Color o = Color.ORANGE;
		Color b = Color.BLACK;
		Color p = Color.PINK;
		Color w = Color.WHITE;
		Color[][] fox = 
			{
				 {t,t,t,t,t,b,t,t,t,t,t,t,t,t,b,t,t,t,t,t},
				 {t,t,t,t,b,o,b,t,t,t,t,t,t,b,o,b,t,t,t,t},
				 {t,t,t,t,b,o,o,b,t,t,t,t,b,o,o,b,t,t,t,t},
				 {t,t,t,b,o,p,o,o,b,t,t,b,o,o,p,o,b,t,t,t},
				 {t,t,t,b,p,p,p,o,o,b,b,o,o,p,p,p,b,t,t,t},
				 {t,t,b,o,o,o,o,o,o,o,o,o,o,o,o,o,o,b,t,t},
				 {t,t,b,o,o,b,b,b,o,o,o,o,b,b,b,o,o,b,t,t},
				 {t,b,o,o,o,o,o,o,o,o,o,o,o,o,o,o,o,o,b,t},
				 {t,b,o,o,o,w,w,w,o,o,o,o,w,w,w,o,o,o,b,t},
				 {b,o,o,o,o,w,b,w,o,o,o,o,w,b,w,o,o,o,o,b},
				 {t,b,o,o,o,o,o,o,o,o,o,o,o,o,o,o,o,o,b,t},
				 {t,t,b,o,o,o,o,b,b,o,o,b,b,o,o,o,o,b,t,t},
				 {t,t,t,b,o,o,b,o,o,b,b,o,o,b,o,o,b,t,t,t},
				 {t,t,t,t,b,o,o,o,o,o,o,o,o,o,o,b,t,t,t,t},
				 {t,t,t,t,t,b,o,o,b,o,o,b,o,o,b,t,t,t,t,t},
				 {t,t,t,t,t,t,b,o,o,b,b,o,o,b,t,t,t,t,t,t},
				 {t,t,t,t,t,t,t,b,o,o,o,o,b,t,t,t,t,t,t,t},
				 {t,t,t,t,t,t,t,t,b,o,o,b,t,t,t,t,t,t,t,t},
				 {t,t,t,t,t,t,t,t,t,b,b,t,t,t,t,t,t,t,t,t}
			};
		Sprite foxy = new Sprite("Foxy", fox);
		hi.container.addPainter(foxy);
	}

}
