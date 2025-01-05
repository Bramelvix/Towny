package graphics.opengl;

import graphics.Spritesheet;
import graphics.SpritesheetHashtable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.vectors.Vec2f;
import util.vectors.Vec3f;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL30.glMapBufferRange;

public class InstanceData {
	private int instances; //amount of instances to render per frame
	private final Logger logger = LoggerFactory.getLogger(InstanceData.class);

	private final int vbo;
	private final int bufferSize;
	private ByteBuffer buffer;

	private Spritesheet spritesheet;

	private static final byte BYTES_PER_INSTANCE = 5;
	private static final byte FLOAT_BYTE_SIZE = 4;
	private static final byte INSTANCE_SIZE = BYTES_PER_INSTANCE * FLOAT_BYTE_SIZE;

	public InstanceData(int maxInstances) {
		bufferSize = maxInstances * INSTANCE_SIZE; //bufferSize in bytes, 3 pos and 2 tex
		setSpritesheet(SpritesheetHashtable.getCombined());

		vbo = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, bufferSize, GL_STATIC_DRAW);
		mapBuffer(GL_MAP_WRITE_BIT);
	}

	public void put(Vec3f pos, Vec2f texCoords) {
		buffer.putFloat((instances * INSTANCE_SIZE), pos.x);
		buffer.putFloat((instances * INSTANCE_SIZE) + FLOAT_BYTE_SIZE, pos.y);
		buffer.putFloat((instances * INSTANCE_SIZE) + (2 * FLOAT_BYTE_SIZE), pos.z);

		buffer.putFloat((instances * INSTANCE_SIZE) + (3 * FLOAT_BYTE_SIZE), texCoords.x);
		buffer.putFloat((instances * INSTANCE_SIZE) + (4 * FLOAT_BYTE_SIZE), texCoords.y);
		instances++;
	}

	public void clearInstances() {
		instances = 0;
	}

	public int getInstances() {
		return instances;
	}

	public void mapBuffer(int accessBits) {
		buffer = glMapBufferRange(GL_ARRAY_BUFFER, 0, bufferSize, accessBits, buffer);
		instances = 0;
	}

	public void unmapBuffer() {
		boolean success = glUnmapBuffer(GL_ARRAY_BUFFER);
		if (!success) {
			logger.error("FAILED TO UNMAP BUFFER");
		}
	}

	public void setSpritesheet(Spritesheet spritesheet) {
		this.spritesheet = spritesheet;
	}

	public Spritesheet getSpriteSheet() {
		return spritesheet;
	}

	public void bindTexture() {
		glBindTexture(GL_TEXTURE_2D, spritesheet.getId());
	}

	void destroy() {
		glDeleteBuffers(vbo);
	}
}
