package msu.ivan.rain.entity.mob;

import msu.ivan.rain.entity.Entity;
import msu.ivan.rain.entity.projectile.Projectile;
import msu.ivan.rain.entity.projectile.WizardProjectile;
import msu.ivan.rain.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = -1; // for direction 0 = north, 1 = east, 2 = south, 3 = west
	protected boolean moving = false;

	public void move(int xChange, int yChange) {
		if (xChange != 0 && yChange != 0) {
			move(xChange, 0);
			move(0, yChange);
			return;
		}

		if (xChange > 0)
			dir = 1;
		if (xChange < 0)
			dir = 3;
		if (yChange > 0)
			dir = 2;
		if (yChange < 0)
			dir = 0;

		// if collision is not occur then change the x, y
		if (!collision(xChange, yChange)) {
			x += xChange;
			y += yChange;
		}
	}

	public void update() {

	}

	private boolean collision(int xChange, int yChange) {
		for (int c = 0; c < 4; c++) {
			int $x = (x + xChange + c % 2 * 14 - 8) / 16;
			int $y = (y + yChange + c / 2 * 12 + 3) / 16;
			if (level.getTile($x, $y).solid())
				return true;
		}
		return false;
	}

	public void render() {

	}

	public void shoot(int x, int y, double angle) {
		Projectile wizardProjectile = new WizardProjectile(x, y, angle);
		level.addProjetile(wizardProjectile);
	}
}
