package util.vectors;

public class Vec4f extends Vec3f {
	public float w;

	public Vec4f(float x, float y, float z, float w) {
		super(x, y, z);
		this.w = w;
	}

	public Vec4f(float n) {
		this(n, n, n, n);
	}

	@Override
	public Vec4f add(float n) {
		return add(new Vec4f(n));
	}

	public Vec4f add(Vec4f v) {
		return new Vec4f(x + v.x, y + v.y, z + v.z, w + v.w);
	}

	@Override
	public Vec4f sub(float n) {
		return sub(new Vec4f(n));
	}

	public Vec4f sub(Vec4f v) {
		return new Vec4f(x - v.x, y - v.y, z - v.z, w - v.w);
	}

	@Override
	public Vec4f mul(float n) {
		return mul(new Vec4f(n));
	}

	public Vec4f mul(Vec4f v) {
		return new Vec4f(x * v.x, y * v.y, z * v.z, w * v.w);
	}

	@Override
	public String toString() {
		return "Vec4f: x=" + x + ", y=" + y + ", z=" + z + ", w=" + w;
	}
}
