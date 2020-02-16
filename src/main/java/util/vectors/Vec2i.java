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
		return new Vec2i(x+n, y+n);
	}

	public Vec2i add(Vec2i v) {
		return new Vec2i(x+v.x, y+v.y);
	}

	public Vec2i sub(int n) {
		return new Vec2i(x-n, y-n);
	}

	public Vec2i sub(Vec2i v) {
		return new Vec2i(x-v.x, y-v.y);
	}

	public Vec2i mul(int n) {
		return new Vec2i(x*n, y*n);
	}

	public Vec2i mul(Vec2i v) {
		return new Vec2i(x*v.x, y*v.y);
	}
}
