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
	Spritesheet SPRITESHEET = SpritesheetHashtable.getCombined();

	private static Vec4i getARGB(ByteBuffer buffer, int x, int y, int width) {
		return getARGB(buffer.getInt(((y * width) + x) * 4));
	}

	static Vec4i getARGB(int pixelColour) {
		int a = ((pixelColour >> 24) & 0xFF);
		int r = ((pixelColour >> 16) & 0xFF);
		int g = ((pixelColour >> 8) & 0xFF);
		int b = ((pixelColour) & 0xFF);
		return new Vec4i(a, r, g, b);
	}

	static int getAverageRGB(ByteBuffer buffer, int x, int y, int width) {
		double r = 0;
		double g = 0;
		double b = 0;
		double a = 0;
		int totalCount = (int) (SIZE * SIZE);
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Vec4i pixel = getARGB(buffer, (int) (x * SIZE) + i, (int) (y * SIZE) + j, width);
				if (pixel.x == 0) { // Do not count fully transparent pixels in the average colour
					totalCount--;
					continue;
				}
				a += pixel.x;
				r += pixel.y;
				g += pixel.z;
				b += pixel.w;
			}
		}
		return new Color(
				(int) r / totalCount,
				(int) g / totalCount,
				(int) b / totalCount,
				(int) a / totalCount
		).getRGB();
	}

	static int getAverageRGB(int[] colours) {
		double a = 0;
		double r = 0;
		double g = 0;
		double b = 0;
		int totalCount = colours.length;
		for (int colour : colours) {
			Vec4i pixel = getARGB(colour);
			if (pixel.x == 0) { // Do not count fully transparent pixels in the average colour
				totalCount--;
				continue;
			}
			a += pixel.x;
			r += pixel.y;
			g += pixel.z;
			b += pixel.w;
		}
		Color colour = new Color(
				(int) r / totalCount,
				(int) g / totalCount,
				(int) b / totalCount,
				(int) a / totalCount
		);
		return colour.getRGB();
	}

	void draw(Vec3f pos);

	int getAvgColour();

	Vec2f getTexCoords();

	static Color toColor(int color) {
		Vec4i argb = getARGB(color);
		return new Color(argb.y, argb.z, argb.w, argb.x);
	}
}
