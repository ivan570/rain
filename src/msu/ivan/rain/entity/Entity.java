package msu.ivan.rain.entity;

import java.util.Random;

import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;
import msu.ivan.rain.level.Level;

public abstract class Entity {

	protected double x, y;

	private boolean removed = false;
	protected Sprite sprite;
	protected Level level;
	protected final Random random = new Random();

	public Entity() {

	}

	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void update() {

	}

	public void render(Screen screen) {
		if (sprite != null) screen.renderSprite((int)x, (int)y, sprite, true);
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void remove() {
		// remove from level
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}
	
	public String toString() {
		return "X = " + x + " Y " + y;
	}
}
