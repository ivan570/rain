package msu.ivan.rain.util;

public class Vector2i {

	private int x, y;

	public Vector2i() {
		this(0, 0);
	}

	public Vector2i(Vector2i vector) {
		this(vector.getX(), vector.getY());
	}

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2i add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	public Vector2i subtract(Vector2i vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}

	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Vector2i))
			return false;
		Vector2i vec = (Vector2i) obj;
		if (vec.getX() == this.getX() && vec.getY() == this.getY())
			return true;
		return false;
	}
}
