package msu.ivan.rain.graphics;

// this is class to load the sprite-sheet from the are system.
// to do so we create the constructor for getting path of system sprite-sheet.
// and to load the image we create the load method
// load method load image pixel value into pixels array;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public int[] pixels;

	// create the SpriteSheet object of are sprite-sheet file which we created
	public static SpriteSheet tiles = new SpriteSheet("res/textures/sheets/spritesheet.png", 256);
	public static SpriteSheet spawn_level = new SpriteSheet("res/textures/sheets/spawn_level.png",48);
	public static SpriteSheet projectile_wizard = new SpriteSheet("res/textures/sheets/projectiles/wizard.png",48);
	
	public SpriteSheet(String path, int SIZE) {
		this.path = path;
		this.SIZE = SIZE;
		this.pixels = new int[this.SIZE * this.SIZE];

		load(this.path);
	}

	private void load(String path) {
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(path));
			int w = bufferedImage.getWidth();
			int h = bufferedImage.getHeight();
			bufferedImage.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			System.out.println("msu.ivan.rain.graphics::SpriteSheet::load()");
			e.printStackTrace();
		}
	}
}
