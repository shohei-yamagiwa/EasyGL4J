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

import dev.shoheiyamagiwa.easygl4j.event.WindowEvent;

/**
 * The listener interface to receive window events.
 *
 * @author Shohei Yamagiwa
 * @since 1.0
 */
public interface WindowListener {
    /**
     * Invoked the first time a window is made visible.
     *
     * @param e Window event
     */
    void onWindowOpened(WindowEvent e);

    /**
     * Invoked when a window is closed.
     *
     * @param e Window event
     */
    void onWindowClosed(WindowEvent e);

    /**
     * Invoked when a window is focused.
     *
     * @param e Window event
     */
    void onWindowFocused(WindowEvent e);

    /**
     * Invoked when a window lost focus.
     *
     * @param e Window event
     */
    void onWindowUnfocused(WindowEvent e);
}
