package graphics;

import graphics.opengl.OpenGLUtils;
import util.ImgInfo;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//picture with all the sprites in it
public class Spritesheet {

	public int id; // OpenGL texture id
	public int width; // width of the spritesheet
	public int height; // height of the spritesheet
	private int[] pixels; // pixels array

	public Spritesheet(String path) {
		String tempPath = System.getProperty("user.dir")+"/src/main/resources"+path; //TODO see if this is alright to do

		ImgInfo img = OpenGLUtils.loadTexture(tempPath);
		this.id = img.id;
		this.width = img.width;
		this.height = img.height;

		try {
			BufferedImage image = ImageIO.read(Spritesheet.class.getResource(path));

			//width = image.getWidth();
			//height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int[] getPixels() {
		return pixels;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
