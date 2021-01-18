package msu.ivan.rain.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import msu.ivan.rain.entity.mob.Shooter;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
	}

	@Override
	protected void loadLevel(String path) {
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(path));
			width = bufferedImage.getWidth();
			height = bufferedImage.getHeight();
			tiles = new int[width * height];
			bufferedImage.getRGB(0, 0, width, height, tiles, 0, width);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("msu.ivan.rain.level::SpawnLevel::loadLevel()");
		}
		for (int i = 0; i < 1; ++i)
			add(new Shooter(19, 62));
	}

	@Override
	// need to convert the array of levelPiexls into array of tiles;
	// Grass = 0xFF00FF00
	// Flower = 0xFFFFFF00
	// Rock = 0xFF7F7F00
	protected void generateLevel() {
	}

}
