package msu.ivan.rain.entity.mob;

import msu.ivan.rain.graphics.AnimatedSprite;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;
import msu.ivan.rain.graphics.SpriteSheet;

public class Dummy extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	private AnimatedSprite animatedSprite = down;
	private boolean walking = false;
	
	private int time = 0;
	private int xChange = 0, yChange = 0;
	
	public Dummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
	}

	public void update() {
		time++;
		if (time % (random.nextInt(30) + 30) == 0) {
			xChange = (random.nextInt(5) != 0) ? (random.nextInt(3) - 1) : 0;
			yChange = (random.nextInt(5) != 0) ? (random.nextInt(3) - 1) : 0;
		}
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

	public void render(Screen screen) {
		screen.renderMob(x, y, sprite, 0);
	}

}
