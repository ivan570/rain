package msu.ivan.rain.level;

import java.util.ArrayList;
import java.util.List;

import msu.ivan.rain.entity.Entity;
import msu.ivan.rain.entity.particle.Particle;
import msu.ivan.rain.entity.projectile.Projectile;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.level.tile.Tile;

// their are two level first level => the randomLevel and second level => getLevelFromFile
public abstract class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;

	public final static Level spawn = new SpawnLevel("res/levels/spawn.png");
	private List<Entity> entities = new ArrayList<>();
	private List<Projectile> projectiles = new ArrayList<>();
	private List<Particle> particles = new ArrayList<>();

	// Constructor for randomLevel generation
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[height * width];
		generateLevel();
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void add(Entity entity) {
		entity.setLevel(this);
		if (entity instanceof Particle)
			particles.add((Particle) entity);
		else if (entity instanceof Projectile)
			projectiles.add((Projectile) entity);
		else
			entities.add(entity);
	}

	protected void generateLevel() {

	}

	// Constructors for get the level from file
	// path is "path of file"
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void loadLevel(String path) {

	}

	private void time() {

	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		for (int c = 0; c < 4; c++) {
			int $x = (x - c % 2 * size + xOffset) >> 4;
			int $y = (y - c / 2 * size + yOffset) >> 4;
			if (getTile($x, $y).solid())
				return true;
		}
		return false;
	}

	// change = 60 update per second
	public void update() {
		for (Entity entity : entities) {
			entity.update();
		}
		for (Projectile projectile : projectiles) {
			projectile.update();
		}
		for (Particle particle : particles) {
			particle.update();
		}
		remove();
	}

	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved())
				entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved())
				projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved())
				particles.remove(i);
		}
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		// xScroll and yScroll are the two point in pixels coordinate
		screen.setOfSet(xScroll, yScroll);
		int x0 = xScroll >> 4; // (x0, y0) starting point in tiles coordinate system.
		int y0 = yScroll >> 4;
		// here 16 is the size of tile
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4; // (x1, y1) ending point in tiles coordinate system.

		for (int y = y0; y < y1; ++y) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		for (Entity entity : entities) {
			entity.render(screen);
		}
		for (Projectile projectile : projectiles) {
			projectile.render(screen);
		}
		for (Particle particle : particles) {
			particle.render(screen);
		}
	}

	// Grass = 0xFF00FF00
	// Flower = 0xFFFFFF00
	// Rock = 0xFF7F7F00
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;

		if (tiles[x + y * width] == Tile.col_spawn_floor)
			return Tile.spawn_floor;
		if (tiles[x + y * width] == Tile.col_spawn_grass)
			return Tile.spawn_grass;
		if (tiles[x + y * width] == Tile.col_spawn_hedge)
			return Tile.spawn_hedge;
		if (tiles[x + y * width] == Tile.col_spawn_water)
			return Tile.spawn_water;
		if (tiles[x + y * width] == Tile.col_spawn_wall1)
			return Tile.spawn_wall1;
		if (tiles[x + y * width] == Tile.col_spawn_wall2)
			return Tile.spawn_wall2;
		if (tiles[x + y * width] == 0xff7f7f00)
			return Tile.rock;

		return Tile.voidTile;
	}
}
