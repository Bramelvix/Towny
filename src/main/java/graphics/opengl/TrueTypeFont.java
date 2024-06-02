package graphics.opengl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import util.BufferedImageUtil;
import util.vectors.Vec2f;
import util.vectors.Vec4i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

public class TrueTypeFont {

	public final Vec4i[] charArray = new Vec4i[256];
	public final int fontSize;
	private int fontHeight;
	public final int fontTextureID;
	private static final int TEXTURE_WIDTH = 512;
	private static final int TEXTURE_HEIGHT = 512;

	public int getHeight() {
		return fontHeight;
	}

	public TrueTypeFont(Font font, Color colour) {
		this.fontSize = font.getSize() + 3;
		BufferedImage imgTemp = new BufferedImage(TEXTURE_WIDTH, TEXTURE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) imgTemp.getGraphics();

		g.setColor(new Color(0, 0, 0, 1));
		g.fillRect(0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);

		int rowHeight = 0;
		int positionX = 0;
		int positionY = 0;

		for (int i = 0; i < 256; i++) {
			char ch = (char) i;
			BufferedImage fontImage = getFontImage(font, ch, colour);
			Vec4i newIntObject = new Vec4i(0);
			newIntObject.x = fontImage.getWidth();
			newIntObject.y = fontImage.getHeight();


			if (positionX + newIntObject.x >= TEXTURE_WIDTH) {
				positionX = 0;
				positionY += rowHeight;
				rowHeight = 0;
			}

			newIntObject.z = positionX;
			newIntObject.w = positionY;

			if (newIntObject.y > fontHeight) {
				fontHeight = newIntObject.y;
			}

			if (newIntObject.y > rowHeight) {
				rowHeight = newIntObject.y;
			}

			// Draw it here
			g.drawImage(fontImage, positionX, positionY, null);

			positionX += newIntObject.x;
			charArray[i] = newIntObject;
		}

		fontTextureID = BufferedImageUtil.getTexture(imgTemp);
		fontHeight -= 1;
		if (fontHeight <= 0) {
			fontHeight = 1;
		}
	}

	private BufferedImage getFontImage(Font font, char ch, Color colour) {
		Graphics2D g = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
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

	void drawQuad(float x, float y, float drawX2, float drawY2, float srcX, float srcY, float srcX2, float srcY2) {
		Vec2f glyphSize = new Vec2f(drawX2 - x, drawY2 - y);
		float u = srcX / TEXTURE_WIDTH;
		float v = srcY / TEXTURE_HEIGHT;
		float srcWidth = srcX2 - srcX;
		float srcHeight = srcY2 - srcY;
		float texWidth = (srcWidth / TEXTURE_WIDTH);
		float texHeight = (srcHeight / TEXTURE_HEIGHT);

		OpenGLUtils.drawTexturedQuad(new Vec2f(x, y), glyphSize, new Vec2f(0, 0), new Vec2f(u, v + texHeight), new Vec2f(texWidth, -texHeight), fontTextureID);
	}

	public void destroy() {
		IntBuffer scratch = BufferUtils.createIntBuffer(1);
		scratch.put(0, fontTextureID);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glDeleteTextures(scratch);
	}

}
