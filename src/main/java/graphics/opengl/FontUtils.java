package graphics.opengl;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class FontUtils {
	private FontUtils() {

	}

	static TrueTypeFont black;
	static TrueTypeFont red;

	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_RIGHT = 1;
	public static final int ALIGN_CENTER = 2;

	public static void init() {
		Font font = new Font("Verdana", Font.PLAIN, 17);
		black = new TrueTypeFont(font, Color.BLACK);
		red = new TrueTypeFont(font, Color.RED); //existence is pain
	}

	public static void drawString(TrueTypeFont font, float x, float y, String whatchars, float scaleX, float scaleY) {
		drawString(font, x, y, whatchars, whatchars.length() - 1, scaleX, scaleY, ALIGN_LEFT);
	}

	public static void drawString(TrueTypeFont font, float x, float y, String whatchars, float scaleX, float scaleY, int format) {
		drawString(font, x, y, whatchars, whatchars.length() - 1, scaleX, scaleY, format);
	}

	private static void drawString(TrueTypeFont font, float x, float y, String whatchars, int endIndex, float scaleX, float scaleY, int format) {
		TrueTypeFont.IntObject intObject;
		int charCurrent;
		int totalwidth = 0;
		int i = 0;
		int d = 1;
		int c = 9;
		float startY = 0;

		switch (format) {
			case ALIGN_RIGHT: {
				while (i < endIndex) {
					if (whatchars.charAt(i) == '\n') {
						startY -= font.getHeight();
					}
					i++;
				}
				break;
			}
			case ALIGN_CENTER: {
				for (int l = 0; l <= endIndex; l++) {
					charCurrent = whatchars.charAt(l);
					if (charCurrent == '\n') {
						break;
					}
					intObject = font.charArray[charCurrent];
					totalwidth += intObject.getWidth() - 9;
				}
				totalwidth /= -2;
				break;
			}
		}
		OpenGLUtils.getFontShader().use();
		glBindTexture(GL_TEXTURE_2D, font.fontTextureID);
		while (i >= 0 && i <= endIndex) {
			charCurrent = whatchars.charAt(i);
			intObject = font.charArray[charCurrent];
			if (intObject != null) {
				if (charCurrent == '\n') {
					startY -= font.getHeight() * d;
					totalwidth = 0;
					if (format == ALIGN_CENTER) {
						for (int l = i + 1; l <= endIndex; l++) {
							charCurrent = whatchars.charAt(l);
							if (charCurrent == '\n')
								break;
							intObject = font.charArray[charCurrent];
							totalwidth += intObject.getWidth() - 9;
						}
						totalwidth /= -2;
					}
					// if center get next lines total width/2;
				} else {
					font.drawQuad((totalwidth + intObject.getWidth()) * scaleX + x,
							startY * scaleY + y,
							totalwidth * scaleX + x,
							(startY + intObject.getHeight()) * scaleY + y,
							intObject.storedX + intObject.getWidth(),
							intObject.storedY + intObject.getHeight(),
							intObject.storedX,
							intObject.storedY
					);
					totalwidth += (intObject.getWidth() - c) * d;
				}
				i += d;

			}
		}
	}

	public static void destroy() {
		black.destroy();
		red.destroy();
	}

}
