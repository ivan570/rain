package msu.ivan.rain.graphics;

public class AnimatedSprite extends Sprite {

	private int frame = 0;
	private Sprite sprite;
	private int rate = 5;
	private int time = 0;
	private int length = -1;

	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
		super(sheet, width, height);
		this.length = length;
		sprite = sheet.getSprites()[0];
		if (length > sheet.getSprites().length) {
			System.err.print("Error :: length is two high in animatedsheet");
			System.out.println("msu.ivan.rain.graphics::AnimatedSprite::AnimatedSprite(SpriteSheet, int, int, int)");
		}
	}

	public void update() {
		time++;
		if (time % rate == 0) {
			if (frame >= length - 1)
				frame = 0;
			else
				frame++;
			sprite = spriteSheet.getSprites()[frame];
		}
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setFrameRate(int rateOfFrames) {
		this.rate = rateOfFrames;
	}

	public void setFrame(int value) {
		sprite = spriteSheet.getSprites()[value & (spriteSheet.getSprites().length - 1)];
		frame = value;
	}
}
