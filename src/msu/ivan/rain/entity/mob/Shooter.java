package msu.ivan.rain.entity.mob;

import msu.ivan.rain.graphics.AnimatedSprite;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;
import msu.ivan.rain.graphics.SpriteSheet;

public class Shooter extends Mob {

	private int time = 0;
	private int xa, ya;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	private AnimatedSprite animSprite = down;

	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
	}

	@Override
	public void update() {
		time++;
		if (time % (random.nextInt(30) + 30) == 0) {
			xa = (random.nextInt(2) != 0) ? (random.nextInt(3) - 1) : 0;
			ya = (random.nextInt(2) != 0) ? (random.nextInt(3) - 1) : 0;
		}
		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);

		if (ya < 0) {
			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = Direction.DOWN;
		}

		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			dir = Direction.RIGHT;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else
			walking = false;
		sprite = animSprite.getSprite();
		Player p = level.getClientPlayer();
		double dx = p.getX() - x;
		double dy = p.getY() - y;
		double dir = Math.atan2(dy, dx);
		shoot(x, y, dir);
	}

	@Override
	public void render(Screen screen) {
		screen.renderMob((int) x - 16, (int) y - 16, this);
	}

}
