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
package dev.shoheiyamagiwa.easygl4j;

import java.nio.ByteBuffer;

/**
 * The {@code Texture} interface is the superclass of all classes that represent rendered texture.
 *
 * @author Shohei Yamagiwa
 * @since 1.0
 */
public abstract class Image {
    /**
     * The source buffer of the texture.
     */
    private final ByteBuffer imageBuffer;

    /**
     * The width of the texture.
     */
    private final int width;

    /**
     * The height of the texture.
     */
    private final int height;

    /**
     * Creates new texture with given buffer.
     *
     * @param imageBuffer The buffer of image.
     */
    public Image(ByteBuffer imageBuffer, int width, int height) {
        this.imageBuffer = imageBuffer;
        this.width = width;
        this.height = height;
    }

    /**
     * Disposes the image.
     */
    public abstract void dispose();

    public ByteBuffer getImageBuffer() {
        return imageBuffer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
