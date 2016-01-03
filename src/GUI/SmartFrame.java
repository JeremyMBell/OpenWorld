package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

public class SmartFrame extends JFrame {
	List<Drawable> painters;
	Dimension resolution;
	public SmartFrame(String name, Dimension res) {
		super(name);
		resolution = res;
		painters = new LinkedList<>();
	}
	public void addPainter(Drawable painter) {
		painters.add(painter);
	}
	public void addAllPainters(Collection<? extends Drawable> c) {
		painters.addAll(c);
	}
	public void paint(Graphics g) {
		Dimension frameSize = this.getSize();
		Dimension scale = new Dimension(frameSize.width/resolution.width, frameSize.height/resolution.height);
		Color clr = g.getColor();
		g.setColor(getBackground());
		g.fillRect(0, 0, frameSize.width, frameSize.height);
		g.translate(0, 39);
		for(Drawable painter:painters) {
			painter.draw(g, scale);
			g.translate(400, 0);
		}
		g.setColor(clr);
		g.translate(-100 * painters.size(), -39);
	}
}
