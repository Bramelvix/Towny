package graphics;

import graphics.opengl.OpenGLUtils;
import util.TextureInfo;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//picture with all the sprites in it
public class Spritesheet {

	private final int id; // OpenGL texture id
	private final int width; // width of the spritesheet
	private final int height; // height of the spritesheet

	public Spritesheet(String path) {
		TextureInfo img = OpenGLUtils.loadTexture(path);
		this.id = img.id;
		this.width = img.width;
		this.height = img.height;
	}

	public int getId() {return id;}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
