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
package dev.shoheiyamagiwa.easygl4j.opengl.graphics;

import dev.shoheiyamagiwa.easygl4j.graphics.AbstractFont;
import dev.shoheiyamagiwa.easygl4j.utils.ASCII;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code GLFont} class is the OpenGL implementation of {@link AbstractFont}.
 *
 * @author Shohei Yamagiwa
 * @since 1.0
 */
public class GLFont extends AbstractFont {
    /**
     * The image that stores all standard glyphs.
     */
    private final GLImage fontImage;

    /**
     * All standard glyphs.
     */
    private final Map<Character, Glyph> glyphs = new HashMap<>();

    /**
     * Creates new GLFont from given font info.
     *
     * @param font The font of the text to be rendered on the screen.
     */
    public GLFont(Font font) {
        fontImage = createFontImage(font);
    }

    @Override
    public int getWidth(CharSequence text) {
        int currentLineWidth = 0;
        int maxWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            /* Update the maximum width and reset current line width */
            if (c == '\n') {
                maxWidth = Math.max(maxWidth, currentLineWidth);
                currentLineWidth = 0;
                continue;
            }

            // Skip the carriage return.
            if (c == '\r') {
                continue;
            }

            Glyph glyph = glyphs.get(c);
            currentLineWidth += glyph.width();
        }
        maxWidth = Math.max(maxWidth, currentLineWidth);
        return maxWidth;
    }

    @Override
    public int getHeight(CharSequence text) {
        int currentLineHeight = 0;
        int totalHeight = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            /* Update the total height and reset current line height */
            if (c == '\n') {
                totalHeight += currentLineHeight;
                currentLineHeight = 0;
                continue;
            }

            /* Skip the carriage return */
            if (c == '\r') {
                continue;
            }

            Glyph glyph = glyphs.get(c);
            currentLineHeight = Math.max(currentLineHeight, glyph.height());
        }
        totalHeight += currentLineHeight;
        return totalHeight;
    }

    @Override
    public void dispose() {
        if (fontImage != null) {
            fontImage.dispose();
        }
    }

    /**
     * Creates concatenated image of font glyph to be rendered with given font.
     *
     * @param font The font to be used in rendering text.
     * @return One concatenated image of font glyph to be rendered with given font.
     */
    private GLImage createFontImage(Font font) {
        int imageWidth = 0;
        int imageHeight = 0;

        /* Loop through all printable ascii codes */
        for (char c : ASCII.PRINTABLE_CODES) {
            BufferedImage ch = createCharImage(font, c);
            if (ch == null) { // If character image is null, specified font doesn't contain the character.
                continue;
            }
            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        /* Create image that aligns standard chars horizontally */
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int x = 0;

        /* Loop through all printable ascii codes */
        for (char c : ASCII.PRINTABLE_CODES) {
            BufferedImage charImage = createCharImage(font, c);
            if (charImage == null) { // If character image is null, specified font does not contain the character.
                continue;
            }
            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            /* Register the glyph information */
            Glyph glyph = new Glyph(charWidth, charHeight, x, image.getHeight() - charHeight, 0.0F);
            glyphs.put(c, glyph);

            /* Draw the character on the image */
            g.drawImage(charImage, x, 0, null);
            x += charWidth;
        }
        /* Flip image horizontal to set the origin to bottom left */
        AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
        transform.translate(0, -image.getHeight());
        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = operation.filter(image, null);

        /* Get width and height of the image */
        int width = image.getWidth();
        int height = image.getHeight();

        /* Get pixel data of image */
        int[] pixels = new int[height * width];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        /* Put pixel data into a ByteBuffer */
        ByteBuffer buffer = MemoryUtil.memAlloc(height * width * 4);
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                /* Pixel as RGBA: 0xAARRGGBB */
                int pixel = pixels[h * width + w];
                /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
                buffer.put((byte) (pixel & 0xFF));
                /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        // Prepare the buffer for use.
        buffer.flip();

        // Create font image
        GLImage fontImage = new GLImage(buffer, width, height);

        // Free the memory.
        MemoryUtil.memFree(buffer);
        return fontImage;
    }

    /**
     * Creates the image of given character.
     *
     * @param font The font to create character image with.
     * @param c    The character to be created as an image.
     * @return Buffer image for the given character.
     */
    private BufferedImage createCharImage(Font font, char c) {
        /* Creating temporary image to extract character size */
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        /* Get width and height of given char */
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();
        if (charWidth == 0) {
            return null;
        }

        /* Create image for holding the char */
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        g.setPaint(java.awt.Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    public GLImage getFontImage() {
        return fontImage;
    }

    public Glyph getGlyph(char c) {
        return glyphs.get(c);
    }

    /**
     * The class {@code Glyph} represents dimensions of each glyph of a font.
     *
     * @param width   The width of the glyph.
     * @param height  The height of the glyph.
     * @param x       The x position of the glyph.
     * @param y       The y position of the glyph.
     * @param advance The advance of the glyph from origin.
     */
    public record Glyph(int width, int height, int x, int y, float advance) {
    }
}
