package util.vectors;

public class Vec3i extends Vec2i {
	public int z;

	public Vec3i(int x, int y, int z) {
		super(x, y);
		this.z = z;
	}

	public Vec3i(int n) {
		this(n, n, n);
	}

	public Vec3i add(int n) {
		return add(new Vec3i(n));
	}

	public Vec3i add(Vec3i v) {
		return new Vec3i(x + v.x, y + v.y, z + v.z);
	}

	public Vec3i sub(int n) {
		return sub(new Vec3i(n));
	}

	public Vec3i sub(Vec3i v) {
		return new Vec3i(x - v.x, y - v.y, z - v.z);
	}

	public Vec3i mul(int n) {
		return mul(new Vec3i(n));
	}

	public Vec3i mul(Vec3i v) {
		return new Vec3i(x * v.x, y * v.y, z * v.z);
	}

	@Override
	public String toString() {
		return "Vec3i: x=" + x + ", y=" + y + ", z=" + z;
	}
}
