package graphics;

import graphics.opengl.OpenGLUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

//picture with all the sprites in it
public class Spritesheet {

	private final int id; // OpenGL texture id
	private final ByteBuffer buffer;
	private final int width;
	private final int height;

	public Spritesheet(BufferedImage image) {
		TextureInfo img = OpenGLUtils.loadTexture(image);
		width = img.width();
		height = img.height();
		this.id = img.id();
		this.buffer = img.buffer();
	}

	public int getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

}
