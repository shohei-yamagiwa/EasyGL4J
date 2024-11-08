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

import dev.shoheiyamagiwa.easygl4j.graphics.Color;
import dev.shoheiyamagiwa.easygl4j.graphics.Graphics;
import dev.shoheiyamagiwa.easygl4j.graphics.Image;
import dev.shoheiyamagiwa.easygl4j.graphics.Shader;
import dev.shoheiyamagiwa.easygl4j.math.Matrix4f;
import dev.shoheiyamagiwa.easygl4j.opengl.graphics.shader.GLFragmentShader;
import dev.shoheiyamagiwa.easygl4j.opengl.graphics.shader.GLShaderProgram;
import dev.shoheiyamagiwa.easygl4j.opengl.graphics.shader.GLVertexShader;
import dev.shoheiyamagiwa.easygl4j.utils.GLUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * The {@code GLGraphics} class is the implementation of {@link Graphics} interface with OpenGL.
 *
 * @author Shohei Yamagiwa
 * @since 1.0
 */
public class GLGraphics implements Graphics {
    /**
     * Variable names in vertex shader file.
     */
    private static final String VERT_ATTR_POSITION = "attrPosition";
    private static final String VERT_ATTR_COLOR = "attrColor";
    private static final String VERT_ATTR_TEX_COORD = "attrTexCoord";
    private static final String VERT_UNI_MODEL = "uniModel";
    private static final String VERT_UNI_VIEW = "uniView";
    private static final String VERT_UNI_PROJECTION = "uniProjection";

    /**
     * Variable names in fragment shader file.
     */
    private static final String FRAG_UNI_TEX_IMAGE = "uniTexImage";
    private static final String FRAG_OUT_COLOR = "fragColor";

    private final GLVertexArrayObject vao;
    private final GLVertexBufferObject vbo;
    private final GLShaderProgram program;

    private final FloatBuffer vertices;
    private int verticesCount;

    private boolean rendering;

    /**
     * Initializes graphics context.
     */
    public GLGraphics() {
        // Initialize states
        verticesCount = 0;
        rendering = false;

        vao = new GLVertexArrayObject();
        vao.bind();
        vbo = new GLVertexBufferObject();
        vbo.bind();

        // Allocate vertices
        vertices = MemoryUtil.memAllocFloat(4096);

        // Upload null data to allocate storage for the VBO
        long size = (long) vertices.capacity() * Float.BYTES;
        vbo.uploadData(size);

        // Load shaders from GLSL file
        // FIXME: Change the way to load a shader file.
        Shader vertexShader = new GLVertexShader(GLUtil.loadSource("/EasyGL4J/src/main/resources/default.vert"));
        Shader fragmentShader = new GLFragmentShader(GLUtil.loadSource("/EasyGL4J/src/main/resources/default.frag"));

        // Create shader program
        program = new GLShaderProgram();
        program.attachShader(vertexShader);
        program.attachShader(fragmentShader);
        program.bindFragmentDataLocation(0, FRAG_OUT_COLOR);
        program.link();
        program.use();

        // Dispose linked shaders
        vertexShader.dispose();
        fragmentShader.dispose();

        /* Specify vertex attributes */
        /* pos[0]  pos[1]  color[0]  color[1]  color[2]  color[3]  texCoord[0]  texCoord[1] */
        int attributePos = program.getAttributeLocation(VERT_ATTR_POSITION);
        program.enableVertexAttribute(attributePos);
        program.pointVertexAttribute(attributePos, 2, 8 * Float.BYTES, 0);

        int attributeColor = program.getAttributeLocation(VERT_ATTR_COLOR);
        program.enableVertexAttribute(attributeColor);
        program.pointVertexAttribute(attributeColor, 4, 8 * Float.BYTES, 2 * Float.BYTES);

        int attributeTextureCoord = program.getAttributeLocation(VERT_ATTR_TEX_COORD);
        program.enableVertexAttribute(attributeTextureCoord);
        program.pointVertexAttribute(attributeTextureCoord, 2, 8 * Float.BYTES, 6 * Float.BYTES);

        /* Get width and height of framebuffer */
        long window = GLFW.glfwGetCurrentContext();
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer widthBuffer = stack.mallocInt(1);
            IntBuffer heightBuffer = stack.mallocInt(1);
            GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
            width = widthBuffer.get();
            height = heightBuffer.get();
        }

        /* Specify texture uniform */
        int uniformTexture = program.getUniformLocation(FRAG_UNI_TEX_IMAGE);
        program.setUniform(uniformTexture, 0);

        /* Set model matrix to identity matrix */
        int uniformModel = program.getUniformLocation(VERT_UNI_MODEL);
        Matrix4f model = new Matrix4f();
        program.setUniform(uniformModel, model);

        /* Set view matrix to identity matrix */
        int uniformView = program.getUniformLocation(VERT_UNI_VIEW);
        Matrix4f view = new Matrix4f();
        program.setUniform(uniformView, view);

        /* Set projection matrix to an orthographic projection */
        int uniformProjection = program.getUniformLocation(VERT_UNI_PROJECTION);
        Matrix4f projection = Matrix4f.orthographic(0.0F, width, 0.0F, height, -1.0F, 1.0F);
        program.setUniform(uniformProjection, projection);

        /* Enable blending */
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, Color color) {
    }

    @Override
    public void drawText(String text, int x, int y, Color color) {
    }

    @Override
    public void drawImage(Image image, int x, int y) {
        /* Vertex positions */
        float x2 = x + image.getWidth();
        float y2 = y + image.getHeight();

        /* Image coordinates */
        float s1 = 0.0F;
        float t1 = 0.0F;
        float s2 = 1.0F;
        float t2 = 1.0F;

        drawImage(x, y, x2, y2, s1, t1, s2, t2, Color.WHITE);
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void begin() {
        if (rendering) {
            return;
        }
        rendering = true;
        verticesCount = 0;
    }

    public void end() {
        if (!rendering) {
            return;
        }
        rendering = false;
        flush();
    }

    private void flush() {
        if (verticesCount <= 0) {
            return;
        }
        vertices.flip();

        vao.bind();
        program.use();

        // Upload a new vertex data
        vbo.bind();
        vbo.uploadSubData(vertices);

        // Render batch
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verticesCount);

        // Clear vertex data for next batch
        vertices.clear();
        verticesCount = 0;
    }

    /**
     * Dispose renderer and its used data
     */
    public void dispose() {
        MemoryUtil.memFree(vertices);

        vao.delete();
        vbo.delete();
        program.delete();
    }

    /**
     * Draws a texture region with the currently bound texture on specified
     * coordinates.
     *
     * @param x1 Bottom left x position
     * @param y1 Bottom left y position
     * @param x2 Top right x position
     * @param y2 Top right y position
     * @param s1 Bottom left s coordinate
     * @param t1 Bottom left t coordinate
     * @param s2 Top right s coordinate
     * @param t2 Top right t coordinate
     * @param c  The color to use
     */
    private void drawImage(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, Color c) {
        if (vertices.remaining() < 8 * 6) {
            flush(); // We need more space in the buffer, so flush it
        }
        float r = c.getRed();
        float g = c.getGreen();
        float b = c.getBlue();
        float a = c.getAlpha();

        vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices.put(x1).put(y2).put(r).put(g).put(b).put(a).put(s1).put(t2);
        vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
        vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
        vertices.put(x2).put(y1).put(r).put(g).put(b).put(a).put(s2).put(t1);
        verticesCount += 6;
    }
}
