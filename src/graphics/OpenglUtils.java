package graphics;

import main.Game;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import static org.lwjgl.opengl.GL11.*;

public abstract class OpenglUtils {
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
        //Return the texture ID so we can bind it later again
        return textureID;
    }
    public static void deleteTexture(int textId) {
        glDeleteTextures(textId);
    }
    public static ByteBuffer getByteBuffer(int[] pixels, int width, int height) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4); //4 for RGBA, 3 for RGB
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }
        buffer.flip();
        return buffer;
    }

    public static void drawTexturedQuadScaled(int id, int x, int y, int size) { //drawTexturedQuadScaled ingame shit which needs to be scaled up
        drawTexturedQuadScaled(x*Game.SCALE,y*Game.SCALE,size*Game.SCALE,size*Game.SCALE,id);

    }
    public static void drawTexturedQuadScaled(int x, int y, int width, int height, int texture) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPushMatrix();
        glBindTexture(GL_TEXTURE_2D, texture);
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(x, y);

            glTexCoord2f(0, 1);
            glVertex2f(x, y + height);

            glTexCoord2f(1, 1);
            glVertex2f(x + width, y + height);

            glTexCoord2f(1, 0);
            glVertex2f(x + width, y );

        }
        glEnd();
        glPopMatrix();
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    public static void drawSelection(int x, int y, int width, int height) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPushMatrix();
        glColor3f(1.0f,0.0f,0.0f);
        glLineWidth(3);
        glBegin(GL_LINE_LOOP);
        {
            glVertex2f(x, y);
            glVertex2f(x, y + height);
            glVertex2f(x + width, y + height);
            glVertex2f(x + width, y );
        }
        glEnd();
        glColor3f(1.0f, 1.0f, 1.0f);
        glPopMatrix();

    }

    public static void iconDraw(int id, int x, int y, int width,int height, boolean drawSelectionSquare) { //drawTexturedQuadScaled ui which does not need to be scaled up
        drawTexturedQuadScaled(x,y,width,height,id);
        if (drawSelectionSquare) {
            drawSelection(x,y,width,height);
        }

    }
    public static void menuDraw(int x, int y, int width, int height) {
        drawFilledSquare(x,y,width,height,0.3568f,0.3686f,0.8235f,0.5f);
    }
    public static void drawFilledSquare(int x, int y, int width, int height, float r, float g, float b, float a) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPushMatrix();
        glColor4f(r,g,b,a);
        glBegin(GL_QUADS);
        {
            glVertex2f(x, y);
            glVertex2f(x, y + height);
            glVertex2f(x + width, y + height);
            glVertex2f(x + width, y );
        }
        glEnd();
        glPopMatrix();
        glColor3f(1.0f,1.0f,1.0f);
    }
    public static void menuItemDraw(int x, int y, String text, boolean selected) {
        if (selected) {
            drawTextRed(text, x, y-5);
        } else {
            drawText(text,x,y-5);
        }
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPushMatrix();
        if (selected) {
            glColor3f(1.0f, 0.0f, 0.0f);
        } else {
            glColor3f(1.0f, 1.0f, 1.0f);
        }
        glBegin(GL_QUADS);
        glEnd();
        glPopMatrix();
        glColor3f(1.0f,1.0f,1.0f);

    }
    public static void buildOutlineDraw(int x, int y, int size, Color color) {
        drawFilledSquare(x,y,size,size,color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f,color.getAlpha()/255f);
    }

    public static BufferedImage getScaledBufferedImage(BufferedImage before, float xScale, float yScale) {
        int w = before.getWidth();
        int h = before.getHeight();
        AffineTransform at = new AffineTransform();
        at.scale(xScale, yScale);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage after = new BufferedImage( (int)(w*xScale), (int)(h*yScale), BufferedImage.TYPE_INT_ARGB);
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
