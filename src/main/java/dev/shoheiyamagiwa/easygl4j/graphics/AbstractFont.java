package dev.shoheiyamagiwa.easygl4j.graphics;

public abstract class AbstractFont {
    /**
     * Returns the width of rendered text.
     *
     * @param text The text to be rendered on the screen.
     * @return The width of rendered text.
     */
    public abstract int getWidth(CharSequence text);

    /**
     * Returns the height of rendered text.
     *
     * @param text The text to be rendered on the screen.
     * @return The height of rendered text.
     */
    public abstract int getHeight(CharSequence text);

    /**
     * Disposes the font
     */
    public abstract void dispose();
}
