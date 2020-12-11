package msu.ivan.rain.graphics;

// Sprite class load the single sprite at time
// to do so, Sprite constructor receive the size of sprite the x point and the y point of the sprite not pixels and
// we the object of SpriteSheet in SpriteSheet constructor to know the current sprite is belong to this specific SpriteSheet  
// load simply load the sprite using SpriteSheet object pixels array
public class Sprite {

	public final int SIZE;
	private int width, height;

	private int x, y; // refer the pixels value of coordinate of sprite

	protected SpriteSheet spriteSheet; // refer the specific SpriteSheet object
	public int[] pixels;

	// create the grass sprite the starting with (0, 0) and SIZE = 16
	// it will use [top left square] in are system sprite-sheet of size 16
	public static Sprite grass = new Sprite(16, 0, 6, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0x2F4858);

	// spawn level sprite here
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_hedge = new Sprite(16, 1, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_wall1 = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_wall2 = new Sprite(16, 0, 2, SpriteSheet.spawn_level);
	public static Sprite spawn_floor = new Sprite(16, 1, 1, SpriteSheet.spawn_level);

	// player sprite here
	public static Sprite player_forward = new Sprite(32, 0, 5, SpriteSheet.tiles);
	public static Sprite player_back = new Sprite(32, 2, 5, SpriteSheet.tiles);
	public static Sprite player_side = new Sprite(32, 1, 5, SpriteSheet.tiles);

	public static Sprite player_forward_1 = new Sprite(32, 0, 6, SpriteSheet.tiles);
	public static Sprite player_forward_2 = new Sprite(32, 0, 7, SpriteSheet.tiles);

	public static Sprite player_back_1 = new Sprite(32, 2, 6, SpriteSheet.tiles);
	public static Sprite player_back_2 = new Sprite(32, 2, 7, SpriteSheet.tiles);

	public static Sprite player_side_1 = new Sprite(32, 1, 6, SpriteSheet.tiles);
	public static Sprite player_side_2 = new Sprite(32, 1, 7, SpriteSheet.tiles);

	public static Sprite projectiles_wizard = new Sprite(16, 0, 0, SpriteSheet.projectile_wizard);

	// Particles
	public static Sprite particle_normal = new Sprite(3, 0x999999);
	// for create void tile to avoid NullPointerException

	protected Sprite(SpriteSheet sheet, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.height = height;
		this.width = width;
		this.spriteSheet = sheet;
	}

	public Sprite(int[] pixel, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.height = height;
		this.width = width;
		this.pixels = pixel;
	}

	public Sprite(int size, int color) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.height = this.width = size;
		setColor(color);
	}

	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.height = height;
		pixels = new int[width * height];
		this.width = width;
		setColor(color);
	}

	public Sprite(int size, int x, int y, SpriteSheet spriteSheet) {
		this.SIZE = size;
		this.x = x * this.SIZE;
		this.height = this.width = size;
		this.y = y * this.SIZE;
		this.spriteSheet = spriteSheet;
		this.pixels = new int[this.SIZE * this.SIZE];
		load();
	}

	public void setColor(int color) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				int $x = x + this.x, $y = y + this.y;
				pixels[x + y * SIZE] = spriteSheet.pixels[$x + $y * spriteSheet.SIZE];
			}
		}
	}

}
