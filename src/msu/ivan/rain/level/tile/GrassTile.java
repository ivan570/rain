package msu.ivan.rain.level.tile;

import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;

public class GrassTile extends Tile {

	public GrassTile(Sprite sprite) {
		super(sprite);
	}

	@Override
	public void render(int x, int y, Screen screen) {
		// render grass
		// at level class in render method we call Tile.render with tile coordinate system
		// that why to convert into pixel format we multiply tile coordinate point with 16(which is size of tiles).
		screen.renderTile(x << 4, y << 4, this);
	}
//	default solid value is false
}
