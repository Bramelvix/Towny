package util;

import java.nio.ByteBuffer;

public class TextureInfo {
	public int id;
	public int width;
	public int height;
	public int channels;
	public ByteBuffer buffer;

	public TextureInfo(int id, int width, int height, int channels, ByteBuffer buffer) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.channels = channels;
		this.buffer = buffer;
	}
}
