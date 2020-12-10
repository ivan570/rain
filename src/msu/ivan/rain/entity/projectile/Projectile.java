package msu.ivan.rain.entity.projectile;

import msu.ivan.rain.entity.Entity;
import msu.ivan.rain.graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double distance;
	protected double newX, newY;
	protected double range, speed, damage;

	public Projectile(int xOrigin, int yOrigin, double direction) {
		this.x = this.xOrigin = xOrigin;
		this.y = this.yOrigin = yOrigin;
		this.angle = direction;
	}

	protected void move() {

	}

	public Sprite getSprite() {
		return sprite;
	}

	public int getSpriteSize() {
		return sprite.SIZE;
	}
}
