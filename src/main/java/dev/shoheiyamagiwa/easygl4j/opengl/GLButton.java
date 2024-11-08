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
package dev.shoheiyamagiwa.easygl4j.opengl;

import dev.shoheiyamagiwa.easygl4j.graphics.AbstractFont;
import dev.shoheiyamagiwa.easygl4j.graphics.Color;
import dev.shoheiyamagiwa.easygl4j.graphics.Graphics;
import dev.shoheiyamagiwa.easygl4j.opengl.graphics.GLFont;

import java.awt.*;

/**
 * The {@code GLButton} class represents the button in the application and is implemented with OpenGL specification.
 *
 * @author Shohei Yamagiwa
 */
public class GLButton extends GLComponent {
    private AbstractFont font;

    @Override
    public void draw(Graphics g) {
        if (font == null) {
            font = new GLFont(new Font("JetBrains Mono", Font.PLAIN, 24));
        }
        g.setFont(font);
        g.drawText("abcde", 10, 10, new Color(0.5F, 0.5F, 0.0F));
    }

    @Override
    public void dispose() {
        if (font != null) {
            font.dispose();
        }
    }
}
