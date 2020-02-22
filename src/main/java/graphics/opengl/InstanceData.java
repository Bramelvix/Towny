package graphics.opengl;

import util.vectors.Vec2f;
import util.vectors.Vec3f;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL30.glMapBufferRange;

public class InstanceData {
	private static boolean firstTime = true;

	private int maxInstances;
	private int instances; //amount of instances to render per frame

	private int vbo;
	private int bufferSize;
	private ByteBuffer buffer;

	private int textureID;

	public InstanceData(int maxInstances) {
		this.maxInstances = maxInstances; //maximum amount of instances that can be drawn in one frame (per buffer)
		bufferSize = (maxInstances*3*4) + (maxInstances*2*4); //bufferSize in bytes, 3 pos and 2 tex

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

	public int getInstances() {
		return instances;
	}

	public void mapBuffer(int accessBits) {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		buffer = glMapBufferRange(GL_ARRAY_BUFFER, 0, bufferSize, accessBits, buffer);
		instances = 0;
	}

	public void unmapBuffer() {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		boolean sucess = glUnmapBuffer(GL_ARRAY_BUFFER);
		if (!sucess) System.out.println("FAILED TO UNMAP BUFFER");
	}

	public int getVbo() {
		return vbo;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setTextureID(int id) {
		textureID = id;
	}

	public void bindTexture() {
		glBindTexture(GL_TEXTURE_2D, textureID);
	}
}
