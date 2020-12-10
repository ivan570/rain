package msu.ivan.rain.level;

import java.util.Random;

public class RandomLevel extends Level {

	private static final Random random = new Random();

	public RandomLevel(int width, int height) {
		super(width, height);
	}

	// set tiles array with random value
	// if tile value is 0 then it mean it tile is for grass
	// and else it's not define yet but we will do soon.
	// we use it in Level class and getTile method
	@Override
	protected void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tilesInt[x + y * width] = random.nextInt(4); // it will generate the 0, 1, 2, 3 value randomly  
			}
		}
	}

}
