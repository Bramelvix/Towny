package graphics.opengl;

import main.Game;
import map.Tile;
import org.lwjgl.BufferUtils;
import util.TextureInfo;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

import java.awt.*;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.stb.STBImage.*;

public abstract class OpenGLUtils {

	private static int VAO;
	public static Shader texShader;
	public static Shader colShader;
	public static Shader fontShader;

	public static void init() throws Exception {
		texShader = new Shader(Game.class.getResource("/shaders/tex_shader.vert"), Game.class.getResource("/shaders/tex_shader.frag"));
		colShader = new Shader(Game.class.getResource("/shaders/col_shader.vert"), Game.class.getResource("/shaders/col_shader.frag"));
		fontShader = new Shader(Game.class.getResource("/shaders/text_shader.vert"), Game.class.getResource("/shaders/tex_shader.frag"));

		float[] vertices = {
				// Left bottom triangle
				pToGL((float)Game.width/2, 'w'), pToGL((float)Game.height/2, 'h'), 0f,
				pToGL((float)Game.width/2, 'w'), pToGL((float)Game.height/2 + Tile.SIZE, 'h'), 0f,
				pToGL((float)Game.width/2 + Tile.SIZE, 'w'), pToGL((float)Game.height/2 + Tile.SIZE, 'h'), 0f,
				// Right top triangle
				pToGL((float)Game.width/2 + Tile.SIZE, 'w'), pToGL((float)Game.height/2 + Tile.SIZE, 'h'), 0f,
				pToGL((float)Game.width/2 + Tile.SIZE, 'w'), pToGL((float)Game.height/2, 'h'), 0f,
				pToGL((float)Game.width/2, 'w'), pToGL((float)Game.height/2, 'h'), 0f
		};

		float[] texCoords = {
				0f, 0f,
				0f, 1f,
				1f, 1f,
				1f, 1f,
				1f, 0f,
				0f, 0f
		};

		VAO = glGenVertexArrays();
		int VBO = glGenBuffers();

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
		float orientation = o == 'w' ? Game.width : Game.height;
		if(o != 'w') { pixel = Game.height - pixel; }
		return (2f * pixel + 1f) / orientation - 1f;
	}

	public static int loadTexture(int[] pixels, int width, int height) {
		ByteBuffer buffer = getByteBuffer(pixels,width,height);
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
		return textureID;
	}

	public static TextureInfo loadTexture(String filename) {
		int[] imageWidth = new int[1];
		int[] imageHeight = new int[1];
		int[] channels = new int[1];
		filename = System.getProperty("user.dir")+"/src/main/resources"+filename;
		ByteBuffer buffer = stbi_load(filename, imageWidth, imageHeight, channels, 4);

		//System.out.println(stbi_failure_reason());

		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, imageWidth[0], imageHeight[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		return new TextureInfo(textureID, imageWidth[0], imageHeight[0], channels[0], buffer);
	}

	public static void deleteTexture(int textId) {
		glDeleteTextures(textId);
	}

	public static ByteBuffer getByteBuffer(int[] pixels, int width, int height) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = pixels[y * width + x];
				buffer.put((byte) ((pixel >> 24) & 0xFF));     // Alpha component.
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));             // Blue component
			}
		}
		buffer.flip();
		return buffer;
	}

	public static void drawTexturedQuad(Vec2f pos, Vec2f size, Vec2f offset, Vec2f texPos, Vec2f texSize, int texture) {
		//fontShader.use();
		glBindTexture(GL_TEXTURE_2D, texture);

		fontShader.setUniform("offset", pToGL(pos.x - offset.x, 'w'), pToGL(pos.y - offset.y, 'h'));
		fontShader.setUniform("scale", size.x / Tile.SIZE, size.y / Tile.SIZE);

		fontShader.setUniform("tex_offset", texPos.x, texPos.y);
		fontShader.setUniform("tex_scale", texSize.x, texSize.y);

		glBindVertexArray(VAO);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		//texShader.use();
	}

	public static void drawTexturedQuadScaled(Vec2f pos, Vec2f size, Vec2f offset, int texture) {
		//TODO Move this out of here, shader.use() is very bad for fps
		texShader.use();

		glBindTexture(GL_TEXTURE_2D, texture);

		texShader.setUniform("offset", pToGL(pos.x - offset.x, 'w'), pToGL(pos.y - offset.y, 'h'));
		texShader.setUniform("scale", size.x / Tile.SIZE, size.y / Tile.SIZE);

		glBindVertexArray(VAO);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		fontShader.use();
	}

	public static void drawOutline(Vec2f pos, Vec2f size, Vec2f offset, Vec4f color) {
		colShader.use();
		colShader.setUniform("offset", pToGL(pos.x - offset.x, 'w'), pToGL(pos.y - offset.y, 'h'));
		colShader.setUniform("scale", size.x/ Tile.SIZE, size.y / Tile.SIZE);
		colShader.setUniform("i_color", color);
		glBindVertexArray(VAO);
		glDrawArrays(GL_LINE_LOOP, 0, 6);
		fontShader.use();
	}

	public static void iconDraw(int id, Vec2f pos, Vec2f size, boolean drawSelectionSquare) { //drawTexturedQuadScaled ui which does not need to be scaled up
		drawTexturedQuadScaled(pos, size, new Vec2f(0,0), id);
		if (drawSelectionSquare) {
			drawOutline(pos, size, new Vec2f(0,0), new Vec4f(1,0,0,1));
		}
	}

	public static void menuDraw(Vec2f pos, Vec2f size) {
		drawFilledSquare(pos,size, new Vec2f(0,0), new Vec4f(0.3568f,0.3686f,0.8235f,0.5f));
	}

	public static void drawFilledSquare(Vec2f pos, Vec2f size, Vec2f offset, Vec4f color) {
		colShader.use();
		colShader.setUniform("offset", pToGL(pos.x - offset.x, 'w'), pToGL(pos.y - offset.y, 'h'));
		colShader.setUniform("scale", size.x/ Tile.SIZE, size.y / Tile.SIZE);
		colShader.setUniform("i_color", color);
		glBindVertexArray(VAO);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		fontShader.use();

	}

	public static void menuItemDraw(Vec2f pos, String text, boolean selected) {
		if (selected) {
			drawTextRed(text, pos.x, pos.y - 5);
		} else {
			drawText(text, pos.x,pos.y - 5);
		}

	}

	public static void buildOutlineDraw(Vec2f pos, float size, Vec2f offset, Color color) {
		Vec4f outColor = new Vec4f(color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f,color.getAlpha()/255f);
		drawFilledSquare(pos, new Vec2f(size), offset, outColor);
	}

	public static void drawText(String text, float x, float y) {
		TrueTypeFont.black.drawString(x,y,text,1,1);
		//texShader.use();
	}

	public static void drawTextRed(String text, float x, float y) {
		TrueTypeFont.red.drawString(x,y,text,1,1);
		//texShader.use();
	}

}
