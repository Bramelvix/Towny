package graphics;

import main.Game;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public abstract class OpenglUtils {
    public static int loadTexture(int[] pixels, int size) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(size * size * 4); //4 for RGBA, 3 for RGB

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int pixel = pixels[y * size + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }
        buffer.flip();
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, size, size, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        //Return the texture ID so we can bind it later again
        return textureID;
    }

    public static void draw(int id, int x, int y, int size) { //draw ingame shit which needs to be scaled up
        drawQuad(x*Game.SCALE,y*Game.SCALE,size*Game.SCALE,id);

    }
    private static void drawQuad(int x, int y, int size, int texture) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPushMatrix();
        glBindTexture(GL_TEXTURE_2D, texture);
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(x, y);

            glTexCoord2f(0, 1);
            glVertex2f(x, y + size);

            glTexCoord2f(1, 1);
            glVertex2f(x + size, y + size);

            glTexCoord2f(1, 0);
            glVertex2f(x + size, y );

        }
        glEnd();
        glPopMatrix();
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    public static void drawSelection(int x, int y, int size) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPushMatrix();
        glColor3f(1.0f,0.0f,0.0f);
        glLineWidth(3);
        glBegin(GL_LINE_LOOP);
        {
            glVertex2f(x, y);
            glVertex2f(x, y + size);
            glVertex2f(x + size, y + size);
            glVertex2f(x + size, y );
        }
        glEnd();
        glColor3f(1.0f, 1.0f, 1.0f);
        glPopMatrix();

    }

    public static void iconDraw(int id, int x, int y, int size, boolean drawSelectionSquare) { //draw ui which does not need to be scaled up
        drawQuad(x,y,size,id);
        if (drawSelectionSquare) {
            drawSelection(x,y,size);
        }

    }
    public static void menuDraw(int x, int y, int width, int height) {
        drawFilledSquare(x,y,width,height,0.3568f,0.3686f,0.8235f);
    }
    private static void drawFilledSquare(int x, int y, int width, int height, float r, float g, float b) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPushMatrix();
        glColor3f(r,g,b);
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
        drawFilledSquare(x,y,size,size,color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f);

    }


}
