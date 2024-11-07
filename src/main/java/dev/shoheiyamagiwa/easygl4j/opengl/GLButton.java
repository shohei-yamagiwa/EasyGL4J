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

import dev.shoheiyamagiwa.easygl4j.Button;
import dev.shoheiyamagiwa.easygl4j.Container;
import dev.shoheiyamagiwa.easygl4j.Graphics;

/**
 * The {@code GLButton} class is an implementation of the button super class.
 *
 * @author Shohei Yamagiwa
 */
public class GLButton extends Button {
    /**
     * Creates a new button that belongs to parent container.
     *
     * @param parent The container that is a parent of the component.
     */
    protected GLButton(Container parent) {
        super(parent);
    }

    @Override
    public void render(Graphics g) {
    }
}
