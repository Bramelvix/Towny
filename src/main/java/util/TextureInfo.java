package util;

import java.nio.ByteBuffer;

public class TextureInfo {
	public final int id;
	public final int width;
	public final int height;
	public final ByteBuffer buffer;

	public TextureInfo(int id, int width, int height, ByteBuffer buffer) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.buffer = buffer;
	}
}
