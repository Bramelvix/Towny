package graphics.opengl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import util.vectors.Vec2f;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class TrueTypeFont {

	static TrueTypeFont black, red;

	private final static int ALIGN_LEFT = 0, ALIGN_RIGHT = 1, ALIGN_CENTER = 2;
	private final IntObject[] charArray = new IntObject[256];
	private final int fontSize;
	private int fontHeight;
	private int fontTextureID;
	private final int textureWidth = 512;
	private final int textureHeight = 512;

	public static void init() {
		Font font = new Font("Verdana", Font.PLAIN, 17);
		black = new TrueTypeFont(font, Color.BLACK);
		red = new TrueTypeFont(font, Color.RED); //existence is pain
	}

	private static class IntObject {
		public int width;
		public int height;
		int storedX;
		int storedY;
	}

	private TrueTypeFont(Font font, Color colour) {
		this.fontSize = font.getSize() + 3;
		createSet(font, colour);
		fontHeight -= 1;
		if (fontHeight <= 0)
			fontHeight = 1;
	}

	private BufferedImage getFontImage(Font font, char ch, Color colour) {
		BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics();
		int charwidth = fontMetrics.charWidth(ch) + 8;

		if (charwidth <= 0) {
			charwidth = 7;
		}
		int charheight = fontMetrics.getHeight() + 3;
		if (charheight <= 0) {
			charheight = fontSize;
		}

		BufferedImage fontImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gt = (Graphics2D) fontImage.getGraphics();
		gt.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		gt.setFont(font);
		gt.setColor(colour);
		int charx = 3;
		int chary = 1;
		gt.drawString(String.valueOf(ch), charx, chary + fontMetrics.getAscent());
		return fontImage;

	}

	private void createSet(Font font, Color colour) {
		try {
			BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) imgTemp.getGraphics();

			g.setColor(new Color(0, 0, 0, 1));
			g.fillRect(0, 0, textureWidth, textureHeight);

			int rowHeight = 0;
			int positionX = 0;
			int positionY = 0;

			for (int i = 0; i < 256; i++) {
				char ch = (char) i;
				BufferedImage fontImage = getFontImage(font, ch, colour);
				IntObject newIntObject = new IntObject();
				newIntObject.width = fontImage.getWidth();
				newIntObject.height = fontImage.getHeight();

				if (positionX + newIntObject.width >= textureWidth) {
					positionX = 0;
					positionY += rowHeight;
					rowHeight = 0;
				}

				newIntObject.storedX = positionX;
				newIntObject.storedY = positionY;

				if (newIntObject.height > fontHeight) {
					fontHeight = newIntObject.height;
				}

				if (newIntObject.height > rowHeight) {
					rowHeight = newIntObject.height;
				}

				// Draw it here
				g.drawImage(fontImage, positionX, positionY, null);

				positionX += newIntObject.width;
				charArray[i] = newIntObject;
			}

			fontTextureID = BufferedImageUtil.getTexture(imgTemp);

		} catch (Exception e) {
			System.err.println("Failed to create font.");
			e.printStackTrace();
		}
	}

	private void drawQuad(float x, float y, float drawX2, float drawY2, float srcX, float srcY, float srcX2, float srcY2) {
		Vec2f glyphSize = new Vec2f(drawX2 - x, drawY2 - y);
		float u = srcX / textureWidth;
		float v = srcY / textureHeight;
		float SrcWidth = srcX2 - srcX;
		float SrcHeight = srcY2 - srcY;
		float texWidth = (SrcWidth / textureWidth);
		float texHeight = (SrcHeight / textureHeight);

		OpenGLUtils.drawTexturedQuad(new Vec2f(x,y), glyphSize, new Vec2f(0,0), new Vec2f(u, v+texHeight), new Vec2f(texWidth, -texHeight), fontTextureID);
	}

	public int getHeight() {
		return fontHeight;
	}

	void drawString(float x, float y, String whatchars, float scaleX, float scaleY) {
		drawString(x, y, whatchars, 0, whatchars.length() - 1, scaleX, scaleY, ALIGN_LEFT);
	}

	public void drawString(float x, float y, String whatchars, float scaleX, float scaleY, int format) {
		drawString(x, y, whatchars, 0, whatchars.length() - 1, scaleX, scaleY, format);
	}

	private void drawString(float x, float y, String whatchars, int startIndex, int endIndex, float scaleX, float scaleY, int format) {
		IntObject intObject;
		int charCurrent;
		int totalwidth = 0;
		int i = startIndex;
		int d;
		int c;
		float startY = 0;

		switch (format) {
			case ALIGN_RIGHT: {
				while (i < endIndex) {
					if (whatchars.charAt(i) == '\n') {
						startY -= fontHeight;
					}
					i++;
				}
			}
			case ALIGN_CENTER: {
				for (int l = startIndex; l <= endIndex; l++) {
					charCurrent = whatchars.charAt(l);
					if (charCurrent == '\n') {
						break;
					}
					intObject = charArray[charCurrent];
					totalwidth += intObject.width - 9;
				}
				totalwidth /= -2;
			}
			case ALIGN_LEFT:
			default:
				d = 1;
				c = 9;
				break;
		}
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fontTextureID);
		OpenGLUtils.fontShader.use();
		glBindTexture(GL_TEXTURE_2D, fontTextureID);
		while (i >= startIndex && i <= endIndex) {
			charCurrent = whatchars.charAt(i);
			intObject = charArray[charCurrent];
			if (intObject != null) {
				if (charCurrent == '\n') {
					startY -= fontHeight * d;
					totalwidth = 0;
					if (format == ALIGN_CENTER) {
						for (int l = i + 1; l <= endIndex; l++) {
							charCurrent = whatchars.charAt(l);
							if (charCurrent == '\n')
								break;
							intObject = charArray[charCurrent];
							totalwidth += intObject.width - 9;
						}
						totalwidth /= -2;
					}
					// if center get next lines total width/2;
				} else {
					drawQuad((totalwidth + intObject.width) * scaleX + x,
						startY * scaleY + y,
						totalwidth * scaleX + x,
						(startY + intObject.height) * scaleY + y,
						intObject.storedX + intObject.width,
						intObject.storedY + intObject.height,
						intObject.storedX,
						intObject.storedY
					);
					totalwidth += (intObject.width - c) * d;
				}
				i += d;

			}
		}
	}

	public void destroy() {
		IntBuffer scratch = BufferUtils.createIntBuffer(1);
		scratch.put(0, fontTextureID);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glDeleteTextures(scratch);
	}

}
