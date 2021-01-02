package msu.ivan.rain.entity.mob;

import java.util.List;

import msu.ivan.rain.graphics.AnimatedSprite;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.SpriteSheet;

public class Chaser extends Mob {
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	private AnimatedSprite animatedSprite = down;

	private int xChange = 0, yChange = 0;

	public Chaser(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = animatedSprite.getSprite();
	}

	private void move() {
		xChange = yChange = 0;
		List<Player> players = level.getPlayers(this, 50);
		if (players.size() > 0) {
			Player player = players.get(0);

			if (x < player.getX())
				xChange++;
			if (x > player.getX())
				xChange--;
			if (y < player.getY())
				yChange++;
			if (y > player.getY())
				yChange--;
		}
		if (xChange != 0 || yChange != 0) {
			move(xChange, yChange);
			walking = true;
		} else {
			walking = false;
		}
	}

	@Override
	public void update() {
		move();
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

	}

	@Override
	public void render(Screen screen) {
		sprite = animatedSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), this);
	}

}
