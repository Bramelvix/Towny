package graphics;

import graphics.opengl.OpenGLUtils;
import util.TextureInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

//picture with all the sprites in it
public class Spritesheet {

	private final int id; // OpenGL texture id
	private final ByteBuffer buffer;
	private final BufferedImage image;

	public Spritesheet(String path) throws IOException{
		image = ImageIO.read(Spritesheet.class.getResource(path));
		TextureInfo img = OpenGLUtils.loadTexture(image);
		this.id = img.id;
		this.buffer = img.buffer;
	}
	public Spritesheet(BufferedImage image) {
		this.image = image;
		TextureInfo img = OpenGLUtils.loadTexture(image);
		this.id = img.id;
		this.buffer = img.buffer;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getId() {return id;}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

}
