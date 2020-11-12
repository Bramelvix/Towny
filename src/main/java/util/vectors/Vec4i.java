package util.vectors;

public class Vec4i extends Vec3i {
	public int w;

	public Vec4i(int x, int y, int z, int w) {
		super(x, y, z);
		this.w = w;
	}

	public Vec4i(int n) {
		this(n, n, n, n);
	}

	public Vec4i add(int n) {
		return add(new Vec4i(n));
	}

	public Vec4i add(Vec4i v) {
		return new Vec4i(x+v.x, y+v.y, z+v.z, w+v.w);
	}

	public Vec4i sub(int n) {
		return sub(new Vec4i(n));
	}

	public Vec4i sub(Vec4i v) {
		return new Vec4i(x-v.x, y-v.y, z-v.z, w-v.w);
	}

	public Vec4i mul(int n) {
		return mul(new Vec4i(n));
	}

	public Vec4i mul(Vec4i v) {
		return new Vec4i(x*v.x, y*v.y, z*v.z, w*v.w);
	}

	@Override
	public String toString() {
		return "Vec4i: x=" + x + ", y=" + y + ", z=" + z + ", w=" + w;
	}
}
