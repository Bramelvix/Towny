package graphics.opengl;

import graphics.Spritesheet;
import graphics.SpritesheetHashtable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.vectors.Vec2f;
import util.vectors.Vec3f;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL30.glMapBufferRange;

public class InstanceData {
	private int instances; //amount of instances to render per frame
	private final Logger logger = LoggerFactory.getLogger(InstanceData.class);

	private final int vbo;
	private final int bufferSize;
	private ByteBuffer buffer;

	private Spritesheet spritesheet;

	public InstanceData(int maxInstances) {
		bufferSize = (maxInstances*5*4) + (maxInstances*5*4); //bufferSize in bytes, 3 pos and 2 tex
		setSpritesheet(SpritesheetHashtable.getCombined());

		vbo = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, bufferSize, GL_STATIC_DRAW);
		mapBuffer(GL_MAP_WRITE_BIT);
	}

	public void put(Vec3f pos, Vec2f texCoords) {
		buffer.putFloat((instances*5*4), pos.x);
		buffer.putFloat((instances*5*4)+4, pos.y);
		buffer.putFloat((instances*5*4)+8, pos.z);

		buffer.putFloat((instances*5*4)+12, texCoords.x);
		buffer.putFloat((instances*5*4)+16, texCoords.y);
		instances++;
	}

	public void clearInstances() {
		instances = 0;
	}

	public int getInstances() {
		return instances;
	}

	public boolean notEmpty() {
		return instances > 0;
	}

	public void mapBuffer(int accessBits) {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		buffer = glMapBufferRange(GL_ARRAY_BUFFER, 0, bufferSize, accessBits, buffer);
		instances = 0;
	}

	public void unmapBuffer() {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		boolean sucess = glUnmapBuffer(GL_ARRAY_BUFFER);
		if (!sucess) {
			logger.error("FAILED TO UNMAP BUFFER");
		}
	}

	public int getVbo() {
		return vbo;
	}

	public int getBufferSize() {
		return bufferSize;
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
