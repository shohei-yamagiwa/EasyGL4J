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
package dev.shoheiyamagiwa.easygl4j.graphics;

import dev.shoheiyamagiwa.easygl4j.utils.MathUtil;

/**
 * The {@code Color} class represents an RGBA color.
 *
 * @author Shohei Yamagiwa
 */
public class Color {
    /**
     * White
     */
    public static final Color WHITE = new Color(1.0F, 1.0F, 1.0F, 1.0F);

    /**
     * A red component of an RGBA color. It ranges from {@code 0.0F} to {@code 1.0F}.
     */
    private float red;

    /**
     * A green component of an RGBA color. It ranges from {@code 0.0F} to {@code 1.0F}.
     */
    private float green;

    /**
     * A blue component of an RGBA color. It ranges from {@code 0.0F} to {@code 1.0F}.
     */
    private float blue;

    /**
     * An opacity component of an RGBA color. It ranges from {@code 0.0F} to {@code 1.0F}.
     */
    private float alpha;

    /**
     * Creates new opaque color with using given components.
     *
     * @param red   A red component of an RGBA color. It ranges from {@code 0.0} to {@code 1.0}.
     * @param green A green component of an RGBA color. It ranges from {@code 0.0} to {@code 1.0}.
     * @param blue  A blue component of an RGBA color. It ranges from {@code 0.0} to {@code 1.0}.
     */
    public Color(float red, float green, float blue) {
        this(red, green, blue, 1.0F);
    }

    /**
     * Creates new color with using given components.
     *
     * @param red   A red component of an RGBA color. It ranges from {@code 0.0} to {@code 1.0}.
     * @param green A green component of an RGBA color. It ranges from {@code 0.0} to {@code 1.0}.
     * @param blue  A blue component of an RGBA color. It ranges from {@code 0.0} to {@code 1.0}.
     * @param alpha An opacity component of an RGBA color. It ranges from {@code 0.0} to {@code 1.0}.
     */
    public Color(float red, float green, float blue, float alpha) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
        setAlpha(alpha);
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = MathUtil.value(red, 0.0F, 1.0F);
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = MathUtil.value(green, 0.0F, 1.0F);
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = MathUtil.value(blue, 0.0F, 1.0F);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = MathUtil.value(alpha, 0.0F, 1.0F);
    }
}