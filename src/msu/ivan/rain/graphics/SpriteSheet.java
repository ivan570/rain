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
	public final int SIZE, HEIGHT, WIDTH;
	public int[] pixels;
	private Sprite[] sprites;

	// create the SpriteSheet object of are sprite-sheet file which we created
	public static SpriteSheet tiles = new SpriteSheet("res/textures/sheets/spritesheet.png", 256);
	public static SpriteSheet spawn_level = new SpriteSheet("res/textures/sheets/spawn_level.png", 48);
	public static SpriteSheet projectile_wizard = new SpriteSheet("res/textures/sheets/projectiles/wizard.png", 48);

	// player sprite-sheet
	public static SpriteSheet player = new SpriteSheet("res/textures/sheets/player_sheet.png", 128, 96);
	public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 1, 3, 32);

	public SpriteSheet(String path, int SIZE) {
		this.path = path;
		this.SIZE = HEIGHT = WIDTH = SIZE;
		this.pixels = new int[this.SIZE * this.SIZE];

		load(this.path);
	}

	public SpriteSheet(String path, int width, int height) {
		this.SIZE = -1;
		this.path = path;
		this.HEIGHT = height;
		this.WIDTH = width;
		this.pixels = new int[this.WIDTH * this.HEIGHT];

		load(this.path);
	}

	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int $x = x * spriteSize;
		int $y = y * spriteSize;
		int $width = width * spriteSize;
		int $height = height * spriteSize;
		this.WIDTH = $width;
		this.HEIGHT = $height;
		if (this.WIDTH == this.HEIGHT)
			this.SIZE = this.HEIGHT;
		else
			this.SIZE = -1;

		pixels = new int[$width * $height];

		for (int tempY = 0; tempY < $height; tempY++) {
			int yPosition = $y + tempY;
			for (int tempX = 0; tempX < $width; tempX++) {
				int xPosition = $x + tempX;
				pixels[tempX + tempY * $width] = sheet.pixels[xPosition + yPosition * sheet.WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixel = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixel[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * width];
					}
				}
				Sprite sprite = new Sprite(spritePixel, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}

	}

	public Sprite[] getSprite() {
		return sprites;
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
