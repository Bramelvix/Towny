package graphics;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

import static org.lwjgl.opengl.EXTTextureMirrorClamp.GL_MIRROR_CLAMP_TO_EDGE_EXT;
import static org.lwjgl.opengl.GL11.*;

class BufferedImageUtil {

    static int getTexture(BufferedImage resourceimage) {
        int srcPixelFormat;
        int textureID = glGenTextures();
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, textureID);
        if (resourceimage.getColorModel().hasAlpha()) {
            srcPixelFormat = GL_RGBA;
        } else {
            srcPixelFormat = GL_RGB;
        }
        ByteBuffer textureBuffer = imageToByteBuffer(resourceimage);
        int textHeight = resourceimage.getHeight();
        int textWidth = resourceimage.getWidth();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRROR_CLAMP_TO_EDGE_EXT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRROR_CLAMP_TO_EDGE_EXT);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, textWidth, textHeight, 0, srcPixelFormat, GL_UNSIGNED_BYTE, textureBuffer);
        return textureID;
    }

    private static ByteBuffer imageToByteBuffer(BufferedImage image) {
        WritableRaster raster;
        BufferedImage texImage;
        int texWidth = 2;
        int texHeight = 2;
        while (texWidth < image.getWidth()) {
            texWidth *= 2;
        }
        while (texHeight < image.getHeight()) {
            texHeight *= 2;
        }
        boolean useAlpha = image.getColorModel().hasAlpha();

        if (useAlpha) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
            texImage = new BufferedImage(new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                    new int[]{8, 8, 8, 8},
                    true,
                    false,
                    ComponentColorModel.TRANSLUCENT,
                    DataBuffer.TYPE_BYTE), raster, false, new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
            texImage = new BufferedImage(new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                    new int[]{8, 8, 8, 0}, false, false, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE), raster, false, new Hashtable());
        }

        Graphics2D g = (Graphics2D) texImage.getGraphics();

        if (useAlpha) {
            g.setColor(new Color(0f, 0f, 0f, 0f));
            g.fillRect(0, 0, texWidth, texHeight);
        }
        g.drawImage(image, 0, 0, null);
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
        ByteBuffer imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();
        g.dispose();
        return imageBuffer;
    }
}
