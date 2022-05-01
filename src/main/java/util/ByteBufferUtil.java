package util;

import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public final class ByteBufferUtil {
	private ByteBufferUtil() {
	}

	public static ByteBuffer getByteBuffer(int[] pixels, int width, int height) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = pixels[y * width + x];
				buffer.put((byte) ((pixel >> 24) & 0xFF));     // Alpha component.
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));             // Blue component
			}
		}
		buffer.flip();
		return buffer;
	}

	public static ByteBuffer getByteBuffer(BufferedImage image) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = image.getRGB(x, y);
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));             // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));     // Alpha component.
			}
		}
		buffer.flip();
		return buffer;
	}

}
