package msu.ivan.rain.entity.projectile;

import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;

public class WizardProjectile extends Projectile {

	public WizardProjectile(int xOrigin, int yOrigin, double direction) {
		super(xOrigin, yOrigin, direction);
		range = 200;
		damage = 20;
		speed = 4;
		sprite = Sprite.grass;
		rateOfFire = 15;
		newX = Math.cos(angle) * speed;
		newY = Math.sin(angle) * speed;
	}

	@Override
	public void update() {
		move();
	}

	@Override
	protected void move() {
		x += newX;
		y += newY;
	}

	public void render(Screen screen) {
		screen.renderTile(x, y, sprite);
	}
}
