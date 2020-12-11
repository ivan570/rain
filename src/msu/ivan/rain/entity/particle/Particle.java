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
		this.xPosition = random.nextGaussian() + 1.8;
		if (xPosition < 0)
			xPosition = 0.1;
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

		newX += xPosition;
		newY += yPosition;
		newZ += zPosition;
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite((int) newX, (int) (newY - newZ), sprite, true);
	}

}
