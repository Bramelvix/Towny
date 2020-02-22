package util.vectors;

public class Vec3f {
	public float x;
	public float y;
	public float z;

	public Vec3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3f(float n) {
		this(n,n,n);
	}

	public Vec3f add(float n) {
		return new Vec3f(x+n, y+n, z+n);
	}

	public Vec3f add(Vec3f v) {
		return new Vec3f(x+v.x, y+v.y, z+v.z);
	}

	public Vec3f sub(float n) {
		return new Vec3f(x-n, y-n, z-n);
	}

	public Vec3f sub(Vec3f v) {
		return new Vec3f(x-v.x, y-v.y, z-v.z);
	}

	public Vec3f mul(float n) {
		return new Vec3f(x*n, y*n, z*n);
	}

	public Vec3f mul(Vec3f v) {
		return new Vec3f(x*v.x, y*v.y, z*v.z);
	}

	public Vec2f xy() {
		return new Vec2f(x, y);
	}
}
