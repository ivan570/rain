package msu.ivan.rain.entity.mob;

import msu.ivan.rain.entity.Entity;
import msu.ivan.rain.entity.projectile.Projectile;
import msu.ivan.rain.entity.projectile.WizardProjectile;
import msu.ivan.rain.graphics.Screen;

public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public Direction dir;

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}

		if (xa > 0)
			dir = Direction.RIGHT;
		if (xa < 0)
			dir = Direction.LEFT;
		if (ya > 0)
			dir = Direction.DOWN;
		if (ya < 0)
			dir = Direction.UP;

		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if (!collision(abs(xa), ya))
					this.x += abs(xa);
				xa -= abs(xa);
			} else {
				if (!collision(abs(xa), ya))
					this.x += xa;
				xa = 0;
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya)))
					this.y += abs(ya);
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya)))
					this.y += ya;
				ya = 0;
			}
		}
	}

	private int abs(double value) {
		if (value < 0)
			return -1;
		return 1;
	}

	public abstract void update();
	public abstract void render(Screen screen);

	private boolean collision(double xChange, double yChange) {
		for (int c = 0; c < 4; c++) {
			double $x = ((x + xChange) - c % 2 * 15) / 16;
			double $y = ((y + yChange) - c / 2 * 15) / 16;
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

	public void shoot(double x, double y, double angle) {
		Projectile wizardProjectile = new WizardProjectile(x, y, angle);
		level.add(wizardProjectile);
	}

}
