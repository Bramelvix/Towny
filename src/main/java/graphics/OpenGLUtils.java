package graphics;

import main.Game;
import map.Tile;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import util.vectors.Vec4f;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.file.Paths;

import static java.lang.Math.abs;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL44.glBindVertexBuffers;

public abstract class OpenGLUtils {

	private static int VAO;
	public static Shader texShader, colShader, textShader;

	public static void init() throws Exception {
		texShader = new Shader(Paths.get(Game.class.getResource("/shaders/tex_shader.vert").toURI()),Paths.get(Game.class.getResource("/shaders/tex_shader.frag").toURI()));
		colShader = new Shader(Paths.get(Game.class.getResource("/shaders/col_shader.vert").toURI()),Paths.get(Game.class.getResource("/shaders/col_shader.frag").toURI()));
		textShader = new Shader(Paths.get(Game.class.getResource("/shaders/text_shader.vert").toURI()),Paths.get(Game.class.getResource("/shaders/tex_shader.frag").toURI()));

		float[] vertices = {
				// Left bottom triangle
				pToGL((float)Game.width/2, 'w'), pToGL((float)Game.height/2, 'h'), 0f,
				pToGL((float)Game.width/2, 'w'), pToGL((float)Game.height/2+48, 'h'), 0f,
				pToGL((float)Game.width/2+48, 'w'), pToGL((float)Game.height/2+48, 'h'), 0f,
				// Right top triangle
				pToGL((float)Game.width/2+48, 'w'), pToGL((float)Game.height/2+48, 'h'), 0f,
				pToGL((float)Game.width/2+48, 'w'), pToGL((float)Game.height/2+0, 'h'), 0f,
				pToGL((float)Game.width/2+0, 'w'), pToGL((float)Game.height/2+0, 'h'), 0f
		};

		float[] texCoords = {
				0f, 0f,
				0f, 1f,
				1f, 1f,
				1f, 1f,
				1f, 0f,
				0f, 0f
		};

		int VBO;
		VAO = glGenVertexArrays();
		VBO = glGenBuffers();

		glBindVertexArray(VAO);
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		int VBO2 = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VBO2);
		glBufferData(GL_ARRAY_BUFFER, texCoords, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);

		glLineWidth(3);
		glEnable(GL_LINE_SMOOTH);
	}

	private static float pToGL(float pixel, char o) { //converts between pixels and openGL coordinates
		float orientation;
		if(o == 'w') orientation = Game.width;
		else {
			orientation = Game.height;
			pixel = Game.height-pixel;
		}
		return (2f * pixel + 1f) / orientation - 1f;
	}

	public static int loadTexture(int[] pixels, int width, int height) {
		ByteBuffer buffer = getByteBuffer(pixels,width,height);
		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
		//Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		//Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		//Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		//Return the texture ID so we can bind it again later
		return textureID;
	}

	public static void deleteTexture(int textId) {
		glDeleteTextures(textId);
	}

	public static ByteBuffer getByteBuffer(int[] pixels, int width, int height) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = pixels[y * width + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));             // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));     // Alpha component.
			}
		}
		buffer.flip();
		return buffer;
	}

	public static void drawGlyph(int texture, float x, float y, float width, float height, float u, float v, float texW, float texH) {
		textShader.setUniform("offset", pToGL(x, 'w'), pToGL(y, 'h'));
		textShader.setUniform("scale", width / Tile.SIZE, height / Tile.SIZE);

		textShader.setUniform("tex_offset", u, v);
		textShader.setUniform("tex_scale", texW, texH);

		glBindVertexArray(VAO);
		glDrawArrays(GL_TRIANGLES, 0, 6);
	}

	public static void drawTexturedQuadScaled(int id, int x, int y, float xOffset, float yOffset,  int size) { //drawTexturedQuadScaled ingame shit which needs to be scaled up
		drawTexturedQuadScaled(x,y,size,size, xOffset, yOffset, id);
	}

	public static void drawTexturedQuadScaled(float x, float y, float width, float height, float xOffset, float yOffset, int texture) {
		//TODO Move this out of here, shader.use() is very bad for fps
		texShader.use();

		glBindTexture(GL_TEXTURE_2D, texture);

		texShader.setUniform("offset", pToGL(x - xOffset, 'w'), pToGL(y - yOffset, 'h'));
		texShader.setUniform("scale", width / Tile.SIZE, height / Tile.SIZE);

		glBindVertexArray(VAO);
		glDrawArrays(GL_TRIANGLES, 0, 6);
	}

	public static void drawOutline(int x, int y, int width, int height, float xOffset, float yOffset, Vec4f color) {
		colShader.use();

		colShader.setUniform("offset", pToGL((float)x - xOffset, 'w'), pToGL((float)y - yOffset, 'h'));
		colShader.setUniform("scale", (float) width/ Tile.SIZE, (float) height / Tile.SIZE);
		colShader.setUniform("i_color", color);

		glBindVertexArray(VAO);
		glDrawArrays(GL_LINE_LOOP, 0, 6);
	}

	public static void iconDraw(int id, int x, int y, int width,int height, boolean drawSelectionSquare) { //drawTexturedQuadScaled ui which does not need to be scaled up
		drawTexturedQuadScaled(x,y,width,height, 0, 0 ,id);
		if (drawSelectionSquare) {
			drawOutline(x,y,width,height, 0, 0, new Vec4f(1,0,0,1));
		}
	}

	public static void menuDraw(int x, int y, int width, int height) {
		drawFilledSquare(x,y,width,height,0,0 , new Vec4f(0.3568f,0.3686f,0.8235f,0.5f));
	}

	public static void drawFilledSquare(int x, int y, int width, int height, float xOffset, float yOffset, Vec4f color) {
		colShader.use();
		colShader.setUniform("offset", pToGL((float)x - xOffset, 'w'), pToGL((float)y - yOffset, 'h'));
		colShader.setUniform("scale", (float) width/ Tile.SIZE, (float) height / Tile.SIZE);
		colShader.setUniform("i_color", color);
		glBindVertexArray(VAO);
		glDrawArrays(GL_TRIANGLES, 0, 6);

	}

	public static void menuItemDraw(int x, int y, String text, boolean selected) {
		if (selected) {
			drawTextRed(text, x, y - 5);
		} else {
			drawText(text,x,y - 5);
		}

	}

	public static void buildOutlineDraw(int x, int y, int size, float xOffset, float yOffset, Color color) {
		Vec4f outColor = new Vec4f(color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f,color.getAlpha()/255f);
		drawFilledSquare(x, y, size, size, xOffset, yOffset, outColor);
	}

	public static BufferedImage getScaledBufferedImage(BufferedImage before, float xScale, float yScale) {
		AffineTransform at = new AffineTransform();
		at.scale(xScale, yScale);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage after = new BufferedImage( (int)(before.getWidth()*xScale), (int)(before.getHeight()*yScale), BufferedImage.TYPE_INT_ARGB);
		after = scaleOp.filter(before, after);
		return after;
	}

	public static void drawText(String text, int x, int y) {
		TrueTypeFont.black.drawString(x,y,text,1,1);
	}

	public static void drawTextRed(String text, int x, int y) {
		TrueTypeFont.red.drawString(x,y,text,1,1);
	}

}
