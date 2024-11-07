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
 * A component is a graphical element that is displayed on the screen and interacted with the user.
 *
 * @author Shohei Yamagiwa
 * @since 1.0
 */
public abstract class Component {
    /**
     * The parent of the component. It may be {@code null} if the component is in top-level.
     */
    private Container parent;

    /**
     * The x position of the component in the parent component.
     */
    private int x;

    /**
     * The y position of the component in the parent component.
     */
    private int y;

    /**
     * The width of the component.
     */
    private int width;

    /**
     * The height of the component.
     */
    private int height;

    /**
     * Indicates whether the component is visible or not.
     * A component that is not visible is not displayed on the screen.
     */
    private boolean visible;

    /**
     * Indicates whether the component is enabled or not.
     * A component that is not enabled does not interact with the user.
     */
    private boolean enabled;

    protected Component(Container parent) {
        this.parent = parent;
    }

    public abstract void render(Graphics g);

    protected Container getParent() {
        return parent;
    }

    protected void setParent(Container parent) {
        this.parent = parent;
    }

    protected void setLocation(int x, int y) {
        setX(x);
        setY(y);
    }

    protected int getX() {
        return x;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected int getY() {
        return y;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    protected int getWidth() {
        return width;
    }

    protected void setWidth(int width) {
        this.width = Math.max(0, width);
    }

    protected int getHeight() {
        return height;
    }

    protected void setHeight(int height) {
        this.height = Math.max(0, height);
    }

    protected boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    protected boolean isEnabled() {
        return enabled;
    }

    protected void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
