package GUI;

import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Sprite implements Drawable {
	private String name;
	private Palette palette;
	private static final Color TRANSPARENT = new Color(0,0,0,0);
	public Sprite(String name, Color[][] colors) {
		this.name = name;
		this.palette = new Palette(colors);
	}
	/**
	 * 
	 * @return The sprite in a serialized form.
	 */
	public String toString() {
		Dimension size = palette.getDimension();
		String s = "";
		for(Color[] row:palette.toBitmap()) {
			boolean trans = true;
			String rowString = "";
			for (int i = row.length - 1; i >= 0; i--) {
				String colorString = "";
				if(trans && row[i].getAlpha() > 0) {
					trans = false;
				} else if (trans) {
					continue;
				}
				if (row[i].getAlpha() > 0) {
					colorString = row[i].getRed() + "x" + row[i].getGreen() + "x" + row[i].getBlue();
				}
				colorString += ",";
				rowString = colorString + rowString;
			}
			s += rowString + "\t";
		}
		return this.name + "\n" + s;
	}
	public void toFile(String location) {
		File dir = new File(location);
		if(!dir.exists()){dir.mkdir();}
		System.out.println(dir.getAbsolutePath());
		File file = new File(dir.getAbsolutePath() + "/" + this.name + ".sprite");
		FileWriter write;
		try {
			write = new FileWriter(file, false);
			write.write(this.toString());
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Sorry, we failed to write \"" + this.name + "\" to location: " + location + ".sprite");
		}
	}
	/**
	 * @return Dimension that the sprite gives
	 */
	public Dimension getDimension() {
		return this.palette.getDimension();
	}

	/**
	 * @return Name of the sprite.
	 */
	public String getName() {return this.name;}
	public static List<Sprite> readSpritesFromText(String text) {
		String[] sprites = text.split("-\n");//Sprites separated by a line of "-"
		List<Sprite> spriteList = new LinkedList<>();
		
		//Iterate through all sprites encoded
		for (String sprite:sprites) {
			
			//3 lines: one with the name of sprite, one with dimensions, and one with colors
			String[] spriteAttributes = sprite.split("\n");
			
			//If no three lines, sprite isn't valid, move on.
			if (spriteAttributes.length == 2) {
				//Encoded RedxBluexGreen,RedxBluexGreen,...
				String[] rows = spriteAttributes[1].split("\t");
				int largestSize = 0;
				List<String[]> rowsList = new LinkedList<>();
				for(int i = 0; i < rows.length; i++) {
					String[] row = rows[i].split(",");
					if (row.length > largestSize) {
						largestSize = row.length;
					}
					rowsList.add(row);
				}
				Color[][] sortedColors = new Color[rows.length][largestSize];
				for(int i = 0; i < sortedColors.length; i++) {
					for (int j = 0; j < sortedColors[i].length; j++) {
						String[] color = {};//Color: RxBxG
						if(j < rowsList.get(i).length) {
							color = rowsList.get(i)[j].split("x");
						}
						int[] rgb = new int[3];
						boolean done = false;
						switch(color.length) {//Handles null values
							case 2://One blank value
								rgb[2] = 0;
								break;
							case 1:
								if (color[0].length()> 0) {//Two blank values
									rgb[2] = 0;
									rgb[1] = 0;
								} else {//Transparent color
									sortedColors[i][j] = new Color(0,0,0,0);
									done = true;//Color established
								}
								break;
							case 0://Assume transparent
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
		System.out.println(spriteList.size());
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
