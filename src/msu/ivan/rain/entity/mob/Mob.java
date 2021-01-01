package msu.ivan.rain.entity.mob;

import msu.ivan.rain.entity.Entity;
import msu.ivan.rain.entity.projectile.Projectile;
import msu.ivan.rain.entity.projectile.WizardProjectile;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected boolean moving = false;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public Direction dir;
	
	public void move(int xChange, int yChange) {
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

		// if collision is not occur then change the x, y
		if (!collision(xChange, yChange)) {
			x += xChange;
			y += yChange;
		}
	}

	public abstract void update();
	public abstract void render(Screen screen);

	private boolean collision(int xChange, int yChange) {
		for (int c = 0; c < 4; c++) {
			int $x = (x + xChange + c % 2 * 14 - 8) / 16;
			int $y = (y + yChange + c / 2 * 12 + 3) / 16;
			if (level.getTile($x, $y).solid())
				return true;
		}
		return false;
	}

	public void shoot(int x, int y, double angle) {
		Projectile wizardProjectile = new WizardProjectile(x, y, angle);
		level.add(wizardProjectile);
	}
}
