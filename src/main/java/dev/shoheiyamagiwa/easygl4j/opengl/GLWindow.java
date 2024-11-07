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

import dev.shoheiyamagiwa.easygl4j.Graphics;
import dev.shoheiyamagiwa.easygl4j.Window;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * The {@code GLWindow} class is a main class to create the window with using OpenGL contexts.
 *
 * @author Shohei Yamagiwa
 * @since 1.0
 */
public class GLWindow extends Window {
    /**
     * The id for handling the window in OpenGL.
     */
    private long id;

    public GLWindow(String title, int width, int height) {
        super(title, width, height);
    }

    public GLWindow(String title, Window owner, int width, int height) {
        super(title, owner, width, height);
    }

    @Override
    public void create() {
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW.");
        }

        // Reset all window hints
        glfwDefaultWindowHints();

        // Configure window hints
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_VISIBLE, isVisible() ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, isResizable() ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, isFullscreen() ? GLFW_TRUE : GLFW_FALSE);

        // Create new window
        if (isFullscreen()) {
            id = glfwCreateWindow(getWidth(), getHeight(), getTitle(), glfwGetPrimaryMonitor(), NULL);
        } else {
            id = glfwCreateWindow(getWidth(), getHeight(), getTitle(), NULL, NULL);
        }
        if (id == NULL) {
            throw new RuntimeException("Failed to create a new window.");
        }

        // TODO: Configure callbacks

        // Make the context of the window as current one.
        glfwMakeContextCurrent(id);

        // Show the window if needed.
        if (isVisible()) {
            glfwShowWindow(id);
        }

        // Initialize GL features.
        GL.createCapabilities();
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
    }

    @Override
    public void dispose() {
        glfwDestroyWindow(id);
        glfwTerminate();
    }
}
