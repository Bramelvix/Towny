package graphics;

import main.Game;
import map.Tile;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

//sprites in the game
public class Sprite {
    private int id;
    public static final int SIZE = Tile.SIZE; // 16
    public int[] pixels;

    protected Sprite(int x, int y, Spritesheet sheet) {
        pixels = load(x * SIZE + (x * sheet.getMargin()),
                y * SIZE + (y * sheet.getMargin()), SIZE, sheet);
        id = loadTexture(pixels, SIZE);
    }

    public Sprite(int[] pixels) {
        id = loadTexture(pixels, SIZE);
    }
    public Sprite() {
        id = 0;
    }

    // load a sprites pixels into the pixel array
    private int[] load(int xa, int ya, int size, Spritesheet sheet) {
        int[] pixels = new int[16 * 16];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                pixels[x + y * size] = sheet.getPixels()[(x + xa) + (y + ya) * sheet.getWidth()];
            }
        }
        return pixels;
    }

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

    public void draw(int x, int y) {


        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glPushMatrix();
        glBindTexture(GL_TEXTURE_2D, id);
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(x*Game.SCALE,y*Game.SCALE);

            glTexCoord2f(0, 1);
            glVertex2f(x*Game.SCALE, (y+SIZE)* Game.SCALE);

            glTexCoord2f(1, 1);
            glVertex2f((x+SIZE)* Game.SCALE, (y+SIZE)* Game.SCALE);

            glTexCoord2f(1, 0);
            glVertex2f((x+SIZE)* Game.SCALE, y*Game.SCALE);




        }
        glEnd();
        glPopMatrix();
        glBindTexture(GL_TEXTURE_2D,0);
    }


}
