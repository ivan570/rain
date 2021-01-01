package msu.ivan.rain.level;

import msu.ivan.rain.entity.mob.Mob;
import msu.ivan.rain.entity.mob.Mob.Direction;
import msu.ivan.rain.graphics.AnimatedSprite;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.SpriteSheet;

public class Chaser extends Mob {
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	private AnimatedSprite animatedSprite = down;
	private boolean walking = false;

	private int xChange = 0, yChange = 0;
	public Chaser(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void update() {
		if (walking)
			animatedSprite.update();
		else
			animatedSprite.setFrame(0);

		if (yChange < 0) {
			animatedSprite = up;
			dir = Direction.UP;
		} else if (yChange > 0) {
			animatedSprite = down;
			dir = Direction.DOWN;
		}

		if (xChange < 0) {
			animatedSprite = left;
			dir = Direction.LEFT;
		} else if (xChange > 0) {
			animatedSprite = right;
			dir = Direction.RIGHT;
		}

		if (xChange != 0 || yChange != 0) {
			move(xChange, yChange);
			walking = true;
		} else
			walking = false;
		sprite = animatedSprite.getSprite();

	}

	@Override
	public void render(Screen screen) {
		sprite = animatedSprite.getSprite();
		screen.renderMob(x, y, sprite, 0);
	}

}
