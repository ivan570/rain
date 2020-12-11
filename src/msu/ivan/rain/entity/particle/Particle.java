package msu.ivan.rain.entity.particle;

import msu.ivan.rain.entity.Entity;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;

public class Particle extends Entity {

	private Sprite sprite;

	private int life, time = 0;
	protected double newX, newY, xPosition, yPosition;

	public Particle(int x, int y, int life) {
		sprite = Sprite.particle_normal;
		this.newX = this.x = x;
		this.newY = this.y = y;
		this.life = (int)((double)life * (1.0 - random.nextGaussian()));
		this.xPosition = random.nextGaussian();
		this.yPosition = random.nextGaussian();
	}

	@Override
	public void update() {
		time++;
		if (time >= 7400)
			time = 0;
		if (time > life)
			remove();

		newX += xPosition;
		newY += yPosition;
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite((int) newX, (int) newY, sprite, true);
	}

}
