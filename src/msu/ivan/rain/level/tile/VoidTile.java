package msu.ivan.rain.level.tile;

import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}

	@Override
	public void render(int x, int y, Screen screen) {
//		render voidTiles
		screen.renderTile(x << 4, y << 4, this);
	}
}
