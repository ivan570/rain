package msu.ivan.rain.entity.spawner;

import msu.ivan.rain.entity.Entity;
import msu.ivan.rain.level.Level;

public class Spawner extends Entity {

	public enum Type {
		MOB, PARTICLE;
	}

	private Type type;

	public Spawner(int x, int y, Type type, int amount, Level level) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public Type getType() {
		return type;
	}
}
