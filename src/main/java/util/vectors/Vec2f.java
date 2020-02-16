package util.vectors;

public class Vec2f {
	public float x;
	public float y;

	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vec2f(float n) {
		this(n,n);
	}

	public Vec2f add(float n) {
		return new Vec2f(x+n, y+n);
	}

	public Vec2f add(Vec2f v) {
		return new Vec2f(x+v.x, y+v.y);
	}

	public Vec2f sub(float n) {
		return new Vec2f(x-n, y-n);
	}

	public Vec2f sub(Vec2f v) {
		return new Vec2f(x-v.x, y-v.y);
	}

	public Vec2f mul(float n) {
		return new Vec2f(x*n, y*n);
	}

	public Vec2f mul(Vec2f v) {
		return new Vec2f(x*v.x, y*v.y);
	}
}