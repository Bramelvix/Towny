package graphics;

import map.Tile;
import util.vectors.Vec2f;
import util.vectors.Vec3f;
import util.vectors.Vec4i;

import java.awt.*;
import java.nio.ByteBuffer;

//sprites in the game
public interface Sprite {
	float SIZE = Tile.SIZE; // 48

	private static Vec4i getARGB(ByteBuffer buffer, int x, int y, int width) {
		int pixel = buffer.getInt(((y * width) + x) * 4);
		int a = ((pixel >> 24) & 0xFF);
		int r = ((pixel >> 16) & 0xFF);
		int g = ((pixel >> 8) & 0xFF);
		int b = ((pixel) & 0xFF);
		return new Vec4i(a, r, g, b);
	}

	static int getAverageRGB(ByteBuffer buffer, int x, int y, int width) {
		double r = 0;
		double g = 0;
		double b = 0;
		double a = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Vec4i pixel = getARGB(buffer, (int) (x * SIZE) + i, (int) (y * SIZE) + j, width);
				a += pixel.x * pixel.x;
				r += pixel.y * pixel.y;
				g += pixel.z * pixel.z;
				b += pixel.w * pixel.w;
			}
		}
		return new Color(
			(float) Math.sqrt(r / (SIZE*SIZE)) / 255f,
			(float) Math.sqrt(g / (SIZE*SIZE)) / 255f,
			(float) Math.sqrt(b / (SIZE*SIZE)) / 255f,
			(float) Math.sqrt(a / (SIZE*SIZE)) / 255f
		).getRGB();
	}

	void draw(Vec3f pos);

	int getAvgColour();

	Vec2f getTexCoords();


}
