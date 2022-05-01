package graphics;

import java.nio.ByteBuffer;

public record TextureInfo(int id, int width, int height, ByteBuffer buffer) {
}
