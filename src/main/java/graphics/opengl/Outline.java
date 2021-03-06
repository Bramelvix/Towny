package graphics.opengl;

import util.vectors.Vec2f;
import util.vectors.Vec4f;

public class Outline {
	final Vec2f pos;
	final Vec2f size;
	final Vec4f color;

	public Outline(Vec2f pos, Vec2f size, Vec4f color) {
		this.pos = pos;
		this.size = size;
		this.color = color;
	}
}
