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

	public void move(double xChange, double yChange) {
		if (xChange != 0 && yChange != 0) {
			move(xChange, 0);
			move(0, yChange);
			return;
		}

		if (xChange > 0)
			dir = Direction.RIGHT;
		if (xChange < 0)
			dir = Direction.LEFT;
		if (yChange > 0)
			dir = Direction.DOWN;
		if (yChange < 0)
			dir = Direction.UP;

		while (Math.abs(xChange) > 0) {
			if (Math.abs(xChange) > 1) {
				if (!collision(abs(xChange), yChange))
					this.x += abs(xChange);
				xChange -= abs(xChange);
			} else {
				if (!collision(abs(xChange), yChange))
					this.x += xChange;
				xChange = 0;
			}
		}
		while (Math.abs(yChange) > 0) {
			if (Math.abs(yChange) > 1) {
				if (!collision(xChange, abs(yChange)))
					this.y += abs(yChange);
				yChange -= abs(yChange);
			} else {
				if (!collision(xChange, abs(yChange)))
					this.y += yChange;
				yChange = 0;
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
			double $x = ((x + xChange) - c % 2 * 16) / 16;
			double $y = ((y + yChange) - c / 2 * 16) / 16;
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
