package msu.ivan.rain.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import msu.ivan.rain.entity.Entity;
import msu.ivan.rain.entity.mob.Mob;
import msu.ivan.rain.entity.mob.Player;
import msu.ivan.rain.entity.particle.Particle;
import msu.ivan.rain.entity.projectile.Projectile;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.level.tile.Tile;
import msu.ivan.rain.util.Vector2i;

// their are two level first level => the randomLevel and second level => getLevelFromFile
public abstract class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;

	public final static Level spawn = new SpawnLevel("res/levels/spawn.png");
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();

	private List<Player> players = new ArrayList<Player>();

	private Comparator<Node> nodeSorter = new Comparator<Node>() {

		public int compare(Node n0, Node n1) {
			if (n0.fCost > n1.fCost)
				return 1;
			else if (n0.fCost < n1.fCost)
				return -1;
			return 0;
		}
	};

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
		else if (entity instanceof Player)
			players.add((Player) entity);
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

	public List<Player> getPlayers() {
		return players;
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		double ex = e.getX(), ey = e.getY();

		for (Entity entity : entities) {
			double x = entity.getX(), y = entity.getY();
			double dx = x - ex, dy = y - ey;
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance < radius)
				result.add(entity);
		}

		return result;
	}

	public List<Mob> getPlayers(Entity e, int radius) {
		List<Mob> result = new ArrayList<Mob>();
		double ex = e.getX();
		double ey = e.getY();
		for (int i = 0; i < players.size(); i++) {
			Mob player = players.get(i);
			double x = player.getX();
			double y = player.getY();
			double dx = Math.abs(x - ex);
			double dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(player);
		}
		return result;
	}

	public Player getPlayerAt(int index) {
		return players.get(index);
	}

	public Player getClientPlayer() {
		return players.get(0);
	}

	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closeList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);

		while (!openList.isEmpty()) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closeList.clear();
				return path;
			}
			openList.remove(current);
			closeList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4)
					continue;
				int x = current.tile.getX(), y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null || at.solid())
					continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closeList, a) && gCost >= current.gCost)
					continue;
				if (!vecInList(openList, a) || gCost < current.gCost)
					openList.add(node);
			}
		}
		closeList.clear();
		return null;
	}

	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector))
				return true;
		}
		return false;
	}

	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		double distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
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
		for (Player player : players) {
			player.update();
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
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved())
				players.remove(i);
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
		for (Player player : players) {
			player.render(screen);
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
