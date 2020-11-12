package util.vectors;

public class Vec2i {
	public int x;
	public int y;

	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vec2i(int n) {
		this(n,n);
	}

	public Vec2i add(int n) {
		return add(new Vec2i(n));
	}

	public Vec2i add(Vec2i v) {
		return new Vec2i(x+v.x, y+v.y);
	}

	public Vec2i sub(int n) {
		return sub(new Vec2i(n));
	}

	public Vec2i sub(Vec2i v) {
		return new Vec2i(x-v.x, y-v.y);
	}

	public Vec2i mul(int n) {
		return mul(new Vec2i(n));
	}

	public Vec2i mul(Vec2i v) {
		return new Vec2i(x*v.x, y*v.y);
	}

	@Override
	public String toString() {
		return "Vec2i: x=" + x + ", y=" + y;
	}
}
