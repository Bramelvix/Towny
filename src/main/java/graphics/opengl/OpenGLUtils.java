package graphics.opengl;

import graphics.TextureInfo;
import main.Game;
import map.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ByteBufferUtil;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.ARBImaging.GL_TABLE_TOO_LARGE;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;
import static org.lwjgl.opengl.GL45.GL_CONTEXT_LOST;

public abstract class OpenGLUtils {

	private OpenGLUtils() {

	}

	private static int vao;
	private static int vbo;
	private static int vbo2;
	private static Shader texShader;
	private static Shader colShader;
	private static Shader fontShader;
	private static Shader tileShader;
	private static final Vec4f outlineColour = new Vec4f(1, 0, 0, 1);

	private static final String OFFSET = "offset";
	private static final String SCALE = "scale";
	private static final Logger logger = LoggerFactory.getLogger(OpenGLUtils.class);


	private static final int MAX_INSTANCES = 5000; //maximum amount of instances that can be drawn in one frame (per buffer)

	private static InstanceData instanceData;

	private static final ArrayList<Outline> outlines = new ArrayList<>();

	public static void init() {
		try {
			texShader = new Shader("/shaders/tex_shader.vert", "/shaders/font_shader.frag");
			colShader = new Shader("/shaders/col_shader.vert", "/shaders/col_shader.frag");
			fontShader = new Shader("/shaders/font_shader.vert", "/shaders/font_shader.frag");
			tileShader = new Shader("/shaders/tile_shader.vert", "/shaders/tile_shader.frag");
		} catch (Exception e) {
			logger.error("Error creating shader", e);
			System.exit(1);
		}

		float[] vertices = {
				// Left bottom triangle
				pToGL((float) Game.WIDTH / 2, 'w'), pToGL((float) Game.HEIGHT / 2, 'h'),
				pToGL((float) Game.WIDTH / 2, 'w'), pToGL((float) Game.HEIGHT / 2 + Tile.SIZE, 'h'),
				pToGL((float) Game.WIDTH / 2 + Tile.SIZE, 'w'), pToGL((float) Game.HEIGHT / 2 + Tile.SIZE, 'h'),
				// Right top triangle
				pToGL((float) Game.WIDTH / 2 + Tile.SIZE, 'w'), pToGL((float) Game.HEIGHT / 2 + Tile.SIZE, 'h'),
				pToGL((float) Game.WIDTH / 2 + Tile.SIZE, 'w'), pToGL((float) Game.HEIGHT / 2, 'h'),
				pToGL((float) Game.WIDTH / 2, 'w'), pToGL((float) Game.HEIGHT / 2, 'h')
		};

		float[] texCoords = {
				0f, 0f,
				0f, 1f,
				1f, 1f,
				1f, 1f,
				1f, 0f,
				0f, 0f
		};


		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glEnableVertexAttribArray(0);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);

