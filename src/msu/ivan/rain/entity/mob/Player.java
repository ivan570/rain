package msu.ivan.rain.entity.mob;

import msu.ivan.rain.Game;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;
import msu.ivan.rain.input.Keyboard;
import msu.ivan.rain.input.Mouse;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite = Sprite.player_forward;
	private int anim = 0;
	private boolean walking = false;

	// create Player at default location
	public Player(Keyboard input) {
		this.input = input;
	}

	// create Player at (x, y) location
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
	}

	@Override
	public void update() {
		int xChange = 0, yChange = 0;
		anim++;
		if (anim <= 0) anim = 0;
		if (input.up) yChange--;
		if (input.down) yChange++;
		if (input.left) xChange--;
		if (input.right) xChange++;

		if (xChange != 0 || yChange != 0) {
			move(xChange, yChange);
			walking = true;
		} else walking = false;

		updateShooting();
	}

	private void updateShooting() {
		if (Mouse.getMouseButton() == 1) {
			double dx = Mouse.getMouseX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getMouseY() - Game.getWindowHeight() / 2;
			double directionAngle = Math.atan2(dy, dx);
//			shoot(Game.getWindowWidth() / 2, Game.getWindowHeight() / 2, directionAngle);
			shoot(this.x, this.y, directionAngle);
		}
	}

	@Override
	public void render(Screen screen) {
		int flip = 0; // value = 0 no flip, 1 for x flip, 2 for y flip, 3 for both flip
		if (dir == 0) {
			sprite = Sprite.player_forward;
			if (walking) {
				if (anim % 20 > 10) sprite = Sprite.player_forward_1;
				else sprite = Sprite.player_forward_2;
			}
		}
		if (dir == 1) {
			sprite = Sprite.player_side;
			if (walking) {
				if (anim % 20 > 10) sprite = Sprite.player_side_1;
				else sprite = Sprite.player_side_2;
			}
		}
		if (dir == 2) {
			sprite = Sprite.player_back;
			if (walking) {
				if (anim % 20 > 10) sprite = Sprite.player_back_1;
				else sprite = Sprite.player_back_2;
			}
		}
		if (dir == 3) {
			sprite = Sprite.player_side;
			if (walking) {
				if (anim % 20 > 10) sprite = Sprite.player_side_1;
				else sprite = Sprite.player_side_2;
			}
			flip = 1;
		}

		screen.renderPLayer(x - 16, y - 16, sprite, flip);
	}

}