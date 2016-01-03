package GUI;

import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Sprite implements Drawable {
	private String name;
	private Palette palette;
	public Sprite(String name, Color[][] colors) {
		this.name = name;
		this.palette = new Palette(colors);
	}
	
	
	/**
	 * @return Dimension that the sprite gives
	 */
	public Dimension getDimension() {
		return this.palette.getDimension();
	}
	/**
	 * @see Sprite#toString
	 */
	public String getName() {return this.name;}
	/**
	 * @return Name of the sprite.
	 */
	public String toString() {return this.name;}
	public static List<Sprite> readSpritesFromText(String text) {
		String[] sprites = text.split("-\n");//Sprites separated by a line of "-"
		List<Sprite> spriteList = new LinkedList<>();
		
		//Iterate through all sprites encoded
		for (String sprite:sprites) {
			
			//3 lines: one with the name of sprite, one with dimensions, and one with colors
			String[] spriteAttributes = sprite.split("\n");
			
			//If no three lines, sprite isn't valid, move on.
			if (spriteAttributes.length == 3) {
				//e.g. 1024x768 is 1024 wide and 768 height
				String[] dimensions = spriteAttributes[1].split("x");
				//Encoded RedxBluexGreen,RedxBluexGreen,...
				String[] colors = spriteAttributes[2].split(",");
				if (dimensions.length < 2) continue;//If a width and height not given, sprite isn't valid, move on
				Dimension size;
				try {//Reference dimension
					size = new Dimension(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]));
				} catch(Exception e) {//If numbers weren't given in one of the dimensions, the sprite isn't valid
					continue;
				}
				//If less colors given than dimensions allotted for, move on.
				if (colors.length < size.getHeight()*size.getWidth()) continue;
				Color[][] sortedColors = new Color[(int) size.getHeight()][(int) size.getWidth()];
				for(int i = 0; i < size.getHeight(); i++) {
					for (int j = 0; j < size.getWidth(); j++) {
						String[] color = colors[(int) size.getWidth() * i + j].split("x");//Color: RxBxG
						int[] rgb = new int[3];
						boolean done = false;
						switch(color.length) {//Handles null values
							case 2://One blank value
								rgb[2] = 0;
								break;
							case 1://Two blank values
								rgb[2] = 0;
								rgb[1] = 0;
								break;
							case 0://Transparent color
								sortedColors[i][j] = new Color(0,0,0,0);
								done = true;//Color established
								break;
						}
						if (done)continue;//Only in the case of a transparent color
						for (int k = 0; k < color.length && k < 3; k++) {
							try {
								rgb[k] = Integer.parseInt(color[k]);								
							} catch (Exception e) {//If nonnumber given, set number = 0
								rgb[k] = 0;
							}
						}
						sortedColors[i][j] = new Color(rgb[0], rgb[1], rgb[2]);
					}
				}
				spriteList.add(new Sprite(spriteAttributes[0], sortedColors));
			}
		}
		return spriteList;
	}
	public static List<Sprite> readSpritesFromFile(String filename) {
		File file = new File(filename);
		if (file.exists() && file.canRead()) {
			try {
				FileReader fileReader = new FileReader(file);
				BufferedReader buffReader = new BufferedReader(fileReader);
				String page = "";
				String line = null;
				while ((line = buffReader.readLine()) != null) {
					page += line + "\n";
				}
				buffReader.close();
				return readSpritesFromText(page);
				
			}catch (IOException e) {
				System.out.println("Could not find file: " + filename);
				return null;
			}
			
		}
		else {
			return null;
		}
	}


	@Override
	public void draw(Graphics g, Dimension d) {
		this.palette.draw(g,d);
		
	}
}
