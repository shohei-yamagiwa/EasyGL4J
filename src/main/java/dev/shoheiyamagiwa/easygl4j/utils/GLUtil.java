/**
 * The MIT License (MIT)
 * <p>
 * Copyright @ 2024, Shohei Yamagiwa
 * </p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * </p>
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package dev.shoheiyamagiwa.easygl4j.utils;

import dev.shoheiyamagiwa.easygl4j.graphics.Color;
import dev.shoheiyamagiwa.easygl4j.opengl.graphics.GLImage;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * The {@code GLUtil} class has a lot of utility classes involved with OpenGL.
 *
 * @author Shohei Yamagiwa
 */
public class GLUtil {
    /**
     * Load a shader from file.
     *
     * @param filePath The file path of the shader.
     * @return Loaded shader from given file
     */
    public static CharSequence loadSource(String filePath) {
        StringBuilder builder = new StringBuilder();
        try (InputStream in = new FileInputStream(filePath)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * Creates new {@code GLImage} using given image path.
     *
     * @param path The file path of an image.
     * @return {@code GLImage} instance.
     */
    public static GLImage createImage(String path) {
        ByteBuffer imageBuffer;
        int width;
        int height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            /* Allocate pointers to store image data */
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            /* Load image */
            STBImage.stbi_set_flip_vertically_on_load(true);
            imageBuffer = STBImage.stbi_load(path, w, h, channels, 4);
            if (imageBuffer == null) {
                throw new RuntimeException("Failed to load a texture file!" + System.lineSeparator() + STBImage.stbi_failure_reason());
            }

            /* Get width and height of image */
            width = w.get();
            height = h.get();
        }
        return new GLImage(imageBuffer, width, height);
    }

    /**
     * Creates new {@code GLImage} using given color.
     *
     * @param color The color of an image
     * @return {@code GLImage} instance.
     */
    public static GLImage createImage(Color color, int width, int height) {
        ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            long hex = color.toHex();
            /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
            buffer.put((byte) ((hex >> 16) & 0xFF));
            /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
            buffer.put((byte) ((hex >> 8) & 0xFF));
            /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
            buffer.put((byte) (hex & 0xFF));
            /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
            buffer.put((byte) ((hex >> 24) & 0xFF));
        }
        buffer.flip();
        GLImage image = new GLImage(buffer, width, height);
        MemoryUtil.memFree(buffer);
        return image;
    }

    /**
     * Creates vertical gradient {@code GLImage} using given colors.
     *
     * @param top    The top color of a gradient.
     * @param bottom The bottom color of a gradient.
     * @return {@code GLImage} instance.
     */
    public static GLImage createVerticalGradientImage(Color top, Color bottom, int width, int height) {
        ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
        for (int i = 0; i < height; i++) {
            float colorRatio = 1.0F - ((float) (i + 1) / height);
            float r = top.getRed() * colorRatio + bottom.getRed() * (1.0F - colorRatio);
            float g = top.getGreen() * colorRatio + bottom.getGreen() * (1.0F - colorRatio);
            float b = top.getBlue() * colorRatio + bottom.getBlue() * (1.0F - colorRatio);
            float a = top.getAlpha() * colorRatio + bottom.getAlpha() * (1.0F - colorRatio);
            Color color = new Color(r, g, b, a);
            long hex = color.toHex();

            for (int j = 0; j < width; j++) {
                /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
                buffer.put((byte) ((hex >> 16) & 0xFF));
                /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
                buffer.put((byte) ((hex >> 8) & 0xFF));
                /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
                buffer.put((byte) (hex & 0xFF));
                /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
                buffer.put((byte) ((hex >> 24) & 0xFF));
            }
        }
        buffer.flip();
        GLImage image = new GLImage(buffer, width, height);
        MemoryUtil.memFree(buffer);
        return image;
    }

    /**
     * Creates horizontal gradient {@code GLImage} using given colors.
     *
     * @param left  The left color of a gradient.
     * @param right The right color of a gradient.
     * @return {@code GLImage} instance.
     */
    public static GLImage createHorizontalGradientImage(Color left, Color right, int width, int height) {
        ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                float colorRatio = 1.0F - ((float) (j + 1) / height);
                float r = left.getRed() * colorRatio + right.getRed() * (1.0F - colorRatio);
                float g = left.getGreen() * colorRatio + right.getGreen() * (1.0F - colorRatio);
                float b = left.getBlue() * colorRatio + right.getBlue() * (1.0F - colorRatio);
                float a = left.getAlpha() * colorRatio + right.getAlpha() * (1.0F - colorRatio);
                Color color = new Color(r, g, b, a);
                long hex = color.toHex();
                /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
                buffer.put((byte) ((hex >> 16) & 0xFF));
                /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
                buffer.put((byte) ((hex >> 8) & 0xFF));
                /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
                buffer.put((byte) (hex & 0xFF));
                /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
                buffer.put((byte) ((hex >> 24) & 0xFF));
            }
        }
        buffer.flip();
        GLImage image = new GLImage(buffer, width, height);
        MemoryUtil.memFree(buffer);
        return image;
    }
}