		vbo2 = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo2);
		glEnableVertexAttribArray(1);
		glBufferData(GL_ARRAY_BUFFER, texCoords, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

		instanceData = new InstanceData(MAX_INSTANCES);

		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glVertexAttribDivisor(2, 1); //this sends the vertex attrib to the shader per instance instead of per vertex
		glVertexAttribDivisor(3, 1); //this sends the vertex attrib to the shader per instance instead of per vertex

		glLineWidth(1f); //mac's don't support more than 1
		glEnable(GL_LINE_SMOOTH);
	}

	public static void clearAllInstanceData() {
		instanceData.clearInstances();
	}

	public static float pToGL(float pixel, char o) { //converts between pixels and openGL coordinates
		float orientation = o == 'w' ? Game.WIDTH : Game.HEIGHT;
		if (o != 'w') {
			pixel = Game.HEIGHT - pixel;
		}
		return (2f * pixel) / orientation - 1f;
	}

	public static TextureInfo loadTexture(int[] pixels, int width, int height) {
		ByteBuffer buffer = ByteBufferUtil.getByteBuffer(pixels, width, height);
		return loadTexture(buffer, width, height);
	}

	public static TextureInfo loadTexture(ByteBuffer buffer, int width, int height) {
		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
		//Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		//Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		//Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		//Return the texture ID so we can bind it again later
		return new TextureInfo(textureID, width, height, buffer);
	}

	public static TextureInfo loadTexture(BufferedImage image) {
		ByteBuffer buffer = ByteBufferUtil.getByteBuffer(image);
		return loadTexture(buffer, image.getWidth(), image.getHeight());
	}

	public static TextureInfo loadTexture(String filename) {
		try {
			return loadTexture(ImageIO.read(OpenGLUtils.class.getResource(filename)));
		} catch (IOException e) {
			logger.error("Error reading Image: {}", filename, e);
			System.exit(1);
			return null;
		}
	}

	public static void deleteTexture(int textId) {
		glDeleteTextures(textId);
	}

	public static void drawTexturedQuad(Vec2f pos, Vec2f size, Vec2f offset, Vec2f texPos, Vec2f texSize, int texture) {
		glBindTexture(GL_TEXTURE_2D, texture);

		fontShader.setUniform(OFFSET, pToGL(pos.x - offset.x, 'w'), pToGL(pos.y - offset.y, 'h'));
		fontShader.setUniform(SCALE, size.x / Tile.SIZE, size.y / Tile.SIZE);

		fontShader.setUniform("tex_offset", texPos);
		fontShader.setUniform("tex_scale", texSize);

		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLES, 0, 6);
	}

	public static void drawInstanced(InstanceData instanceData, Vec2f offset) {
		tileShader.use();
		instanceData.bindTexture();

		tileShader.setUniform(SCALE, 1f, 1f);

		tileShader.setUniform(OFFSET, (2f * -offset.x) / Game.WIDTH, (2f * offset.y) / Game.HEIGHT);
		Vec2f texScale = new Vec2f(Tile.SIZE / instanceData.getSpriteSheet().getWidth(), Tile.SIZE / instanceData.getSpriteSheet().getHeight());
		tileShader.setUniform("tex_scale", texScale);

		instanceData.unmapBuffer();

		//I don't like this
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 5 * 4, 0);
		glVertexAttribPointer(3, 2, GL_FLOAT, false, 5 * 4, 3 * 4);

		glDrawArraysInstanced(GL_TRIANGLES, 0, 6, instanceData.getInstances());

		//GL_MAP_UNSYNCHRONIZED may break something at some point
		int accessBits = GL_MAP_WRITE_BIT | GL_MAP_INVALIDATE_BUFFER_BIT | GL_MAP_UNSYNCHRONIZED_BIT;
		instanceData.mapBuffer(accessBits);
	}

	public static void drawTexturedQuadScaled(Vec2f pos, Vec2f size, int texture) {
		drawTexturedQuadScaled(pos, size, new Vec2f(0), texture);
	}

	public static void drawTexturedQuadScaled(Vec2f pos, Vec2f size, Vec2f offset, int texture) {
		//TODO Move this out of here, shader.use() is very bad for fps
		texShader.use();

		glBindTexture(GL_TEXTURE_2D, texture);

		texShader.setUniform(OFFSET, pToGL(pos.x - offset.x, 'w'), pToGL(pos.y - offset.y, 'h'));
		texShader.setUniform(SCALE, size.x / Tile.SIZE, size.y / Tile.SIZE);

		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLES, 0, 6);
	}

	public static void drawOutline(Vec2f pos, Vec2f size, Vec2f offset, Vec4f colour) {
		initSquareDraw(pos, size, offset, colour);
		glDrawArrays(GL_LINE_LOOP, 0, 6);
	}

	public static void drawOutline(Vec2f pos, Vec2f size, Vec4f colour) {
		drawOutline(pos, size, new Vec2f(0), colour);
	}

	public static void iconDraw(int id, Vec2f pos, Vec2f size, boolean drawSelectionSquare) { //drawTexturedQuadScaled ui which does not need to be scaled up
		drawTexturedQuadScaled(pos, size, id);
		if (drawSelectionSquare) {
			drawOutline(pos, size, new Vec4f(1, 0, 0, 1));
		}
	}

	public static void menuDraw(Vec2f pos, Vec2f size) {
		drawFilledSquare(pos, size, new Vec2f(0, 0), new Vec4f(0.3568f, 0.3686f, 0.8235f, 0.5f));
	}

	public static void drawFilledSquare(Vec2f pos, Vec2f size, Vec4f colour) {
		drawFilledSquare(pos, size, new Vec2f(0), colour);
	}

	private static void initSquareDraw(Vec2f pos, Vec2f size, Vec2f offset, Vec4f colour) {
		colShader.use();
		colShader.setUniform(OFFSET, pToGL(pos.x - offset.x, 'w'), pToGL(pos.y - offset.y, 'h'));
		colShader.setUniform(SCALE, size.x / Tile.SIZE, size.y / Tile.SIZE);
		colShader.setUniform("i_color", colour);
		glBindVertexArray(vao);
	}

	public static void drawFilledSquare(Vec2f pos, Vec2f size, Vec2f offset, Vec4f colour) {
		initSquareDraw(pos, size, offset, colour);
		glDrawArrays(GL_TRIANGLES, 0, 6);
	}

	public static void menuItemDraw(Vec2f pos, String text, boolean selected) {
		FontUtils.drawString(selected ? FontUtils.red : FontUtils.black, pos.x, pos.y, text, 1, 1);

	}

	public static void buildOutlineDraw(Vec2f pos, float size, Vec4f color) {
		drawFilledSquare(pos, new Vec2f(size), color);
	}

	public static void drawText(String text, float x, float y) {
		FontUtils.drawString(FontUtils.black, x, y, text, 1, 1);
	}

	public static void drawTextRed(String text, float x, float y) {
		FontUtils.drawString(FontUtils.red, x, y, text, 1, 1);
	}

	public static void clearOutlines() {
		outlines.clear();
	}

	public static void addOutline(Outline outline) {
		outlines.add(outline);
	}

	public static void addOutline(Vec2f pos, Vec2f size, Vec4f color) {
		addOutline(new Outline(pos, size, color));
	}

	public static void addOutline(Vec2f pos, Vec2f size) {
		addOutline(pos, size, outlineColour);
	}

	public static void drawOutlines(Vec2f offset) {
		for (Outline outline : outlines) {
			drawOutline(outline.pos(), outline.size(), offset, outline.color());
		}
	}

	private static String errorToString(int error) {
		return switch (error) {
			case GL_INVALID_ENUM -> "GL_INVALID_ENUM";
			case GL_INVALID_VALUE -> "GL_INVALID_VALUE";
			case GL_INVALID_OPERATION -> "GL_INVALID_OPERATION";
			case GL_STACK_OVERFLOW -> "GL_STACK_OVERFLOW";
			case GL_STACK_UNDERFLOW -> "GL_STACK_UNDERFLOW";
			case GL_OUT_OF_MEMORY -> "GL_OUT_OF_MEMORY";
			case GL_INVALID_FRAMEBUFFER_OPERATION -> "GL_INVALID_FRAMEBUFFER_OPERATION";
			case GL_CONTEXT_LOST -> "GL_CONTEXT_LOST";
			case GL_TABLE_TOO_LARGE -> "GL_TABLE_TOO_LARGE";
			default -> "UNKNOWN_ERROR";
		};
	}

	public static void checkGLError() {
		int glError = glGetError();
		if (glError != 0) {
			String hexString = Integer.toHexString(glError);
			String errorName = errorToString(glError);
			logger.error("GL ERROR: {} {}", hexString, errorName);
		}
	}

	public static void destroy() {
		outlines.clear();
		texShader.destroy();
		colShader.destroy();
		fontShader.destroy();
		tileShader.destroy();
		glDeleteVertexArrays(vao);
		glDeleteBuffers(vbo);
		glDeleteBuffers(vbo2);
		instanceData.destroy();
	}

	public static Shader getTexShader() {
		return texShader;
	}

	public static Shader getFontShader() {
		return fontShader;
	}

	public static InstanceData getInstanceData() {
		return instanceData;
	}
}
