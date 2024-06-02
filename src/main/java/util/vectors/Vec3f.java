package util.vectors;

public class Vec3f extends Vec2f {
	public float z;

	public Vec3f(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}

	public Vec3f(float n) {
		this(n, n, n);
	}

	@Override
	public Vec3f add(float n) {
		return add(new Vec3f(n));
	}

	public Vec3f add(Vec3f v) {
		return new Vec3f(x + v.x, y + v.y, z + v.z);
	}

	@Override
	public Vec3f sub(float n) {
		return sub(new Vec3f(n));
	}

	public Vec3f sub(Vec3f v) {
		return new Vec3f(x - v.x, y - v.y, z - v.z);
	}

	@Override
	public Vec3f mul(float n) {
		return mul(new Vec3f(n));
	}

	public Vec3f mul(Vec3f v) {
		return new Vec3f(x * v.x, y * v.y, z * v.z);
	}

	public Vec2f xy() {
		return new Vec2f(x, y);
	}

	@Override
	public String toString() {
		return "Vec3f: x=" + x + ", y=" + y + ", z=" + z;
	}
}
