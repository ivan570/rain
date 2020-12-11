package msu.ivan.rain.entity.particle;

import msu.ivan.rain.entity.Entity;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;

public class Particle extends Entity {

	private Sprite sprite;

	private int life, time = 0;
	protected double newX, newY, newZ;
	protected double xPosition, yPosition, zPosition;

	public Particle(int x, int y, int life) {
		sprite = Sprite.particle_normal;
		this.newX = this.x = x;
		this.newY = this.y = y;
		this.life = (int) ((double) life * random.nextGaussian() + 10);
		this.xPosition = random.nextGaussian();
		this.yPosition = random.nextGaussian();
		this.newZ = random.nextDouble() + 2.0;
	}

	@Override
	public void update() {
		time++;
		if (time >= 7400)
			time = 0;
		if (time > life)
			remove();

		zPosition -= 0.1;
		if (newZ < 0) {
			newZ = 0;
			zPosition *= (-0.55);
			xPosition *= 0.5;
			yPosition *= 0.5;
		}

		move((newX + xPosition), (yPosition + newY) + (zPosition + newZ));
	}

	private void move(double x, double y) {
		if (collision(x, y)) {
			this.xPosition *= (-0.5);
			this.yPosition *= (-0.5);
			this.zPosition *= (-0.5);
		}
		newX += xPosition;
		newY += yPosition;
		newZ += zPosition;
	}

	public boolean collision(double x, double y) {
		int size = 16;
		for (int c = 0; c < 4; c++) {
			double $x = (x - c % 2 * size) / 16;
			double $y = (y - c / 2 * size) / 16;

			int ix = (int) Math.ceil($x);
			int iy = (int) Math.ceil($y);
			if (c % 2 == 0)
				ix = (int) Math.floor($x);
			if (c / 2 == 0)
				iy = (int) Math.floor($y);
			if (level.getTile(ix, iy).solid())
				return true;
		}
		return false;
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite((int) (newX - 1.1), (int) (newY - newZ - 1.2), sprite, true);
	}

}
