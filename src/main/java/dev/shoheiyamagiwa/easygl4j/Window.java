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

/**
 * The {@code Frame} class is a superclass of all classes that represent the specific window in the application.
 *
 * @author Shohei Yamagiwa
 * @since 1.0
 */
public abstract class Window extends Container {
    /**
     * The title of the window.
     */
    private String title;

    /**
     * Indicates whether the window can be resized by the user or not.
     */
    private boolean resizable;

    /**
     * Indicates whether the window is in fullscreen or not.
     */
    private boolean fullscreen;

    /**
     * The listener that receives window events.
     */
    private WindowListener windowListener;

    /**
     * The graphics context of the window.
     */
    private final Graphics graphics;

    /**
     * Creates new root window with given {@code title}.
     *
     * @param title The title of the window.
     */
    public Window(String title, Graphics graphics) {
        this(title, null, graphics);
    }

    /**
     * Creates new child window of the {@code owner} with given {@code title}
     *
     * @param title The title of the window.
     * @param owner The owner of the window.
     */
    public Window(String title, Window owner, Graphics graphics) {
        super(owner);
        this.title = title;
        this.graphics = graphics;

        create();
        render(graphics);
        dispose();
    }

    /**
     * Create a new window
     */
    public abstract void create();

    /**
     * Dispose the window.
     */
    public abstract void dispose();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public WindowListener getWindowListener() {
        return windowListener;
    }

    public void setWindowListener(WindowListener windowListener) {
        this.windowListener = windowListener;
    }

    public Graphics getGraphics() {
        return graphics;
    }
}
