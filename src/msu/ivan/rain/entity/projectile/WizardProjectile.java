package msu.ivan.rain.entity.projectile;

import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;

public class WizardProjectile extends Projectile {

	public static final int FIRE_RATE = 10; // higher the FIRE_RATE the slow number of the bullet in second.

	public WizardProjectile(int xOrigin, int yOrigin, double direction) {
		super(xOrigin, yOrigin, direction);
		range = 200;
		damage = 20;
		speed = 4;
		sprite = Sprite.projectiles_wizard;
		newX = Math.cos(angle) * speed;
		newY = Math.sin(angle) * speed;
	}

	@Override
	public void update() {
		if (level.tileCollision(x, y, newX, newY, 7))
			remove();
		move();
	}

	private double distance() {
		return Math.sqrt(Math.pow(x - xOrigin, 2) + Math.pow(y - yOrigin, 2));
	}

	@Override
	protected void move() {
		x += newX;
		y += newY;
		if (distance() > range)
			remove();
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x - 5, (int) y, this);
	}
}
