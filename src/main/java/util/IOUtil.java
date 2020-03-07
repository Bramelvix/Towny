package util;

import org.lwjgl.BufferUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public abstract class IOUtil {

	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
		ByteBuffer buffer;
		try (ReadableByteChannel rbc = Channels.newChannel(IOUtil.class.getResourceAsStream(resource))) {
			buffer = BufferUtils.createByteBuffer(bufferSize);
			int bytes = rbc.read(buffer);
			while (bytes != -1) {
				if (buffer.remaining() == 0) {
					buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
				}
				bytes = rbc.read(buffer);
			}
		}

		buffer.flip();
		return buffer;
	}
}
