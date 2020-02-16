package util.vectors;

public class Vec3i {
	public int x;
	public int y;
	public int z;

	public Vec3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3i(int n) {
		this(n,n,n);
	}

	public Vec3i add(int n) {
		return new Vec3i(x+n, y+n, z+n);
	}

	public Vec3i add(Vec3i v) {
		return new Vec3i(x+v.x, y+v.y, z+v.z);
	}

	public Vec3i sub(int n) {
		return new Vec3i(x-n, y-n, z-n);
	}

	public Vec3i sub(Vec3i v) {
		return new Vec3i(x-v.x, y-v.y, z-v.z);
	}

	public Vec3i mul(int n) {
		return new Vec3i(x*n, y*n, z*n);
	}

	public Vec3i mul(Vec3i v) {
		return new Vec3i(x*v.x, y*v.y, z*v.z);
	}
}
