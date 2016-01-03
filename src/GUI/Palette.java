package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Palette implements Drawable {

	private HashMap<Color, List<Point>> clrRemap;
	private Dimension size;
	public Palette(Color[][] colorMap) {
		this.clrRemap = new HashMap<>();
		this.size = new Dimension(0, colorMap.length);
		//Transcribing into a more manageable mapping for a java.awt.Graphics object
		for (int y = 0; y < colorMap.length; y++) {
			if(this.size.width == 0)this.size.width = colorMap[y].length;
			for (int x = 0; x < colorMap[y].length; x++) {
				if(this.clrRemap.containsKey(colorMap[y][x])){
					clrRemap.get(colorMap[y][x]).add(new Point(x, y));
				} else {
					List<Point> newList = new LinkedList<>();
					newList.add(new Point(x,y));
					clrRemap.put(colorMap[y][x], newList);
				}
			}
		}
	}
	@Override
	public void draw(Graphics g, Dimension d) {
		Set<Color> colors = clrRemap.keySet();
		System.out.println(colors.size());
		Color beforeColor = g.getColor();
		int xR = d.width;
		int yR = d.height;
		for(Color color: colors) {
			g.setColor(color);
			System.out.println("Color:" + color);
			for(Point p:this.clrRemap.get(color)) {
				System.out.println(p);
				g.fillRect(p.x*xR, p.y*yR, xR, yR);
			}
		}
		System.out.println("Done");
		g.setColor(beforeColor);
	}
	@Override
	public Dimension getDimension() {
		return this.size;
	}

}
