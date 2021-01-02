package msu.ivan.rain.entity.mob;

import msu.ivan.rain.Game;
import msu.ivan.rain.entity.projectile.WizardProjectile;
import msu.ivan.rain.graphics.AnimatedSprite;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;
import msu.ivan.rain.graphics.SpriteSheet;
import msu.ivan.rain.input.Keyboard;
import msu.ivan.rain.input.Mouse;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite = Sprite.player_forward;
	private int anim = 0;
	private int fireRate = 0;

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	private AnimatedSprite animatedSprite = down;

	// create Player at default location
	public Player(Keyboard input) {
		this.input = input;
	}

	// create Player at (x, y) location
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		fireRate = WizardProjectile.FIRE_RATE;
	}

	@Override
	public void update() {
		if (walking)
			animatedSprite.update();
		else
			animatedSprite.setFrame(0);
		if (fireRate > 0)
			fireRate--;
		int xChange = 0, yChange = 0;
		anim++;
		if (anim <= 0)
			anim = 0;
		if (input.up) {
			animatedSprite = up;
			yChange--;
		} else if (input.down) {
			animatedSprite = down;
			yChange++;
		}
		if (input.left) {
			animatedSprite = left;
			xChange--;
		} else if (input.right) {
			animatedSprite = right;
			xChange++;
		}
		if (xChange != 0 || yChange != 0) {
			move(xChange, yChange);
			walking = true;
		} else
			walking = false;

		updateShooting();
		clear();
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			if (level.getProjectiles().get(i).isRemoved()) {
				level.getProjectiles().remove(i);
			}
		}
	}

	private void updateShooting() {
		if (Mouse.getMouseButton() == 1 && fireRate <= 0) {
			double dx = Mouse.getMouseX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getMouseY() - Game.getWindowHeight() / 2;
			double directionAngle = Math.atan2(dy, dx);
			// shoot(Game.getWindowWidth() / 2, Game.getWindowHeight() / 2, directionAngle);
			shoot(this.x, this.y, directionAngle);
			fireRate = WizardProjectile.FIRE_RATE;
		}
	}

	@Override
	public void render(Screen screen) {
		int flip = 0; // value = 0 no flip, 1 for x flip, 2 for y flip, 3 for both flip
		sprite = animatedSprite.getSprite();
		screen.renderMob(x - 16, y - 16, sprite, flip);
	}

}
