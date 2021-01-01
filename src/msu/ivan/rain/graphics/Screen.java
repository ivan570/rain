package msu.ivan.rain.graphics;

import java.util.Random;

import msu.ivan.rain.entity.projectile.Projectile;
import msu.ivan.rain.level.tile.Tile;

public class Screen {

	public int width, height;
	private static final int SIZE = 64;

	public int xOfSet, yOfSet;
	private Random random = new Random();

	public int[] tiles = new int[SIZE * SIZE];
	public int[] pixels;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];

		for (int i = 0; i < SIZE * SIZE; ++i) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void setOfSet(int xOfSet, int yOfSet) {
		this.xOfSet = xOfSet;
		this.yOfSet = yOfSet;
	}

	public void renderSpriteSheet(int xPosition, int yPosition, SpriteSheet spriteSheet, boolean fixed) {
		if (fixed) {
			xPosition -= xOfSet;
			yPosition -= yOfSet;
		}
		for (int y = 0; y < spriteSheet.HEIGHT; y++) {
			int $y = y + yPosition;
			for (int x = 0; x < spriteSheet.WIDTH; x++) {
				int $x = x + xPosition;
				if ($x < 0 || $x >= width || $y < 0 || $y >= height)
					continue;
				pixels[$x + $y * width] = spriteSheet.pixels[x + y * spriteSheet.WIDTH];
			}
		}
	}

	public void renderSprite(int xPosition, int yPosition, Sprite sprite, boolean fixed) {
		if (fixed) {
			xPosition -= xOfSet;
			yPosition -= yOfSet;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int $y = y + yPosition;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int $x = x + xPosition;
				if ($x < 0 || $x >= width || $y < 0 || $y >= height)
					continue;
				pixels[$x + $y * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
	}

	public void renderTile(int xPosition, int yPosition, Tile tile) {
		// to make position to relative to the ofSet value
		// to move map into opposite direction.
		xPosition -= xOfSet;
		yPosition -= yOfSet;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int $y = y + yPosition;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int $x = x + xPosition;
				if ($x < -tile.sprite.SIZE || $x >= width || $y < 0 || $y >= height)
					break;
				if ($x < 0)
					$x = 0;
				pixels[$x + $y * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}

	public void renderProjectile(int xPosition, int yPosition, Projectile projectile) {
		xPosition -= xOfSet;
		yPosition -= yOfSet;
		for (int y = 0; y < projectile.getSpriteSize(); y++) {
			int $y = y + yPosition;
			for (int x = 0; x < projectile.getSpriteSize(); x++) {
				int $x = x + xPosition;
				if ($x < -projectile.getSpriteSize() || $x >= width || $y < 0 || $y >= height)
					break;
				if ($x < 0)
					$x = 0;
				int col = projectile.getSprite().pixels[x + y * projectile.getSpriteSize()];
				if (col != 0xffff00ff)
					pixels[$x + $y * width] = col;
			}
		}
	}

	public void renderMob(int xPosition, int yPosition, Sprite sprite, int value) {
		int size = 32;
		xPosition -= xOfSet;
		yPosition -= yOfSet;
		for (int y = 0; y < size; y++) {
			int $y = y + yPosition;
			int ySprite = y;
			if (value == 2 || value == 3)
				ySprite = size - 1 - y;

			for (int x = 0; x < size; x++) {
				int $x = x + xPosition;
				int xSprite = x;

				if (value == 1 || value == 3)
					xSprite = size - 1 - x;
				if ($x < -size || $x >= width || $y < 0 || $y >= height)
					break;
				if ($x < 0)
					$x = 0;

				int color = sprite.pixels[xSprite + ySprite * size];
				if (color != 0xffff00ff)
					pixels[$x + $y * width] = color;
			}
		}
	}

}
