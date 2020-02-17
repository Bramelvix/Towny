package util.vectors;

public class Vec4i {
	public int x;
	public int y;
	public int z;
	public int w;

	public Vec4i(int x, int y, int z, int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vec4i(int n) {
		this(n,n,n,n);
	}

	public Vec4i add(int n) {
		return new Vec4i(x+n, y+n, z+n, w+n);
	}

	public Vec4i add(Vec4i v) {
		return new Vec4i(x+v.x, y+v.y, z+v.z, w+v.w);
	}

	public Vec4i sub(int n) {
		return new Vec4i(x-n, y-n, z-n, w-n);
	}

	public Vec4i sub(Vec4i v) {
		return new Vec4i(x-v.x, y-v.y, z-v.z, w-v.w);
	}

	public Vec4i mul(int n) {
		return new Vec4i(x*n, y*n, z*n, w*n);
	}

	public Vec4i mul(Vec4i v) {
		return new Vec4i(x*v.x, y*v.y, z*v.z, w*v.w);
	}
}
