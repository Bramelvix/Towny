package util.vectors;

public class Vec4f {
	public float x;
	public float y;
	public float z;
	public float w;

	public Vec4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vec4f(float n) {
		this(n,n,n,n);
	}

	public Vec4f add(float n) {
		return new Vec4f(x+n, y+n, z+n, w+n);
	}

	public Vec4f add(Vec4f v) {
		return new Vec4f(x+v.x, y+v.y, z+v.z, w+v.w);
	}

	public Vec4f sub(float n) {
		return new Vec4f(x-n, y-n, z-n, w-n);
	}

	public Vec4f sub(Vec4f v) {
		return new Vec4f(x-v.x, y-v.y, z-v.z, w-v.w);
	}

	public Vec4f mul(float n) {
		return new Vec4f(x*n, y*n, z*n, w*n);
	}

	public Vec4f mul(Vec4f v) {
		return new Vec4f(x*v.x, y*v.y, z*v.z, w*v.w);
	}
}
